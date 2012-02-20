package fr.univartois.ili.fsnet.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
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
		DateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy HH:mm",
				Locale.FRANCE);
		date = simpleFormat.parse(string);
		return date;
	}

	/**
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDate(String string) throws ParseException {
		Date date = null;
		DateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy",
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
//		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY)-1, 0, 0);
		Date today = calendar.getTime();
		return today.compareTo(date);
	}

	private static final DateFormat formatter = new SimpleDateFormat(
			"dd/MM/yyyy");
	private static final DateFormat formatterWithHours = new SimpleDateFormat(
			"dd/MM/yyyy hh:mm");
	
	

	/**
	 * Return a string representation of the date icalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR),
				calendar.get(Calendar.MINUTE), -1);
		Date today = calendar.getTime();n parameter
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
	

	

	/**
	 * @param date
	 * @return
	 */
	public static String renderDateForFullCalendar(Date date) {
		SimpleDateFormat usFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		java.util.Calendar cal = java.util.GregorianCalendar.getInstance();
		cal.setTime(date);
		String usDate = usFormat.format(cal.getTime());
		return usDate;
	}

	/**
	 * @param date
	 * @return
	 */
	public static String renderDBDate(Date date) {
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Calendar cal = java.util.GregorianCalendar.getInstance();
		cal.setTime(date);
		String dbDate = dbDateFormat.format(cal.getTime());
		return dbDate;
	}
	
	public static String renderDBDateWithSecond(Date date) {
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.FRANCE);
		java.util.Calendar cal = java.util.GregorianCalendar.getInstance();
		cal.setTime(date);
		String dbDate = dbDateFormat.format(cal.getTime());
		return dbDate;
	}
	
	
	
	public static Date substractTimeToDate(Date date,int time,String type){
		if(date == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		if(type.equals("minute")){
			calendar.add(Calendar.MINUTE, time*-1);
		}	
		else{
			if(type.equals("hour")){
				calendar.add(Calendar.HOUR, time*-1);
			}
			else
				calendar.add(Calendar.DATE, time*-1);	
		}
		return calendar.getTime();
	}
	
	/**
	 * 
	 * @param date, date
	 * @return string represent the difference between two date in minutes
	 * 
	 */
	public static long differenceBetweenTwoDateInMinutes(Date date1, Date date2){
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(date1);
		calendar2.setTime(date2);
		return (calendar1.getTimeInMillis()-calendar2.getTimeInMillis())/60000;
	}

}
