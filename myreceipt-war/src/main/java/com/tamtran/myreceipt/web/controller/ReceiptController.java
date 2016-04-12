package com.tamtran.myreceipt.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamtran.myreceipt.business.services.impl.ReceiptServiceImpl;
import com.tamtran.myreceipt.business.tesseract.TessOcr;
import com.tamtran.myreceipt.common.model.DeleteReceiptRequest;
import com.tamtran.myreceipt.common.model.ReceiptItems;
import com.tamtran.myreceipt.common.model.ReceiptResource;
import com.tamtran.myreceipt.common.model.SaveReceiptRequest;
import com.tamtran.myreceipt.common.utils.OSValidator;
import com.tamtran.myreceipt.common.utils.ReceiptConstants;
import com.tamtran.myreceipt.web.adapter.FaceDetectApp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.authority.AuthorityUtils;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class ReceiptController {
    private static final Logger logger = LogManager.getLogger();
    private static final String API_KEY = "AIzaSyC0CxtTnmp4k5XWITiFs4o77LU95lIpyRc";
    private static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    private static final String TEXT_HTML = "text/html;charset=UTF-8";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_PREMIUM_USER = "ROLE_PREMIUM";
    private static final int MAX_RESULTS = 4;
    private static DecimalFormat df2 = new DecimalFormat(".##");

    @Autowired
    private ReceiptServiceImpl receiptService;
    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    ApplicationContext context;

    private Set<String> roles;

    @RequestMapping(value = "/nearbysearch", method = {RequestMethod.GET, RequestMethod.HEAD}, produces = {APPLICATION_JSON})
    public
    @ResponseBody
    Object nearbySearch(@RequestParam(value = "lat", required = false) String lat,
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
        List<Map> results = new ArrayList<Map>();
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> gResponse = (Map<String, Object>) restTemplate.getForObject(url, Object.class);
        if ("OK".equalsIgnoreCase((String) gResponse.get("status"))) {
            for(;;) {
                if (null != gResponse.get("next_page_token")) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    results.addAll((ArrayList<Map>) gResponse.get("results"));
                    String nextToken = (String) gResponse.get("next_page_token");
                    String nextUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=" + nextToken + "&key=" + API_KEY;
                    RestTemplate nextRestTemplate = new RestTemplate();
                    gResponse = (Map<String, Object>) nextRestTemplate.getForObject(nextUrl, Object.class);

                } else {
                    break;
                }
            }
            results.addAll((ArrayList<Map>) gResponse.get("results"));
            logger.info("Results size = " + results.size());
        }
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("results", results);
        return response;
    }

    @RequestMapping(value = "/textsearch", method = {RequestMethod.GET, RequestMethod.HEAD}, produces = {APPLICATION_JSON})
    public
    @ResponseBody
    Object textSearch(@RequestParam(value = "name", required = false) String name,
                      @RequestParam(value = "location", required = false) String location,
                      @RequestParam(value = "radius", required = false) String radius,
                      @RequestParam(value = "token", required = false) String token) throws Exception {
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

    @RequestMapping(value = "/receipt", method = RequestMethod.POST, produces = APPLICATION_JSON)
    public @ResponseBody String saveReceipt(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                       @ModelAttribute("saveReceiptRequest") @Valid SaveReceiptRequest saveReceiptRequest,
                       HttpServletRequest request) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("saveReceiptRequest payload = " + saveReceiptRequest.toString());
        ObjectMapper om = new ObjectMapper();
        Map<String, String> response = new LinkedHashMap<String, String>();
        //TODO: server side validation
//		model.addAttribute("message", "Receipt saved successfully.");
        if (!multipartFile.isEmpty() && isValidContentType(multipartFile)) {
            String relativeWebPath = "/WEB-INF/resources/uploaded";
            String absoluteFilePath = request.getSession().getServletContext().getRealPath(relativeWebPath);
            String extrlRequestId = UUID.randomUUID().toString();
            String fileExtension = getFileExtension(multipartFile);
            String tempPreviewFile;
            File newFile = null;
            if (OSValidator.isWindows()) {
                // The Double Backslash "\\" is applied because of window path for localhost server
//				newFile = new File(absoluteFilePath+"\\"+extrlRequestId);
                newFile = new File(ReceiptConstants.WINDOW_RECEIPT_PATH + extrlRequestId + fileExtension);
                tempPreviewFile = absoluteFilePath + "\\preview_" + extrlRequestId + fileExtension;
            } else if (OSValidator.isUnix()) {
//				newFile = new File(absoluteFilePath+"/"+extrlRequestId);
                newFile = new File(ReceiptConstants.UNIX_RECEIPT_PATH + extrlRequestId + fileExtension);
                tempPreviewFile = absoluteFilePath + "/preview_" + extrlRequestId + fileExtension;
            } else {
                return "You failed to upload " + multipartFile.getOriginalFilename() + " because of unknown operation system.";
            }
            logger.info("New File Path: " + newFile.getPath());
            try {
                /*byte[] bytes = multipartFile.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
                stream.write(bytes);
                stream.close();*/
                multipartFile.transferTo(newFile);
                saveReceiptRequest.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                saveReceiptRequest.setExtrlRequestId(extrlRequestId);
                saveReceiptRequest.setReceiptPath(newFile.getPath());
                receiptService.saveReceipt(saveReceiptRequest);

                ReceiptItems receiptItems = new ReceiptItems(extrlRequestId, newFile.getPath());
                receiptItems.setCropX(saveReceiptRequest.getCropX()==null?0:saveReceiptRequest.getCropX());
                receiptItems.setCropX2(saveReceiptRequest.getCropX2()==null?0:saveReceiptRequest.getCropX2());
                receiptItems.setCropY(saveReceiptRequest.getCropY()==null?0:saveReceiptRequest.getCropY());
                receiptItems.setCropY2(saveReceiptRequest.getCropY2()==null?0:saveReceiptRequest.getCropY2());
                receiptItems.setCropW(saveReceiptRequest.getCropW()==null?0:saveReceiptRequest.getCropW());
                receiptItems.setCropH(saveReceiptRequest.getCropH()==null?0:saveReceiptRequest.getCropH());
                receiptItems.setCropO(saveReceiptRequest.getCropO()==null?1:saveReceiptRequest.getCropO());
                receiptItems.setImgH(saveReceiptRequest.getImgH());
                receiptItems.setImgW(saveReceiptRequest.getImgW());
                receiptItems.setIos("true".equalsIgnoreCase(saveReceiptRequest.getIos()));


                TessOcr tessOcr = (TessOcr) context.getBean("tessOcr");
                tessOcr.setReceiptItems(receiptItems);
                tessOcr.setPreviewFile(tempPreviewFile);
                taskExecutor.execute(tessOcr);

                logger.info("Save Receipt Request = " + saveReceiptRequest.toString());
//				return "You successfully uploaded " + file.getOriginalFilename() + "!";
                response.put("receiptId", extrlRequestId);
                return om.writeValueAsString(response);
            } catch (Exception e) {
                e.printStackTrace();
                return "{'error':'upload " + multipartFile.getOriginalFilename() + " fail, " + e.getMessage() + "'}";
            }
        } else {
            logger.error("File empty");
            return "{'error':'upload " + multipartFile.getOriginalFilename() + ".File was empty.";
        }
    }

    private boolean isValidContentType(MultipartFile file) {
        return (file.getContentType().contains("jpg") | file.getContentType().contains("png") | file.getContentType().contains("jpeg"));
    }

    private String getFileExtension(MultipartFile file) {
        if (file.getContentType().contains("jpg")) {
            return ".jpg";
        } else if (file.getContentType().contains("png")) {
            return ".png";
        } else if (file.getContentType().contains("jpeg")) {
            return ".jpeg";
        } else {
            return null;
        }
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
        if (isRole(ROLE_ADMIN) || isRole(ROLE_PREMIUM_USER)) {
            model.addAttribute("role", "premium");
        } else {
            model.addAttribute("role", "regular");
        }
        return "receipt";
    }

    @RequestMapping(value = "/getreceipts", method = RequestMethod.GET, produces = {APPLICATION_JSON})
    public
    @ResponseBody
    List<ReceiptResource> getReceipts() {
        return receiptService.getReceipts(SecurityContextHolder.getContext().getAuthentication().getName());
    }


    @RequestMapping(value = "/deletereceipts", method = RequestMethod.POST)
    public @ResponseBody List<ReceiptResource> deleteReceipts(@ModelAttribute("deleteReceiptRequest") @Valid DeleteReceiptRequest deleteReceiptRequest, Model model) {
        receiptService.deleteReceipts(deleteReceiptRequest);
//		model.addAttribute("message", "Receipt deleted successfully.");
        return getReceipts();
    }


    @RequestMapping(value = "/convertreceipt/{receiptId}", method = RequestMethod.GET, produces = TEXT_HTML)
    public @ResponseBody String convertReceipt(@PathVariable("receiptId") String receiptId, @RequestParam(value = "premium") boolean premiumFlag) throws Exception {
        String receiptPath;
        List<ReceiptResource> receiptResourceList = receiptService.getReceipts(SecurityContextHolder.getContext().getAuthentication().getName());
        for (ReceiptResource receiptResource : receiptResourceList) {
            if (StringUtils.isNotBlank(receiptId) && receiptId.equals(receiptResource.getReceiptId())) {
                receiptPath = receiptResource.getReceiptPath();
                logger.info("Receipt Path: " + receiptPath);
                File file = new File(receiptPath);
                if (file.isFile()) {
                    if (premiumFlag && (isRole(ROLE_ADMIN) || isRole(ROLE_PREMIUM_USER))) {
                        Path inputPath = Paths.get(receiptPath);
                        FaceDetectApp faceDetectApp = new FaceDetectApp(FaceDetectApp.getVisionService());
                        List<String> responseStringList = faceDetectApp.detectText(inputPath, MAX_RESULTS);
                        if (responseStringList.size() > 0) {
                            String rawDataString = faceDetectApp.detectText(inputPath, MAX_RESULTS).get(0);
                            logger.info("raw data string : " + rawDataString);
                            receiptResource.setReceiptRaw(rawDataString);
                            receiptResource.setReceiptProcessed(rawDataString);
                            receiptService.updateReceipt(receiptResource);
                            return rawDataString;
                        } else {
                            throw new Exception("Cannot convert to text");
                        }
                    } else {
                        if(StringUtils.isEmpty(receiptResource.getItems()) || "Unprocessed".equalsIgnoreCase(receiptResource.getItems())) {
                            return "converting in progress...";
                        } else {
                            return receiptResource.getItems();
                        }
                    }
                } else {
                    throw new Exception("Could not get file");
                }
            }
        }
        return "File not found !";
    }

    private boolean isRole(String role) {
        Set<String> authorities = AuthorityUtils
                .authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return authorities.contains(role);
    }

    @RequestMapping(value = "/save-receipt-items", method = RequestMethod.POST, consumes = {"application/json"}, produces = {APPLICATION_JSON})
    public
    @ResponseBody
    String saveReceiptItems(@RequestBody ReceiptItems receiptItems) throws Exception {
        logger.info(receiptItems.getReceiptId());
        logger.info(receiptItems.getReceiptItems());
        String[] itemArray = receiptItems.getReceiptItems().split("\\r?\\n");
        logger.info("number of items = " + itemArray.length);
        receiptService.updateReceiptItems(receiptItems);
        ObjectMapper om = new ObjectMapper();
        Map<String, String> response = new LinkedHashMap<String, String>();
        response.put("result", "success");
        return om.writeValueAsString(response);
    }

    @ExceptionHandler({Exception.class})
    public
    @ResponseBody
    Map exceptionString(Exception exception) {
        logger.error("Exception: " + exception.getMessage());
        exception.printStackTrace();
        Map<String, String> errorMap = new LinkedHashMap<String, String>();
        if (StringUtils.isEmpty(exception.getMessage())) {
            errorMap.put("error", exception.getMessage());
        } else {
            errorMap.put("error", "Unknown error, please contact admin.");
        }
        return errorMap;
    }

}

