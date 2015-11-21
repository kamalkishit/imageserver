package com.humanize.imageserver.service;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.humanize.imageserver.data.Image;
import com.humanize.imageserver.exception.ImageCreationException;


@Service
public class ImageDownloaderService {
	
	private URL url;
	private URLConnection urlConnection;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public boolean downloadImage(Image image) throws ImageCreationException {
		try {
			createConnection(image.getOriginalURL());
			String tempImageFilename = getTempImageFilename(image);
			String imageFilename = getImageFilename(image);
			readImage(tempImageFilename);
			BufferedImage bufferedImage = processImage(tempImageFilename);
			saveImage(bufferedImage, getExtension(imageFilename), imageFilename);
		} catch (Exception exception) {
			throw new ImageCreationException(0, null);
		}
		
		return true;
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
	}
	
	private String getTempImageFilename(Image image) {
		return new String("/root/temp/" + image.getImagePath() + image.getImageName());
	}
	
	private String getImageFilename(Image image) {
		return new String("/root/images/" + image.getImagePath() + image.getImageName());

	}
	
	private void readImage(String tempImageFilename) throws Exception {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			inputStream = new BufferedInputStream(
					urlConnection.getInputStream());
			outputStream = new BufferedOutputStream(new FileOutputStream(
					tempImageFilename));

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
	}
	
	private BufferedImage processImage(String tempImageFilename) throws Exception{
		File inputFile = null;
		BufferedImage inputImage = null;
		BufferedImage outputImage = null;
		
		try {
			inputFile = new File(tempImageFilename);
			inputImage = ImageIO.read(inputFile);
			outputImage = Scalr.resize(inputImage,
					Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT, 512, 288,
					Scalr.OP_ANTIALIAS);
		} catch (Exception exception) {
			logger.error("", exception);
			throw exception;
		} 
		
		return outputImage;
	}
	
	private String getExtension(String imageFilename) {
		String extension = imageFilename.substring(imageFilename.lastIndexOf('.') + 1);
		
		return extension;
	}
	
	private void saveImage(BufferedImage bufferedImage, String extension, String imageFilename) 
		throws Exception {
		try {
			ImageIO.write(bufferedImage, extension, new File(imageFilename));
		} catch (IOException exception) {
			logger.error("", exception);
			throw exception;
		}
	}
}
