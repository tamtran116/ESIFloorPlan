package edu.umsl.esi.floorplan.controller;

import java.io.*;

import javax.servlet.http.HttpServletRequest;

import edu.umsl.esi.floorplan.domain.FloorDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(value="/uploadfloor", method=RequestMethod.GET)
    public String getUploadForm(Model model) {
        model.addAttribute("floor", new FloorDO());
        model.addAttribute("floorList", floorService.listFloor());
        return "uploadForm";
    }


    @RequestMapping(value="/uploadfloor", method=RequestMethod.POST)
    public String fileUploaded(@RequestParam("file") MultipartFile file,
            @ModelAttribute("uploadFloorInfo") FileUpload uploadFloorInfo, HttpServletRequest request, Model model,BindingResult result) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
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
            inputStream = file.getInputStream();
            String relativeWebPath = "/WEB-INF/resources/uploaded";
            String absoluteFilePath = request.getSession().getServletContext().getRealPath(relativeWebPath);
            File newFile = null;
            if (OS.contains("win")) {
                // The Double Backslash "\\" is applied because of window path for localhost server
                newFile = new File(absoluteFilePath+"\\"+fileName);
            } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
                newFile = new File(absoluteFilePath+"/"+fileName);
            }

            if (null!= newFile && !newFile.exists() && newFile.createNewFile()) {
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
//            return new ModelAndView("uploadForm", "error", "Error in uploading file, contact admin or developer");
            return "error";
        }
        model.addAttribute("message", fileName);
        return getUploadForm(model);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleIOException(HttpRequestMethodNotSupportedException ex) {
        // prepare responseEntity
        System.out.println(ex.getMessage());
        return null;
    }

}

