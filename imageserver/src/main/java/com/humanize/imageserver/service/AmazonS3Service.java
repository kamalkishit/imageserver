package com.humanize.imageserver.service;

import java.io.File;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.humanize.imageserver.AmazonS3Properties;
import com.humanize.imageserver.ExceptionConfig;
import com.humanize.imageserver.data.Image;
import com.humanize.imageserver.exception.ImageCreationException;
import com.humanize.imageserver.exception.ImageNotFoundException;

@Service
public class AmazonS3Service {
	
	@Autowired
	AmazonS3Properties amazonS3Properties;
	
	private AmazonS3 s3Client;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public AmazonS3Service() {
		s3Client = new AmazonS3Client(new BasicAWSCredentials("AKIAILGFWMFKMZVXRGIQ", "mcftDmizMAGfPL9vdEmP7G9Zl1wBvAnCcPJGpFmu"));
	}
	
	public void putImage(Image image) throws ImageCreationException {
		try {
			String key = image.getImageName();
			File file = new File("/root/images/" + image.getImagePath() + image.getImageName());
			s3Client.putObject(new PutObjectRequest("com-humanize-images", key, file));
		} catch (Exception exception) {
			logger.error("", exception);
			throw new ImageCreationException(ExceptionConfig.AMAZON_S3_IMAGE_CREATION_ERROR_CODE, ExceptionConfig.AMAZON_S3_IMAGE_CREATION_EXCEPTION);
		} 
	}
	
	public InputStream getImage(String iamgeName) throws ImageNotFoundException {
		S3Object s3Object = null;
		
		try {
			s3Object = s3Client.getObject(amazonS3Properties.getBucketName(), "null.jpg");
		} catch (Exception exception) {
			logger.error("", exception);
			throw new ImageNotFoundException(ExceptionConfig.AMAZON_S3_IMAGE_NOT_FOUND_ERROR_CODE, ExceptionConfig.AMAZON_S3_IMAGE_NOT_FOUND_EXCEPTION);
		}
		
		return s3Object.getObjectContent();
	}
}
