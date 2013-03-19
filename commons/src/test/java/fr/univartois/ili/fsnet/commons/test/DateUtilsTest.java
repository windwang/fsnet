package fr.univartois.ili.fsnet.commons.test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Test;

import fr.univartois.ili.fsnet.commons.utils.DateUtils;

public class DateUtilsTest {

	private final String DATE_TEST = "04/05/06";
	private final String DATE_TEST2 = "04/05/2006";
	private final String DATE_HOUR = "04/05/06 12:45";
	private final String ICS_DATE = "20060404T124500";
	private final String ICS_DATE2 = "20060404T124500Z";
	private final String ICS_DATE3 = "20060404T12";
	private final String ICS_DATE4 = "20060404";
	private final String DATE_HOUR2 = "04/05/06 12:42";
	private final String DATE_HOUR3 = "04/05/06 09:45";
	private final String DATE_HOUR4 = "01/05/06 12:45";
	private final int SUB = 3;
	private final String BEFORE = "01/01/1998";
	private final String AFTER = "01/01/2998";
	private final String US_DATE = "05/04/2006 12:45";

	/**
	 * test for the behaviour of method format(string s)
	 * @throws ParseException 
	 */
	@Test
	public void testFormat() throws ParseException {
		DateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy HH:mm",
				Locale.FRANCE);
		Date date = simpleFormat.parse(DATE_HOUR);
		Date generated = DateUtils.format(DATE_HOUR);
		assertEquals(date, generated);
	}

	/**
	 * test for the behaviour of method formatDate(string s)
	 * @throws ParseException 
	 */
	@Test
	public void testFormatDate() throws ParseException {
		DateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy",
				Locale.FRANCE);
		Date date = simpleFormat.parse(DATE_TEST);
		Date generated = DateUtils.formatDate(DATE_TEST);
		assertEquals(date, generated);
	}

	/**
	 * test for the behaviour of method compareToToday(Date date)
	 * @throws ParseException 
	 */
	@Test
	public void testCompareToToday() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), 0);
		Date today = calendar.getTime();
		Date before = DateFormat.getDateInstance(DateFormat.SHORT).parse(BEFORE);
		Date after = DateFormat.getDateInstance(DateFormat.SHORT).parse(AFTER);
		Integer tmp = DateUtils.compareToToday(today);
		tmp = DateUtils.compareToToday(after);
		assertTrue(tmp < 0);
		tmp = DateUtils.compareToToday(before);
		assertTrue(tmp > 0);
	}

	/**
	 * test for the behaviour of method renderDate(Date date)
	 * @throws ParseException 
	 */
	@Test
	public void testRenderDate() throws ParseException {
		Date before = DateFormat.getDateInstance(DateFormat.SHORT).parse(BEFORE);
		assertEquals(BEFORE, DateUtils.renderDate(before));

	}

	/**
	 * test for the behaviour of method renderDateWithHours(Date date)
	 * @throws ParseException 
	 */
	@Test
	public void testRenderDateWithHours() throws ParseException {
		Date date = DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).parse(DATE_TEST2);
		assertEquals(DATE_TEST2 + " 00:00", DateUtils.renderDateWithHours(date));
	}

	/**
	 * test for the behaviour of method renderDateForFullCalendar(Date date)
	 * @throws ParseException 
	 */
	@Test
	public void testRenderDateForFullCalendar() throws ParseException {
		Date before = DateFormat.getDateInstance(DateFormat.SHORT).parse(BEFORE);
		SimpleDateFormat usFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(before);
		assertEquals(usFormat.format(cal.getTime()), DateUtils.renderDateForFullCalendar(before));
	}

	/**
	 * test for the behaviour of method renderDBDate(Date date)
	 * @throws ParseException 
	 */
	@Test
	public void testRenderDBDate() throws ParseException {
		Date before = DateFormat.getDateInstance(DateFormat.SHORT).parse(BEFORE);
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(before);
		assertEquals(dbDateFormat.format(cal.getTime()), DateUtils.renderDBDate(before));
	}

	/**
	 * test for the behaviour of method renderDBDateWithSecond(Date date)
	 * @throws ParseException 
	 */
	@Test
	public void testRenderDBDateWithSecond() throws ParseException {
		Date before = DateFormat.getDateInstance(DateFormat.SHORT).parse(BEFORE);
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.FRANCE);
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(before);
		assertEquals(dbDateFormat.format(cal.getTime()), DateUtils.renderDBDateWithSecond(before));
	}

	/**
	 * test for the behaviour of method substractTimeToDate(Date date,int time,String type) with null date
	 */
	@Test
	public void testSubstractTimeToDateWithNullDate(){
		assertNull(DateUtils.substractTimeToDate(null, 3, "hour"));
	}

	/**
	 * test for the behaviour of method substractTimeToDate(Date date,int time,String type)
	 * @throws ParseException 
	 */
	@Test
	public void testSubstractTimeToDate() throws ParseException{
		DateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy HH:mm",
				Locale.FRANCE);
		Date date = simpleFormat.parse(DATE_HOUR);
		Date substractedDate1 = simpleFormat.parse(DATE_HOUR2);
		Date substractedDate2 = simpleFormat.parse(DATE_HOUR3);
		Date substractedDate3 = simpleFormat.parse(DATE_HOUR4);
		assertEquals(substractedDate1, DateUtils.substractTimeToDate(date, SUB, "minute"));
		assertEquals(substractedDate2, DateUtils.substractTimeToDate(date, SUB, "hour"));
		assertEquals(substractedDate3, DateUtils.substractTimeToDate(date, SUB, "other"));
	}

	/**
	 * test for the behaviour of method differenceBetweenTwoDateInMinutes(Date date1, Date date2)
	 * @throws ParseException 
	 */
	@Test
	public void testDifferenceBetweenTwoDateInMinutes() throws ParseException{
		DateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy HH:mm",
				Locale.FRANCE);
		Date date1 = simpleFormat.parse(DATE_HOUR);
		Date date2 = simpleFormat.parse(DATE_HOUR2);
		assertEquals(SUB, DateUtils.differenceBetweenTwoDateInMinutes(date1, date2));
	}

	/**
	 * test for the behaviour of method convertIcsTimestampToDate(String s)
	 * @throws ParseException 
	 */
	@Test
	public void testConvertIcsTimestampToDate() throws ParseException{
		DateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy HH:mm",
				Locale.FRANCE);
		Date date1 = simpleFormat.parse(DATE_HOUR);
		assertEquals(date1.toString(), DateUtils.convertIcsTimestampToDate(ICS_DATE).toString());
		assertEquals(date1.toString(), DateUtils.convertIcsTimestampToDate(ICS_DATE2).toString());
		assertFalse(date1.equals(DateUtils.convertIcsTimestampToDate(ICS_DATE3).toString()));
		assertFalse(date1.equals(DateUtils.convertIcsTimestampToDate(ICS_DATE4).toString()));
	}

	/**
	 * test for the behaviour of method toIcal4jFormat(date d)
	 * @throws ParseException 
	 */
	@Test
	public void testToIcal4jFormat() throws ParseException{
		SimpleDateFormat usFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date date1 = usFormat.parse(US_DATE);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		Date date = cal.getTime();
		assertEquals(date, DateUtils.toIcal4jFormat(date1));
	}
}
