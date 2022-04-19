package br.com.olimposistema.aipa.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Responsavel por fazer os serviços de conversão entre os tipos
 * datas do java
 * @author Tiago Luz
 * @version 1.0
 *
 */
public class ConvertDateService {
	
	public static final String formatIso = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	static final String formatIsoNotMili = "yyyy-MM-dd'T'HH:mm:ssXXX";
	
	static final String formatDateTimeBr = "dd/MM/yyyy HH:mm:ss";
	static final String formatDateTimeBrNotSecounds = "dd/MM/yyyy HH:mm";
	
	static final String formatDateBr = "dd/MM/yyyy";
	static final String formatTimeBr = "HH:mm:ss";
	public static final String formatTimeBrNotSecounds = "HH:mm";

	/**
	 * Faz a conversão de Date para Calendar
	 * @param date
	 * @return o valor date passado no formato calendar
	 */
	public static Calendar toCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
	}
	
	/**
	 * Faz a conversão de Calendar para um tipo java util Date
	 * @param calendar
	 * @return o valor do Calendar passado no formato de Date
	 */
	public static Date toDate(Calendar calendar) {
		if(calendar == null) return null;
		return calendar.getTime();
	}
	
	/**
	 * Faz a conversão de uma data em string iso para um tipo Calendar
	 * aceita os seguintes Formatos:
	 * 
	 *    2020-10-22T08:57:39.519-03:00, 
	 *    2020-10-22T16:25:12.755Z, 
	 *    2020-10-30T18:00:00-03:00, 
	 *    
	 *    09/05/1996 08:29:39, 
	 *    09/05/1996 08:36, 
	 *    09/05/1996, 
	 *    
	 *    08:53:22, 
	 *    08:25, 
	 * 
	 * 
	 * @param dateString pode estar nos seguintes formatos: 
	 *    2020-10-22T08:57:39.519-03:00, 
	 *    2020-10-22T16:25:12.755Z, 
	 *    2020-10-30T18:00:00-03:00, 
	 *    
	 *    09/05/1996 08:29:39, 
	 *    09/05/1996 08:36, 
	 *    09/05/1996, 
	 *    
	 *    08:53:22, 
	 *    08:25, 
	 * @throws ParseException 
	 */
	public static Calendar toCalendar(String dateString) throws ParseException {
		return toCalendar(toDate(dateString));
	}
	
	/**
	 * Faz a conversão de uma data em string iso para um tipo Date  
	 * <p>aceita os seguintes Formatos:<br><br>
     * <blockquote>
     * <pre>
	 *    2020-10-22T08:57:39.519-03:00<br>
	 *    2020-10-22T16:25:12.755Z<br>
	 *    2020-10-30T18:00:00-03:00<br><br> 
	 *    
	 *    09/05/1996 08:29:39<br>
	 *    09/05/1996 08:36<br>
	 *    09/05/1996 <br><br>
	 *    
	 *    08:53:22 <br>
	 *    08:25<br> 
 	 * </pre></blockquote>
	 * 
	 * 
	 * @param dateString 
	 * 
	 * <p>aceita os seguintes Formatos:
     * <blockquote>
     * <pre>
	 *    2020-10-22T08:57:39.519-03:00 
	 *    2020-10-22T16:25:12.755Z 
	 *    2020-10-30T18:00:00-03:00 
	 *    
	 *    09/05/1996 08:29:39 
	 *    09/05/1996 08:36
	 *    09/05/1996 
	 *    
	 *    08:53:22 
	 *    08:25 
 	 * </pre></blockquote> 
	 * @throws ParseException 
	 */
	public static Date toDate(String dateString) throws ParseException {
		return iso(dateString);
	}
	
	public static LocalDate toLocalDate(Date dateToConvert) {
		if(dateToConvert == null) return null;
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	public static LocalDate toLocalDate(Calendar calendar) {
		if(calendar == null) return null;
	    return toDate(calendar).toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	public static LocalDateTime toLocalDateTime(Date dateToConvert) {
		if(dateToConvert == null) return null;
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	public static LocalDateTime toLocalDateTime(Calendar calendar) {
		if(calendar == null) return null;
	    return toDate(calendar).toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	public static Date toDate(LocalDate dateToConvert) {
		if(dateToConvert == null) return null;
	    return java.util.Date.from(dateToConvert.atStartOfDay()
	      .atZone(ZoneId.systemDefault())
	      .toInstant());
	}
	
	public static Calendar toCalendar(LocalDate dateToConvert) {
		if(dateToConvert == null) return null;
	    return toCalendar(java.util.Date.from(dateToConvert.atStartOfDay()
	      .atZone(ZoneId.systemDefault())
	      .toInstant()));
	}
	
	public static Date toDate(LocalDateTime dateToConvert) {
		if(dateToConvert == null) return null;
	    return java.util.Date
	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	      .toInstant());
	}
	
	public static Calendar toCalendar(LocalDateTime dateToConvert) {
		if(dateToConvert == null) return null;
	    return toCalendar(java.util.Date
	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	      .toInstant()));
	}
	
	
	public static void setMeiaNoite(Date date) {
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
	}
	
	public static void set2359(Date date) {
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
	}
	
	private static Date iso(String dateString) throws ParseException {
		try {
			return new SimpleDateFormat(formatIso).parse(dateString);
		}catch (ParseException e) {
			return isoNotMili(dateString);
		}
	}
	
	private static Date isoNotMili(String dateString) throws ParseException {
		try {
			return new SimpleDateFormat(formatIsoNotMili).parse(dateString);
		}catch (ParseException e) {
			return dateTimeBr(dateString);
		}
	}
	
	private static Date dateTimeBr(String dateString) throws ParseException {
		try {
			return new SimpleDateFormat(formatDateTimeBr).parse(dateString);
		}catch (ParseException e) {
			return dateTimeBrNotSecounds(dateString);
		}
	}
	
	private static Date dateTimeBrNotSecounds(String dateString) throws ParseException {
		try {
			return new SimpleDateFormat(formatDateTimeBrNotSecounds).parse(dateString);
		}catch (ParseException e) {
			return dateBr(dateString);
		}
	}
	
	private static Date dateBr(String dateString) throws ParseException {
		try {
			return new SimpleDateFormat(formatDateBr).parse(dateString);
		}catch (ParseException e) {
			return timeBr(dateString);
		}
	}
	
	private static Date timeBr(String dateString) throws ParseException {
		try {
			return new SimpleDateFormat(formatTimeBr).parse(dateString);
		}catch (ParseException e) {
			return timeBrNotSecounds(dateString);
		}
	}
	
	private static Date timeBrNotSecounds(String dateString) throws ParseException {
		return new SimpleDateFormat(formatTimeBrNotSecounds).parse(dateString);
	}

	public static void setMeiaNoite(Calendar calendar) {
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
	
	
}
