package swp.bibcommon;

import java.io.Serializable;

/**
 * Oberklasse aller Medien
 * @author Pupat
 *
 */
public class Medium extends BusinessObject implements Serializable{

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
	private String industrialIdentifier;


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
	private String categorie;
	
	/**
	 * Nebenkategorien des Mediums
	 */
	private String subcategories;
	
	
}
