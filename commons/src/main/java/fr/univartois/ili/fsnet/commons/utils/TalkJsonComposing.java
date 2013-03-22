package fr.univartois.ili.fsnet.commons.utils;

public class TalkJsonComposing {

	private String name;
	private Boolean isComposing ;

	/**
	 * @param name
	 * @param isComposing
	 */
	public TalkJsonComposing(String name, Boolean isComposing) {
		super();
		this.name = name;
		this.isComposing = isComposing ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsComposing() {
		return isComposing;
	}

	public void setIsComposing(Boolean isComposing) {
		this.isComposing = isComposing;
	}
	
	
}
