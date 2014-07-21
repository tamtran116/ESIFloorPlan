package edu.umsl.esi.floorplan.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.umsl.esi.floorplan.domain.FileUpload;
import edu.umsl.esi.floorplan.domain.FloorEntity;
import edu.umsl.esi.floorplan.services.CubeService;
import edu.umsl.esi.floorplan.services.FloorService;
import edu.umsl.esi.floorplan.validator.FileUploadValidator;

@Controller
public class FileUploadController {

 @Autowired
 FileUploadValidator fileValidator;
 
 @Autowired
 private FloorService floorService;
 
 

 @RequestMapping(value="/uploadfloor")
 public ModelAndView getUploadForm(
   @ModelAttribute("uploadedFile") FileUpload uploadedFile,
   BindingResult result) {
  return new ModelAndView("uploadForm");
 }

@SuppressWarnings("resource")
@RequestMapping(value="/fileUpload", method=RequestMethod.POST)
 public ModelAndView fileUploaded(
   @ModelAttribute("uploadedFile") FileUpload uploadedFile,
   BindingResult result, HttpServletRequest request) {
  InputStream inputStream = null;
  OutputStream outputStream = null;

  MultipartFile file = uploadedFile.getFile();
  fileValidator.validate(uploadedFile, result);

  FloorEntity floorEntity = new FloorEntity();
  floorEntity.setFloorName(uploadedFile.getFloorName());
  floorEntity.setFloorLocation(uploadedFile.getFloorLocation());
  floorEntity.setUploadedBy(uploadedFile.getUploadedBy());
  floorEntity.setFloorDesc(uploadedFile.getFloorDesc());
  
  String fileName = file.getOriginalFilename();
  if (result.hasErrors()) {
   return new ModelAndView("uploadForm");
  }

  try {
	  
   inputStream = file.getInputStream();
//   ServletContext sc=request.getSession().getServletContext();
   String relativeWebPath = "/WEB-INF/resources/uploaded_floor";
   String absoluteFilePath = request.getSession().getServletContext().getRealPath(relativeWebPath);
//    for localhost
//   String absPath = "/Users/tamtran/Documents/workspace/ESIFloorPlan_Upload/";

   // The Double Backslash "\\" is applied because of window path for localhost server
   File newFile = new File(absoluteFilePath+"\\"+fileName);
   System.out.println(absoluteFilePath+"\\"+fileName);
   if (!newFile.exists()) {
    newFile.createNewFile();
    floorEntity.setFilePath(absoluteFilePath+"\\"+fileName);
    System.out.println(floorEntity.toString());
    floorService.addFloor(floorEntity);
   }
   outputStream = new FileOutputStream(newFile);
   int read = 0;
   byte[] bytes = new byte[1024];

   while ((read = inputStream.read(bytes)) != -1) {
    outputStream.write(bytes, 0, read);
   }
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }

  return new ModelAndView("showFile", "message", fileName);
 }

}

