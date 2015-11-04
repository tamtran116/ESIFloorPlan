package edu.umsl.esi.floorplan.controller;

import edu.umsl.esi.floorplan.domain.ReceiptDO;
import edu.umsl.esi.floorplan.model.DeleteReceiptRequest;
import edu.umsl.esi.floorplan.model.ReceiptResource;
import edu.umsl.esi.floorplan.model.SaveReceiptRequest;
import edu.umsl.esi.floorplan.services.ReceiptServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Controller
public class ReceiptController {
	private static final String API_KEY = "AIzaSyC0CxtTnmp4k5XWITiFs4o77LU95lIpyRc";
	private static DecimalFormat df2 = new DecimalFormat(".##");

	@Autowired
	private ReceiptServiceImpl receiptService;

	private Set<String> roles;

	@RequestMapping(value="/nearbysearch" , method={RequestMethod.GET, RequestMethod.HEAD}, produces={"application/json"})
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

	@RequestMapping(value="/textsearch" , method={RequestMethod.GET, RequestMethod.HEAD}, produces={"application/json"})
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

	@RequestMapping(value = "/receipt", method = RequestMethod.POST)
	public String saveReceipt(@ModelAttribute("saveReceiptRequest") @Valid SaveReceiptRequest saveReceiptRequest, Model model) {
		saveReceiptRequest.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		receiptService.saveReceipt(saveReceiptRequest);
		System.out.println(saveReceiptRequest.toString());
		//TODO: server side validation
		model.addAttribute("message", "Receipt saved successfully.");
		return saveReceipt(model);
	}

	@RequestMapping(value = {"/receipt", "/receipt#"}, method = RequestMethod.GET)
	public String saveReceipt(Model model) {
		Double receiptTotal = 0.0;
		List<ReceiptResource> receiptResourceList = receiptService.getReceipts(SecurityContextHolder.getContext().getAuthentication().getName());
		DeleteReceiptRequest deleteReceiptRequest = new DeleteReceiptRequest();
		deleteReceiptRequest.setReceiptIds(new ArrayList<String>());
		for (ReceiptResource receiptResource : receiptService.getReceipts(SecurityContextHolder.getContext().getAuthentication().getName())) {
			deleteReceiptRequest.getReceiptIds().add(receiptResource.getReceiptId());
			if (StringUtils.isNotBlank(receiptResource.getReceiptTotal())) {
				receiptTotal = receiptTotal + Double.parseDouble(receiptResource.getReceiptTotal().replaceAll(",", ""));
			}
		}
		SaveReceiptRequest saveReceiptRequest = new SaveReceiptRequest();
		saveReceiptRequest.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("deleteReceiptRequest", deleteReceiptRequest);
		model.addAttribute("saveReceiptRequest", saveReceiptRequest);
		model.addAttribute("userName", SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("receiptResourceList", receiptResourceList);
		model.addAttribute("receiptTotal", df2.format(receiptTotal));
		return "receipt";
	}

	@RequestMapping(value = "/deletereceipts", method = RequestMethod.POST)
	public String deleteReceipts(@ModelAttribute("deleteReceiptRequest") @Valid DeleteReceiptRequest deleteReceiptRequest, Model model) {
		receiptService.deleteReceipts(deleteReceiptRequest);
		model.addAttribute("message", "Receipt deleted successfully.");
		return saveReceipt(model);
	}
}
