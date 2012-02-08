package fr.univartois.ili.fsnet.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * class to manage file properties. *
 * 
 * @author habib
 * 
 */

public class PropsUtils {

	private static Properties fsnetProperties = new Properties();;
	static {
		try {

			
			InputStream is = PropsUtils.class.getClassLoader()  
	                .getResourceAsStream("config.properties");  
			fsnetProperties
					.load(is);

		} catch (IOException ex) {

			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * 
	 * @param prop
	 * @return value for property {@code prop}
	 * @throws IllegalStateException
	 *             if property {@code prop} is not defined
	 */
	public static String getProperty(String prop) {
		
		String p = fsnetProperties.getProperty(prop);
		if (p == null) {
			throw new IllegalStateException("Undefined  property: " + prop);
		}
		return p;
	}

}