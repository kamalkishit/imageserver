package com.humanize.imageserver.service;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.humanize.imageserver.ExceptionConfig;
import com.humanize.imageserver.data.Image;
import com.humanize.imageserver.exception.AmazonS3ImageNotFoundException;
import com.humanize.imageserver.exception.ImageCreationException;
import com.humanize.imageserver.exception.ImageNotFoundException;

@Service
public class ImageService {
	
	@Autowired
	ImageDownloaderService imageDownloaderService;
	
	@Autowired
	AmazonS3Service amazonS3Service;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public InputStream getImage(String imageName, String imagePath) throws ImageNotFoundException {
		try {
			FileSystemResource imageResource = new FileSystemResource("/root/images/" + imagePath + imageName);
			return imageResource.getInputStream();
		} catch (IOException exception) {
			logger.error("", exception);
			
			try {
				return amazonS3Service.getImage(imageName);
			} catch (Exception e) {
				logger.error("", e);
				throw new ImageNotFoundException(ExceptionConfig.IMAGE_NOT_FOUND_ERROR_CODE, ExceptionConfig.IMAGE_NOT_FOUND_EXCEPTION);
			}
		}
	}
	
	public void putImage(Image image) throws ImageCreationException {
		try {
			imageDownloaderService.downloadImage(image);
			amazonS3Service.putImage(image);
		} catch (Exception exception) {
			logger.error("", exception);
			throw new ImageCreationException(ExceptionConfig.IMAGE_CREATION_ERROR_CODE, ExceptionConfig.IMAGE_CREATION_EXCEPTION);
		}
	}
	
	/*
	private boolean downloadImage(Image image) throws ImageCreationException{
		try {
			createConnection(image.getOriginalURL());
			
			String tempImageFilename = "/root/temp/" + image.getImagePath() + image.getImageName();
			String imageFilename = "/root/images/" + image.getImagePath() + image.getImageName();

			readImage(tempImageFilename);
			writeImage(imageFilename, tempImageFilename);
		} catch (Exception exception) {
			logger.error("", exception);
			throw new ImageCreationException(ExceptionConfig.IMAGE_CREATION_ERROR_CODE, ExceptionConfig.IMAGE_CREATION_EXCEPTION);
		}
		
		return true;
	}
	
	private void writeImage(String imageFilename, String tempImageFilename) throws Exception{
		try {
			File inputFile = new File(tempImageFilename);
			BufferedImage inputImage = ImageIO.read(inputFile);
			BufferedImage outputImage = Scalr.resize(inputImage,
					Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT, 512, 288,
					Scalr.OP_ANTIALIAS);

			String formatName = imageFilename.substring(imageFilename.lastIndexOf('.') + 1);

			ImageIO.write(outputImage, formatName, new File(imageFilename));
		} catch (Exception exception) {
			logger.error("", exception);
			throw exception;
		}
	}
	
	private OutputStream readImage(String tempImageFilename) throws Exception{
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			inputStream = new BufferedInputStream(urlConnection.getInputStream());
			outputStream = new BufferedOutputStream(new FileOutputStream(tempImageFilename));
			
			for (int i; (i = inputStream.read()) != -1;) {
				outputStream.write(i);
			}
		} catch (Exception exception) {
			logger.error("", exception);
			throw exception;
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException exception) {
				logger.error("", exception);
				throw exception;
			}
		}

		return outputStream;
	}
	
	private void createConnection(String imageUrl) throws Exception {
		try {
			url = new URL(imageUrl);
			urlConnection = url.openConnection();
			urlConnection.setRequestProperty("User-Agent", "Mozilla");
		} catch (Exception exception) {
			logger.error("", exception);
			throw exception;
		}
	} */
}
