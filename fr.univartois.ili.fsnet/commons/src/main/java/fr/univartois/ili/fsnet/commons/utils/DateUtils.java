package fr.univartois.ili.fsnet.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * @author Mehdi Benzagahr
 * 
 */
public class DateUtils {
	/**
	 * 
	 * @param date string
	 *            
	 * @return date object
	 * @throws ParseException
	 */
	public static Date format(String string) throws ParseException {

		Date date = null;
		DateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy",
				Locale.FRANCE);
		date = (Date) simpleFormat.parse(string);
		return date;
	}

	/**
	 * 
	 * @param date
	 * @return X < 0 if date > today ;X > 0 if date < today ; X==0 if date ==
	 *         today
	 */
	public static Integer compareToToday(Date date) {
		Date today = new Date();
		return today.compareTo(date);
	}

}