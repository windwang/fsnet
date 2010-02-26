package fr.univartois.ili.fsnet.facade.security;

public class UnauthorizedOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The unique constructor of this class.
	 * @param key the resource bundle key that represents the message to show to an user
	 */
	public UnauthorizedOperationException(String key) {
	}

}
