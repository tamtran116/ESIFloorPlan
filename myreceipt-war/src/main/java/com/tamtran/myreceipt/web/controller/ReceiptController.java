package com.tamtran.myreceipt.web.controller;

import com.tamtran.myreceipt.business.services.impl.ReceiptServiceImpl;
import com.tamtran.myreceipt.business.tesseract.TessOcr;
import com.tamtran.myreceipt.common.model.DeleteReceiptRequest;
import com.tamtran.myreceipt.common.model.ReceiptResource;
import com.tamtran.myreceipt.common.model.SaveReceiptRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

@Controller
public class ReceiptController {
	private static final String API_KEY = "AIzaSyC0CxtTnmp4k5XWITiFs4o77LU95lIpyRc";
	private static final String APPLICATION_JSON = "application/json; charset=utf-8";
	private static DecimalFormat df2 = new DecimalFormat(".##");

	@Autowired
	private ReceiptServiceImpl receiptService;

	private Set<String> roles;

	@RequestMapping(value="/nearbysearch" , method={RequestMethod.GET, RequestMethod.HEAD}, produces={APPLICATION_JSON})
	public @ResponseBody Object nearbySearch(@RequestParam(value = "lat", required = false) String lat,
							@RequestParam(value = "lng", required = false) String lng,
							@RequestParam(value = "radius", required = false) String radius,
							@RequestParam(value = "token", required = false) String token) {
		String url;
		if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lng)) {
			url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lng + "&radius=" + radius + "&key=" + API_KEY;
		} else if (StringUtils.isNotBlank(token)) {
			url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=" + token + "&key=" + API_KEY;
		} else {
			return "error";
		}
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, Object.class);
	}

	@RequestMapping(value="/textsearch" , method={RequestMethod.GET, RequestMethod.HEAD}, produces={APPLICATION_JSON})
	public @ResponseBody Object textSearch(@RequestParam(value = "name", required = false) String name,
										   @RequestParam(value = "location", required = false) String location,
										   @RequestParam(value = "radius", required = false) String radius,
										   @RequestParam(value = "token", required = false) String token) throws Exception{
		String url;
		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(location)) {
			url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + URLEncoder.encode(name, "UTF-8") + "&location=" + location + "&radius=" + radius + "&key=" + API_KEY;
		} else if (StringUtils.isNotBlank(token)) {
			url = "https://maps.googleapis.com/maps/api/place/textsearch/json?pagetoken=" + token + "&key=" + API_KEY;
		} else {
			return "error";
		}
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, Object.class);
	}

	@RequestMapping(value = "/receipt", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public @ResponseBody String saveReceipt(@RequestParam(value = "file", required = false) MultipartFile file,
											@ModelAttribute("saveReceiptRequest") @Valid SaveReceiptRequest saveReceiptRequest,
											HttpServletRequest request) {
		//TODO: server side validation
//		model.addAttribute("message", "Receipt saved successfully.");
		if (!file.isEmpty() && isValidContentType(file)) {
			String OS = System.getProperty("os.name").toLowerCase();
			String relativeWebPath = "/WEB-INF/resources/uploaded";
			String absoluteFilePath = request.getSession().getServletContext().getRealPath(relativeWebPath);
			String extrlRequestId = UUID.randomUUID().toString();
			File newFile = null;
			if (OS.contains("win")) {
				// The Double Backslash "\\" is applied because of window path for localhost server
				newFile = new File(absoluteFilePath+"\\"+extrlRequestId);
			} else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
				newFile = new File(absoluteFilePath+"/"+extrlRequestId);
			} else {
				return "You failed to upload " + file.getOriginalFilename() + " because of unknown operation system.";
			}
			System.out.println(newFile.getPath());
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream =
						new BufferedOutputStream(new FileOutputStream(newFile));
				stream.write(bytes);
				stream.close();
				saveReceiptRequest.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
				saveReceiptRequest.setExtrlRequestId(extrlRequestId);
				saveReceiptRequest.setReceiptPath(newFile.getPath());
				receiptService.saveReceipt(saveReceiptRequest);
				System.out.println(saveReceiptRequest.toString());
				return "You successfully uploaded " + file.getOriginalFilename() + "!";
			} catch (Exception e) {
				return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
		}
	}

	private boolean isValidContentType(MultipartFile file) {
		return (file.getContentType().contains("jpg") | file.getContentType().contains("png") | file.getContentType().contains("jpeg"));
	}

	@RequestMapping(value = {"/receipt", "/receipt#"}, method = RequestMethod.GET)
	public String saveReceipt(Model model) {
		DeleteReceiptRequest deleteReceiptRequest = new DeleteReceiptRequest();
		deleteReceiptRequest.setReceiptIds(new ArrayList<String>());
		for (ReceiptResource receiptResource : receiptService.getReceipts(SecurityContextHolder.getContext().getAuthentication().getName())) {
			deleteReceiptRequest.getReceiptIds().add(receiptResource.getReceiptId());
		}
		model.addAttribute("deleteReceiptRequest", deleteReceiptRequest);
		model.addAttribute("userName", SecurityContextHolder.getContext().getAuthentication().getName());
		return "receipt";
	}

	@RequestMapping(value = "/getreceipts", method = RequestMethod.GET, produces={APPLICATION_JSON})
	public @ResponseBody List<ReceiptResource> getReceipts() {
		return receiptService.getReceipts(SecurityContextHolder.getContext().getAuthentication().getName());
	}


	@RequestMapping(value = "/deletereceipts", method = RequestMethod.POST)
	public @ResponseBody List<ReceiptResource> deleteReceipts(@ModelAttribute("deleteReceiptRequest") @Valid DeleteReceiptRequest deleteReceiptRequest, Model model) {
		receiptService.deleteReceipts(deleteReceiptRequest);
//		model.addAttribute("message", "Receipt deleted successfully.");
		return getReceipts();
	}


	@RequestMapping(value = "/convertreceipt/{receiptId}", method = RequestMethod.GET, produces = APPLICATION_JSON)
	public @ResponseBody String convertReceipt(@PathVariable("receiptId") String receiptId) throws Exception {
		String receiptPath = "";
		List<ReceiptResource> receiptResourceList = receiptService.getReceipts(SecurityContextHolder.getContext().getAuthentication().getName());
		for (ReceiptResource receiptResource : receiptResourceList) {
			if (StringUtils.isNotBlank(receiptId) && receiptId.equals(receiptResource.getReceiptId())) {
				receiptPath = receiptResource.getReceiptPath();
				System.out.println("Receipt Path: " + receiptPath);
				File file = new File(receiptPath);
				if (file.isFile()) {
					TessOcr ocr = new TessOcr();
					String rawDataString = ocr.processRaw(receiptPath);
					String processedDataString = ocr.processedData(rawDataString);
					receiptResource.setReceiptRaw(rawDataString);
					receiptResource.setReceiptProcessed(processedDataString);
					receiptService.updateReceipt(receiptResource);
					return processedDataString;
				}
			}
		}
		return "File not found !";
	}

	@ExceptionHandler({Exception.class})
	public @ResponseBody Map exceptionString(Exception exception) {
		Map<String, String> errorMap = new LinkedHashMap<String, String>();
		errorMap.put("error", exception.getMessage());
		return errorMap;
	}

}

