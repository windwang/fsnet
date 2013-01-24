package fr.univartois.ili.fsnet.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mehdi Benzagahr
 * 
 */
public final class DateUtils {
	
	/** Value of DATE in ics Calendar RFC (eg : 20120331T143055) **/
	private static final String ICS_DATE_PATTERN = "([0-9]{4})([0-9]{2})([0-9]{2})(T([0-9]{2})([0-9]{2})([0-9]{2})?(Z?))?";
	
	private DateUtils(){
		
	}
	
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
				calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), 0);
		Date today = calendar.getTime();
		return today.compareTo(date);
	}


	/**
	 * Return a string representation of the date icalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR),
				calendar.get(Calendar.MINUTE), -1);
		Date today = calendar.getTime();n parameter
	 */
	public static String renderDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}

	/**
	 * Return a string representation of the date with hours in parameter
	 * 
	 */
	public static String renderDateWithHours(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
	}
	

	

	/**
	 * @param date
	 * @return
	 */
	public static String renderDateForFullCalendar(Date date) {
		SimpleDateFormat usFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		java.util.Calendar cal = java.util.GregorianCalendar.getInstance();
		cal.setTime(date);
		return usFormat.format(cal.getTime());
	}

	/**
	 * @param date
	 * @return
	 */
	public static String renderDBDate(Date date) {
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Calendar cal = java.util.GregorianCalendar.getInstance();
		cal.setTime(date);
		return dbDateFormat.format(cal.getTime());
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
				calendar.add(Calendar.HOUR_OF_DAY, time*-1);
			}else{
				calendar.add(Calendar.DATE, time*-1);	
			}
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

	
	public static Date convertIcsTimestampToDate(String date) {
		Calendar cal = Calendar.getInstance();

		Matcher matcher = Pattern.compile(ICS_DATE_PATTERN).matcher(date);
		while(matcher.find()){
			cal.set(java.util.Calendar.MONTH, Integer.parseInt(matcher.group(2)));
			cal.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(matcher.group(3)));
			cal.set(java.util.Calendar.YEAR, Integer.parseInt(matcher.group(1)));
			if((matcher.group(5)!=null) && (matcher.group(6) != null)) {
				cal.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(matcher.group(5)));
				cal.set(java.util.Calendar.MINUTE, Integer.parseInt(matcher.group(6)));	
				cal.set(java.util.Calendar.SECOND, 0);
			}

		}
		
		return cal.getTime();
	}
	
	/**
	 * Method that convert a string date in us format to a Date object (used by ical4j)
	 * @param date Date in US format : 12/31/2011 23:59
	 * @return
	 */
	public static Date toIcal4jFormat(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.getTime();
	}
}
