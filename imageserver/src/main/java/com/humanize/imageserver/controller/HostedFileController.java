package com.humanize.imageserver.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.humanize.imageserver.data.HostedFile;
import com.humanize.imageserver.service.HostedFileService;


@RestController
public class HostedFileController {
	
	@Autowired
	HostedFileService hostedFileService;
	
	@RequestMapping(value = "/images", method = RequestMethod.GET, 
			headers="Accept=image/jpeg, image/jpg, image/png", produces = "image/jpeg")
	public ResponseEntity<InputStreamResource> getImage(@RequestParam("fileId") String hostedFileId, @RequestParam("filePath") String hostedFilePath) 
		throws Exception {
		
		InputStream inputStream = hostedFileService.getImage(hostedFileId, hostedFilePath);
		
	    return ResponseEntity
	            .ok()
	            .contentType(
	                    MediaType.parseMediaType("image/jpeg"))
	            .body(new InputStreamResource(inputStream));
	}
	
	@RequestMapping(value = "/images", method = RequestMethod.POST)
	public void putImage(@RequestBody HostedFile hostedFile) {
		try {
			hostedFileService.putImage(hostedFile);
		} catch (Exception e) {
			
		}
		
	}
}
