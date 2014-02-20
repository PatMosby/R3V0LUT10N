
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
public class Book extends BusinessObject implements Serializable{

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
//	private boolean available;

	/**
	 * List of authors of this book. Authors are separated by ;.
	 */
	private String authors;
	/**
	 * Industrial identifier. May be ISBN-10 or ISBN-13 in case of a book. May
	 * be an ISSN in case of magazine.
	 */
	private String industrialIdentifier;


	/**
	 * Seitenzahl des Buches
	 */
	private int pageCount;;

	/**
	 * Ausgabe des Buches
	 */

	private String printType;
	/**
	 * Publisher of this book.
	 */
	private String publisher;

	/**
	 * URL to preview this volume on the Google Books site.
	 */
	private String previewLink;
	/**
	 * Anzahl der Datenträger
	 */
	private int media;
	
	/**
	 * Spieldauer des Hörbuches 
	 */
	private int playTime;

	/**
	 * Künsterliste
	 */
	private String artistList;
	
	/**
	 * Label der CD
	 */
	private String label;
	
	/**
	 * Anzahl der Titel
	 */
	private int titleCount;
	/**
	 * Editoren Liste
	 */
	private String editorList;
	

	/**
	 * Liste der Regisseuren
	 */
	private String regisseur;
	
	/**
	 * FSK
	 */
	private int fsk;
	
	/**
	 * Hersteller
	 */
	private String producer;

	/**
	 * Medientyp
	 */
	private String typ;
	
	public Double charges;
	
	/**
	 * Anzahl, wie oft ein Buch ausgeliehen wurde für die Statistik
	 *
	 */
	private int lendings;
	
	 
	/**
	 * Constructor required for DBUtils.
	 */
	public Book() {
	}
	
	/**
	 * Copy constructor.
	 *
	 * IMPORTANT NOTE: Do not forget to add an assignment for every new field.
	 *
	 * @param medium
	 *            the medium whose values are to be copied
	 */
	public Book(Book book) {
		// Keep these assignments in alphabetic order. It will ease
		// to check whether every field is actually set. If any field
		// is added (either to this class or any of its superclasses,
		// you need to add a corresponding assignment here.
		this.dateOfAddition       = copyString(book.dateOfAddition);
		this.dateOfPublication    = copyString(book.dateOfPublication);
		this.description          = copyString(book.description);
		this.id                   = book.id;
		this.imageURL             = copyString(book.imageURL);
		this.language             = copyString(book.language);
		this.location             = copyString(book.location);
		this.note                 = copyString(book.note);
		this.price                = book.price;
		this.subtitle             = copyString(book.subtitle);
		this.title                = copyString(book.title);
		this.categories           = copyString(book.categories);
		this.charges			  = book.charges;
		this.subcategories        = copyString(book.subcategories);
		this.authors              = copyString(book.authors);
		this.industrialIdentifier = copyString(book.industrialIdentifier);
		this.pageCount            = book.pageCount;
		this.previewLink          = copyString(book.previewLink);
		this.publisher            = copyString(book.publisher);
	    this.playTime             = book.playTime;
		this.media                = book.media;
		this.artistList           = copyString(book.artistList);
		this.label                = copyString(book.label);
		this.titleCount           = book.titleCount;
		this.editorList           = copyString(book.editorList);
//		this.ISSN                 = copyString(book.ISSN);
		this.printType            = book.printType;
		this.fsk                  = book.fsk;
		this.regisseur            = copyString(book.regisseur);
		this.producer             = copyString(book.producer);
		this.typ                  = copyString(book.typ);


	}
	
	/**
	 * Constructor with title and categories
	 *
	 * @param title
	 *            the mediums title
	 * @param categories
	 *            the mediums categories
	 */
	public Book(final String title, final String categories) {
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

	public String getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(String subcategories) {
		this.subcategories = subcategories;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getIndustrialIdentifier() {
		return industrialIdentifier;
	}

	public void setIndustrialIdentifier(String industrialIdentifier) {
		this.industrialIdentifier = industrialIdentifier;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPreviewLink() {
		return previewLink;
	}

	public void setPreviewLink(String previewLink) {
		this.previewLink = previewLink;
	}

	public int getMedia() {
		return media;
	}

	public void setMedia(int media) {
		this.media = media;
	}

	public int getPlayTime() {
		return playTime;
	}

	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}

	public String getArtistList() {
		return artistList;
	}

	public void setArtistList(String artistList) {
		this.artistList = artistList;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getTitleCount() {
		return titleCount;
	}

	public void setTitleCount(int titleCount) {
		this.titleCount = titleCount;
	}

	public String getEditorList() {
		return editorList;
	}

	public void setEditorList(String editorList) {
		this.editorList = editorList;
	}

/*	public String getISSN() {
		return ISSN;
	}

	public void setISSN(String iSSN) {
		ISSN = iSSN;
	}*/ 

	public String getRegisseur() {
		return regisseur;
	}

	public void setRegisseur(String regisseur) {
		this.regisseur = regisseur;
	}

	public int getFsk() {
		return fsk;
	}

	public void setFsk(int fsk) {
		this.fsk = fsk;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}
	
	/**
	 * Gibt zurück, ob das Medium verfügbar ist
	 * 
	 * @return true wenn verfügbar, sonst false
	 */
/*	public boolean getAvailable() {
		return available;
	}
	
	/**
	 * Setzt ob es verfügbar ist 
	 * 
	 * @param true wenn verfügbar, sonst false
	 *
	
	public void setAvailable(boolean available) {
		this.available = available;
	}*/
	
	/**
	 * Gibt die Mahngebühr eines Mediums zurück.
	 * @return charges
	 */
	
	public double getCharges() {
		return charges;
	}
	
	/**
	 * Setzt die Mahngebühr eines Mediums.
	 * @param charges
	 * 			Mahngebühr eines Mediums.
	 */
	public void setCharges(double charges) {
		this.charges = charges;
	}

	public int getLendings() {
		return lendings;
	}

	public void setLendings(int lendings) {
		this.lendings = lendings;
	}
	
}
