package fr.univartois.ili.fsnet.actions;

public enum AllowedPictureType {

	JPEG("image/jpeg", ".jpg"), 
	PNG( "image/png",  ".png"), 
	BMP( "image/bmp",  ".bmp");

	private String mimeType;

	private String suffix;

	private AllowedPictureType(String mimeType, String suffix) {
		this.suffix = suffix;
		this.mimeType = mimeType;
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
