package fr.univartois.ili.fsnet.actions;

import java.awt.image.BufferedImage;

public enum AllowedPictureType {

	JPEG("image/jpeg", ".jpeg", BufferedImage.TYPE_BYTE_INDEXED), 
	PNG( "image/png",  ".png",  BufferedImage.TYPE_INT_ARGB), 
	BMP( "image/bmp",  ".bmp",  BufferedImage.TYPE_BYTE_INDEXED);

	private String mimeType;

	private String suffix;

	private int imageType;
	
	private AllowedPictureType(String mimeType, String suffix, int imageType) {
		this.suffix = suffix;
		this.mimeType = mimeType;
		this.imageType = imageType;
	}
	

	/**
	 * @return the imageType
	 */
	public int getImageType() {
		return imageType;
	}

	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

}
