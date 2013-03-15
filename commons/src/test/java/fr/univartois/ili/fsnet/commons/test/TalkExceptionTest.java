package fr.univartois.ili.fsnet.commons.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.univartois.ili.fsnet.commons.utils.TalkException;

public class TalkExceptionTest {
	
	@Test
	public void testEmptyConstructor(){
		TalkException t = new TalkException();
		assertNotNull(t);
	}
	
	@Test
	public void testConstructorWithString(){
		String message = "oui";
		TalkException t = new TalkException(message);
		assertNotNull(t);
	}
	
	@Test
	public void testConstructorWithThrowable(){
		Throwable cause = new Throwable("oui");
		TalkException t = new TalkException(cause);
		assertNotNull(t);
	}
	
	@Test
	public void testConstructorWithThrowableAndString(){
		String message = "oui";
		Throwable cause = new Throwable(message);
		TalkException t = new TalkException(message, cause);
		assertNotNull(t);
	}

}
