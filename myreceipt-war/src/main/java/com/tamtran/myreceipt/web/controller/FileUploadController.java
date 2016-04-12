package com.tamtran.myreceipt.web.controller;

import com.tamtran.myreceipt.business.services.FloorService;
import com.tamtran.myreceipt.common.model.FileUpload;
import com.tamtran.myreceipt.common.utils.OSValidator;
import com.tamtran.myreceipt.common.utils.ReceiptConstants;
import com.tamtran.myreceipt.data.domain.FloorDO;
import com.tamtran.myreceipt.web.validator.FileUploadValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Controller
public class FileUploadController {

    private static final Logger logger = LogManager.getLogger();
    @Autowired
    FileUploadValidator fileValidator;
    @Autowired
    private FloorService floorService;

    @RequestMapping(value="/uploadfloor", method=RequestMethod.GET)
    public String getUploadForm(Model model) {
        model.addAttribute("floor", new FloorDO());
        model.addAttribute("floorList", floorService.listFloor());
        return "uploadForm";
    }


    @RequestMapping(value="/uploadfloor", method=RequestMethod.POST)
    public String fileUploaded(@RequestParam("file") MultipartFile file,
                               @ModelAttribute("uploadFloorInfo") FileUpload uploadFloorInfo, HttpServletRequest request, Model model, BindingResult result) {
        fileValidator.validate(uploadFloorInfo, result);
        
        FloorDO floorDO = new FloorDO();
        floorDO.setFloorName(uploadFloorInfo.getFloorName());
        floorDO.setFloorLocation(uploadFloorInfo.getFloorLocation());
        floorDO.setUploadedBy(uploadFloorInfo.getUploadedBy());
        floorDO.setFloorDesc(uploadFloorInfo.getFloorDesc());
        String fileName = file.getOriginalFilename();
        if (result.hasErrors()) {
            return "uploadForm";
        }
        try {
            String relativeWebPath = "/WEB-INF/resources/uploaded";
            String absoluteFilePath = request.getSession().getServletContext().getRealPath(relativeWebPath);
            File newFile = null;
            if (OSValidator.isWindows()) {
                newFile = new File(absoluteFilePath + "\\" + fileName);
            } else if (OSValidator.isUnix()) {
                newFile = new File(absoluteFilePath + "/" + fileName);
            }
            if (null!= newFile && !newFile.exists() && newFile.createNewFile()) {
                floorDO.setFilePath("resources/uploaded/" +fileName);
                logger.info("adding floor:" + floorDO.toString());
                floorService.addFloor(floorDO);
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
                stream.write(bytes);
                stream.close();
            }
        } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
//            return new ModelAndView("uploadForm", "error", "Error in uploading file, contact admin or developer");
            return "error";
        }
        model.addAttribute("message", fileName);
        return getUploadForm(model);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleIOException(HttpRequestMethodNotSupportedException ex) {
        ex.printStackTrace();
        // prepare responseEntity
        logger.info(ex.getMessage());
        return null;
    }

}

