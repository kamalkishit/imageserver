package com.humanize.imageserver.data;

public class HostedFile {
	
	private String originalURL;
	
	private String hostedFileId;
	
	private String hostedFilePath;

	public String getOriginalURL() {
		return originalURL;
	}

	public void setOriginalURL(String originalURL) {
		this.originalURL = originalURL;
	}

	public String getHostedFileId() {
		return hostedFileId;
	}

	public void setHostedFileId(String hostedFileId) {
		this.hostedFileId = hostedFileId;
	}

	public String getHostedFilePath() {
		return hostedFilePath;
	}

	public void setHostedFilePath(String hostedFilePath) {
		this.hostedFilePath = hostedFilePath;
	}
}
