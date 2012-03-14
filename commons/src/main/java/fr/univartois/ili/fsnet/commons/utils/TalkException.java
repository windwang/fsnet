package fr.univartois.ili.fsnet.commons.utils;

/**
 * manage talk exception
 * 
 * @author habib
 * 
 */
public class TalkException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public TalkException() {
	}

	/**
	 * @param message
	 */
	public TalkException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public TalkException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TalkException(String message, Throwable cause) {
		super(message, cause);
	}
}
