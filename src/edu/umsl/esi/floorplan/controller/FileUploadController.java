package edu.umsl.esi.floorplan.controller;

import java.io.*;

import javax.servlet.http.HttpServletRequest;

import edu.umsl.esi.floorplan.domain.FloorDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.umsl.esi.floorplan.model.FileUpload;
import edu.umsl.esi.floorplan.services.FloorService;
import edu.umsl.esi.floorplan.validator.FileUploadValidator;

@Controller
public class FileUploadController {

    private static String OS = System.getProperty("os.name").toLowerCase();

    @Autowired
    FileUploadValidator fileValidator;

    @Autowired
    private FloorService floorService;

   /* @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }*/

    @RequestMapping(value="/uploadfloor")
    public ModelAndView getUploadForm(@ModelAttribute("uploadedFile") FileUpload uploadedFile, BindingResult result) {
        ModelAndView mov = new ModelAndView("uploadForm");
        mov.addObject("floor", new FloorDO());
        mov.addObject("floorList", floorService.listFloor());
        return mov;
    }

    // @SuppressWarnings("unchecked")
    //@RequestMapping(value="/floordata", method=RequestMethod.GET)
    // public ModelAndView getFloorList() {
    //	 ModelAndView mov = new ModelAndView("uploadForm");
    //	 mov.addObject("uploadedFile", new FileUpload());
    //	 mov.addObject("floor", new FloorDO());
    //	 mov.addObject("floorList", floorService.listFloor());
    //	 System.out.println("Controller floor size = "+floorService.listFloor().size());
    //	 return mov;
    // }

    @SuppressWarnings("resource")
    @RequestMapping(value="/fileUpload", method=RequestMethod.POST)
    public ModelAndView fileUploaded(@ModelAttribute("uploadedFile") FileUpload uploadedFile, BindingResult result, HttpServletRequest request) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        MultipartFile file = uploadedFile.getFile();
        fileValidator.validate(uploadedFile, result);
        FloorDO floorDO = new FloorDO();
        floorDO.setFloorName(uploadedFile.getFloorName());
        floorDO.setFloorLocation(uploadedFile.getFloorLocation());
        floorDO.setUploadedBy(uploadedFile.getUploadedBy());
        floorDO.setFloorDesc(uploadedFile.getFloorDesc());
        String fileName = file.getOriginalFilename();
        if (result.hasErrors()) {
            return new ModelAndView("uploadForm");
        }

        try {
            inputStream = file.getInputStream();
            String relativeWebPath = "/WEB-INF/resources/uploaded";
            String absoluteFilePath = request.getSession().getServletContext().getRealPath(relativeWebPath);
            File newFile = null;
            if (OS.indexOf("win") >= 0) {
                // The Double Backslash "\\" is applied because of window path for localhost server
                newFile = new File(absoluteFilePath+"\\"+fileName);
            } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
                newFile = new File(absoluteFilePath+"/"+fileName);
            }

            if (!newFile.exists()) {
                newFile.createNewFile();
                floorDO.setFilePath("resources/uploaded/"+fileName);
                System.out.println(floorDO.toString());
                floorService.addFloor(floorDO);
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
            return new ModelAndView("uploadForm", "error", "Error in uploading file, contact admin or developer");
        }
        return new ModelAndView("showFile", "message", fileName);
    }
}

