package swp.bibcommon;

import java.io.Serializable;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Die Klasse News repräsentiert die Nachrichten und Informationen
 * @author Bredehöft
 *
 */

public class News extends BusinessObject implements Serializable{
	
	private static final long serialVersionUID = 4142218730909517278L;
	/**
	 * The format to store dates as string.
	 */
	private static final String DateFormat = "d/MM/yyyy H:mm:ss";
	
	private static final String DateFormat2 = "ddMMHmmss";
	/**
	 * Used to convert strings to java.util.Date and vice versa.
	 */
	private static final SimpleDateFormat DateFormatter = new SimpleDateFormat(DateFormat);

	private static final SimpleDateFormat DateFormatter2 = new SimpleDateFormat(DateFormat2);
	
	private String dateOfAddition;
	
	private String news="Hier die neue Nachricht eingeben.";

	/**
	 * Constructor required for DBUtils.
	 */
	public News() {
	}
	
	/**
	 * Copy constructor.
	 *
	 * IMPORTANT NOTE: Do not forget to add an assignment for every new field.
	 *
	 * @param News
	 *            the News whose values are to be copied
	 */
	public News(News news){
		this.id 		= news.id;
		this.dateOfAddition         = news.toString(news.getDateOfAddition());
		this.news       = copyString(news.news);
	}
	
	/**
	 * Returns a copy of value.
	 *
	 * @param value string to be copied; may be null
	 * @return a new copy of value if value != null; null otherwise
	 */
	public static String copyString(String value) {
		if (value == null) {
			return null;
		} else {
			return new String (value);
		}
	}
	
	
	/**
	 * Converts date given as java.util.Date to a string formatted
	 * according to DateFormat.
	 *
	 * @param date date
	 * @return the date formatted as described by DateFormat
	 *   or null if given date is null.
	 */
	public String toString(Date date) {
		if (date == null) {
			return null;
		} else {
			return DateFormatter.format(date);
		}
	}
	
	public String toStringForInt(Date date) {
		if (date == null) {
			return null;
		} else {
			return DateFormatter2.format(date);
		}
	}
	
	
	/**
	 * Converts date string to java.util.Date.
	 *
	 * @param date date formatted as described by DateFormat
	 * @return the date or null if given date is null, empty, or malformed
	 */
	public static Date toDate(String date) {
		if (date == null || date.isEmpty()) {
			return null;
		} else {
			try {
				return DateFormatter.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}
	
	
	
	
	/* ********************
	 * getter und setter
	 *********************/
	
	/**
	 * Gibt die Nachrichten zurück.
	 *
	 * @return Nachrichten
	 */
	public String getNews() {
		return news;
	}
	
	/**
	 * Setzt die Nachricht.
	 *
	 * @param news
	 *              Nachricht
	 */
	public void setNews(String news) {
		this.news = news;
	}
	
	/**
	 * @return the dateOfAddition
	 */
	public Date getDateOfAddition() {
		if (dateOfAddition == null) {
			return null;
		} else {
			return toDate(dateOfAddition);
		}
	}
	
	/**
	 * @return the dateOfAddition
	 */
	public String getStringDateOfAddition() {
		if (dateOfAddition == null) {
			return null;
		} else {
			return dateOfAddition;
		}
	}

	/**
	 * @param dateOfAddition
	 *            the dateOfAddition to set
	 */
	public void setDateOfAddition(Date dateOfAddition) {
		if (dateOfAddition == null) {
			this.dateOfAddition = null;
		} else {
			this.dateOfAddition = toString(dateOfAddition);
			System.out.println("set date bla" + this.dateOfAddition);
			System.out.println();
		}
	}
}
