package com.humanize.imageserver.controller;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.humanize.imageserver.data.Image;
import com.humanize.imageserver.exception.ImageCreationException;
import com.humanize.imageserver.exception.ImageNotFoundException;
import com.humanize.imageserver.service.ImageService;

@RestController
public class ImageController {
	
	@Autowired
	ImageService imageService;
	
	@RequestMapping(value = "/images", method = RequestMethod.GET, 
			headers="Accept=image/jpeg, image/jpg, image/png", produces = "image/jpeg")
	public ResponseEntity<InputStreamResource> getImage(@RequestParam("imageName") String imageName, @RequestParam("imagePath") String imagePath) 
		throws ImageNotFoundException {
		InputStream inputStream = imageService.getImage(imageName, imagePath);
		
	    return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/jpeg"))
	            .body(new InputStreamResource(inputStream));
	}
	
	@RequestMapping(value = "/images", method = RequestMethod.POST)
	public void putImage(@RequestBody Image image) throws ImageCreationException {
		imageService.putImage(image);
	}
}
