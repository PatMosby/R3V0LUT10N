package swp.bibcommon;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Repräsentiert die Oberklasse aller Medien. Beinhaltet alle gemeinsamen
 * Attribute und die entsprechenden getter und setter Methoden
 * @author Pupat
 *
 */
public class Medium extends BusinessObject implements Serializable{

	private static final long serialVersionUID = -2835684051415448361L;
	
	/**
	 * The format to store dates as string.
	 */
	private static final String DateFormat = "yyyy-MM-dd";
	/**
	 * Used to convert strings to java.util.Date and vice versa.
	 */
	private static final SimpleDateFormat DateFormatter = new SimpleDateFormat(DateFormat);

	
	/**
	 * Titel des Medium.
	 */
	private String title;
	/**
	 * Untertitel des Mediums.
	 */
	private String subtitle;
	/**
	 * Industrial identifier. May be ISBN-10 or ISBN-13 in case of a book. May
	 * be an ISSN in case of magazine.
	 */

	/**
	 * An URL to an image file for the Medium.
	 */
	private String imageURL;
	/**
	 * A synopsis of the volume. The text of the description is formatted in
	 * HTML and includes simple formatting elements, such as b, i, and br tags.
	 */
	private String description;

	/**
	 * Any kind of note on the Medium, usually created by the librarian, for
	 * instance, regarding the damage status of the book.
	 */
	private String note;

	/**
	 * Sprache des Mediums
	 */
	private String language;


	/**
	 * Date of publication.
	 */
	private String dateOfPublication;

	/**
	 * The date at which this book was added to the library.
	 */
	private String dateOfAddition;
	/**
	 * Price of the book. Any currency, e.g., EUR or dollar.
	 */
	private java.math.BigDecimal price;
	/**
	 * The location of the book in the library.
	 */
	private String location;
	
	/**
	 * Kategorie des Mediums
	 */
	private String categories;
	
	/**
	 * Nebenkategorien des Mediums
	 */
	private String subcategories;
	
	/**
	* Boolean, ob das Medium verfügbar ist
	*/
	private boolean available;
	
	/**
	 * Constructor required for DBUtils.
	 */
	public Medium() {
	}
	
	/**
	 * Copy constructor.
	 *
	 * IMPORTANT NOTE: Do not forget to add an assignment for every new field.
	 *
	 * @param medium
	 *            the medium whose values are to be copied
	 */
	public Medium(Medium medium) {
		// Keep these assignments in alphabetic order. It will ease
		// to check whether every field is actually set. If any field
		// is added (either to this class or any of its superclasses,
		// you need to add a corresponding assignment here.
		this.dateOfAddition       = copyString(medium.dateOfAddition);
		this.dateOfPublication    = copyString(medium.dateOfPublication);
		this.description          = copyString(medium.description);
		this.id                   = medium.id;
		this.imageURL             = copyString(medium.imageURL);
		this.language             = copyString(medium.language);
		this.location             = copyString(medium.location);
		this.note                 = copyString(medium.note);
		this.price                = medium.price;
		this.subtitle             = copyString(medium.subtitle);
		this.title                = copyString(medium.title);
		this.categories           = copyString(medium.categories);
		this.subcategories        = copyString(medium.subcategories);
	}
	
	/**
	 * Constructor with title and categories
	 *
	 * @param title
	 *            the mediums title
	 * @param categories
	 *            the mediums categories
	 */
	public Medium(final String title, final String categories) {
		this.title                = copyString(title);
		this.categories           = copyString(categories);
	}
	
	/* ****************************************************************
	 * Utilities.
	 * ***************************************************************/

	
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
	private String toString(Date date) {
		if (date == null) {
			return null;
		} else {
			return DateFormatter.format(date);
		}
	}
	
	
	/**
	 * Converts date string to java.util.Date.
	 *
	 * @param date date formatted as described by DateFormat
	 * @return the date or null if given date is null, empty, or malformed
	 */
	private static Date toDate(String date) {
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
	
	
	
	/* ****************************************************************
	 * Setters and getters
	 * ***************************************************************/

	/**
	 * Returns a note about the medium as added by setNote().
	 *
	 * @return note about the medium
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note about this medium.
	 *
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}


	/**
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * @param subtitle
	 *            the subtitle to set
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}


	/**
	 * @return the dateOfPublication
	 */
	public Date getDateOfPublication() {
		if (dateOfPublication == null) {
			return null;
		} else {
			return toDate(dateOfPublication);
		}
	}

	/**
	 * @param dateOfPublication
	 *            the dateOfPublication to set
	 */
	public void setDateOfPublication(Date dateOfPublication) {
		if (dateOfPublication == null) {
			this.dateOfPublication = null;
		} else {
			this.dateOfPublication = toString(dateOfPublication);
		}
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
	 * @param dateOfAddition
	 *            the dateOfAddition to set
	 */
	public void setDateOfAddition(Date dateOfAddition) {
		if (dateOfAddition == null) {
			this.dateOfAddition = null;
		} else {
			this.dateOfAddition = toString(dateOfAddition);
		}
	}

	/**
	 * @return the price
	 */
	public java.math.BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Returns Medium title.
	 *
	 * @return medium title (may be null)
	 */
	public final String getTitle() {
		return title;
	}

	/**
	 * Sets medium title.
	 *
	 * @param title new title
	 */
	public final void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * @return a URL to an image file for the medium cover; may be null or empty
	 */
	public final String getImageURL() {
		return imageURL;
	}

	/**
	 * Sets the URL to an image file for the medium cover.
	 *
	 * @param imageURL
	 *            new URL
	 */
	public final void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}	
	
	/**
	 * Gibt die Kategorie zurück
	 * 
	 * @return categorie
	 */
	public final String getCategories() {
		return categories;
	}
	
	/**
	 * Setzt die Categorie 
	 * 
	 * @param die categorie die gesetzt werden soll
	 */
	
	public void setCategories(String categories) {
		this.categories = categories;
	}
	
	/**
	 * Gibt zurück, ob das Medium verfügbar ist
	 * 
	 * @return true wenn verfügbar, sonst false
	 */
	public boolean getAvailable() {
		return available;
	}
	
	/**
	 * Setzt ob es verfügbar ist 
	 * 
	 * @param true wenn verfügbar, sonst false
	 */
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	

	
}
