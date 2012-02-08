package fr.univartois.ili.fsnet.commons.utils;

/**
 * manage talk exception
 * 
 * @author habib
 * 
 */
public class TalkException extends Exception {

	public TalkException() {
	}

	public TalkException(String message) {
		super(message);
	}

	public TalkException(Throwable cause) {
		super(cause);
	}

	public TalkException(String message, Throwable cause) {
		super(message, cause);
	}
}
