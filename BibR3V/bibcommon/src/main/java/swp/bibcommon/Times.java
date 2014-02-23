package swp.bibcommon;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.Date;

/**
 * 
 * @author Damrow
 *
 */


public class Times extends BusinessObject implements Serializable, Cloneable {
 
	private static final long serialVersionUID = -8586476441486479552L;
	
	private static final String DayFormat = "EEEE";
	private static final String OpenFormat = "H:m";
	private static final String CloseFormat = "H:m";
	
	private static final SimpleDateFormat DayFormatter = new SimpleDateFormat(DayFormat);
	private static final SimpleDateFormat OpenFormatter = new SimpleDateFormat(OpenFormat);
	private static final SimpleDateFormat CloseFormatter = new SimpleDateFormat(CloseFormat);

	public String day;
	public String open;
	public String close;
 
	public Times(){		
	}
	
	public Times(Times times){
		this.day = copyString(times.day);
		this.open = copyString(times.open);
		this.close = copyString(times.close);
	}
	
	public Times(String day, String open, String close){
		this.day = copyString(day);
		this.open = copyString(open);
		this.close = copyString(close);
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
//	private String toStringDay(Date day) {
//		if (day == null) {
//			return null;
//		} else {
//			return DayFormatter.format(day);
//		}
//	}
//	private String toStringOpen(Date open) {
//		if (open == null) {
//			return null;
//		} else {
//			return OpenFormatter.format(open);
//		}
//	}
//	private String toStringClose(Date close) {
//		if (close == null) {
//			return null;
//		} else {
//			return CloseFormatter.format(day);
//		}
//	}
	
	/**
	 * Converts date string to java.util.Date.
	 *
	 * @param date date formatted as described by DateFormat
	 * @return the date or null if given date is null, empty, or malformed
	 */
//	private static Date toDateDay(String day) {
//		if (day == null || day.isEmpty()) {
//			return null;
//		} else {
//			try {
//				return DayFormatter.parse(day);
//			} catch (ParseException e) {
//				return null;
//			}
//		}
//	}
//	private static Date toDateOpen(String open) {
//		if (open == null || open.isEmpty()) {
//			return null;
//		} else {
//			try {
//				return OpenFormatter.parse(open);
//			} catch (ParseException e) {
//				return null;
//			}
//		}
//	}
//	private static Date toDateClose(String close) {
//		if (close == null || close.isEmpty()) {
//			return null;
//		} else {
//			try {
//				return CloseFormatter.parse(close);
//			} catch (ParseException e) {
//				return null;
//			}
//		}
//	}
	
	public String getDay() {
		if (day == null) {
			return null;
		} else {
			return day;
		}
	}
	public void setDay(String day) {
		if (day == null) {
			this.day = null;
		} else {
			this.day = day;
		}
	}
	
	public String getOpen(){
		if (open == null) {
			return null;
		} else {
			return open;
		}
	}
	public void setOpen(String open) {
		if (open == null) {
			this.open = null;
		} else {
			this.open = open;
		}
	}
	public String getClose() {
		if (close == null) {
			return null;
		} else {
			return close;
		}
	}
	public void setClose(String close) {
		if (close == null) {
			this.close = null;
		} else {
			this.close = close;
		}
	}
 }