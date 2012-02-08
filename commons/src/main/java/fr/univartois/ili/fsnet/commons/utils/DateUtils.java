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
	 * @param date
	 *            string
	 * 
	 * @return date object
	 * @throws ParseException
	 */
	public static Date format(String string) throws ParseException {
		Date date = null;
		DateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy hh:mm",
				Locale.FRANCE);
		date = simpleFormat.parse(string);
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

	private static final DateFormat formatter = new SimpleDateFormat(
			"dd/MM/yyyy");
	private static final DateFormat formatterWithHours = new SimpleDateFormat(
			"dd/MM/yyyy hh:mm");

	/**
	 * Return a string representation of the date in parameter
	 */
	public static String renderDate(Date date) {
		return formatter.format(date);
	}

	/**
	 * Return a string representation of the date with hours in parameter
	 * 
	 */
	public static String renderDateWithHours(Date date) {
		return formatterWithHours.format(date);
	}

	public static String renderDateForFullCalendar(Date date) {
		SimpleDateFormat usFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		java.util.Calendar cal = java.util.GregorianCalendar.getInstance();
		cal.setTime(date);
		String usDate = usFormat.format(cal.getTime());
		return usDate;
	}

	public static String renderDBDate(Date date) {
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Calendar cal = java.util.GregorianCalendar.getInstance();
		cal.setTime(date);
		String dbDate = dbDateFormat.format(cal.getTime());
		return dbDate;
	}

}
