package com.humanize.imageserver.data;

import org.hibernate.validator.constraints.NotEmpty;

public class Image {
	
	@NotEmpty
	private String originalURL;
	
	@NotEmpty
	private String imageName;
	
	@NotEmpty
	private String imagePath;

	public String getOriginalURL() {
		return originalURL;
	}

	public void setOriginalURL(String originalURL) {
		this.originalURL = originalURL;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
