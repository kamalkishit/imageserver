package com.humanize.imageserver.service;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.humanize.imageserver.data.HostedFile;
import com.humanize.imageserver.exception.ImageNotFoundException;
import com.humanize.imageserver.exception.NullHostedFileException;
import com.humanize.imageserver.exception.NullHostedFileIdException;
import com.humanize.imageserver.exception.NullHostedFilePathException;

@Service
public class HostedFileService {
	
	public InputStream getImage(String hostedFileId, String hostedFilePath) 
			throws Exception {
		
		validate(hostedFileId, hostedFilePath);
		
		try {
			FileSystemResource imageResource = new FileSystemResource(hostedFilePath + hostedFileId);
			return imageResource.getInputStream();
		} catch (IOException exception) {
			throw new ImageNotFoundException();
		}
	}
	
	public void putImage(HostedFile hostedFile) throws Exception {
		
		validate(hostedFile);
		
		downloadImage(hostedFile);
	}
	
	private void validate(String hostedFileId, String hostedFilePath) 
			throws Exception {
		
		if (hostedFileId == null) {
			throw new NullHostedFileIdException();
		}
		
		if (hostedFilePath == null) {
			throw new NullHostedFilePathException();
		}
	}
	
	private void validate(HostedFile hostedFile) 
			throws Exception {
		
		if (hostedFile == null) {
			throw new NullHostedFileException();
		}
		
		validate(hostedFile.getHostedFileId(), hostedFile.getHostedFilePath());
	}
	
	private boolean downloadImage(HostedFile hostedFile) {
		try {
			URL url = new URL(hostedFile.getOriginalURL());
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("User-Agent", "Mozilla");
			String tempImageFilename = "/root/images/"
					+ "temp"
					+ hostedFile.getOriginalURL().substring(
							hostedFile.getOriginalURL().lastIndexOf('.'));
			String imageFilename = "/root/images/" + hostedFile.getHostedFilePath()
					+ hostedFile.getHostedFileId()
					+ hostedFile.getOriginalURL().substring(
							hostedFile.getOriginalURL().lastIndexOf('.'));

			System.out.println(imageFilename);
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			OutputStream out = new BufferedOutputStream(new FileOutputStream(
					tempImageFilename));

			for (int i; (i = in.read()) != -1;) {
				out.write(i);
			}

			in.close();
			out.close();

			File inputFile = new File(tempImageFilename);
			BufferedImage inputImage = ImageIO.read(inputFile);
			BufferedImage outputImage = Scalr.resize(inputImage,
					Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT, 512, 288,
					Scalr.OP_ANTIALIAS);

			String formatName = hostedFile.getOriginalURL().substring(
					hostedFile.getOriginalURL().lastIndexOf('.') + 1);

			ImageIO.write(outputImage, formatName, new File(imageFilename));

			return true;
		} catch (MalformedURLException exception) {
			
		} catch (FileNotFoundException exception) {
			
		} catch (IOException exception) {
			
		}
		
		return true;
	}
}
