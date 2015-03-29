package edu.umsl.esi.floorplan.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.umsl.esi.floorplan.domain.FileUpload;
@Component
public class FileUploadValidator implements Validator {
	

	 public boolean supports(Class<?> arg0) {
	  // TODO Auto-generated method stub
	  return false;
	 }

	 @Override
	 public void validate(Object uploadedFile, Errors errors) {

		 FileUpload file = (FileUpload) uploadedFile;

	  if (file.getFile().getSize() == 0) {
	   errors.rejectValue("file", "uploadForm.selectFile",
	     "Please select a file!");
	  }

	 }

}
