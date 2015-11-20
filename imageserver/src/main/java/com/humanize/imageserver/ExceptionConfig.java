package com.humanize.imageserver;

public class ExceptionConfig {
	
	public static final int IMAGE_CREATION_ERROR_CODE = 10001;
	public static final String IMAGE_CREATION_EXCEPTION = "Image creation error";
	
	public static final int IMAGE_NOT_FOUND_ERROR_CODE = 10002;
	public static final String IMAGE_NOT_FOUND_EXCEPTION = "Image not found";
	
	public static final int AMAZON_S3_IMAGE_CREATION_ERROR_CODE = 10009;
	public static final int AMAZON_S3_IMAGE_NOT_FOUND_ERROR_CODE = 10010;
	
	public static final String AMAZON_S3_IMAGE_CREATION_EXCEPTION = "Image not created successfully on Amazon S3";
	public static final String AMAZON_S3_IMAGE_NOT_FOUND_EXCEPTION = "Image not found on Amazon s3";
}
