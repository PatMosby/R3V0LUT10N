
package swp.bibcommon;

import java.io.Serializable;

/**
 * Enthält alle Attribute eines Magazines
 * @author Pupat
 *
 */
public class Magazine extends Medium implements Serializable {

	/**
	 * Editoren Liste
	 */
	private String editorList;
	
	/**
	 * ISSN des Magazines
	 */
	private String ISSN;
	
	/**
	 * Ausgabe des Magazines
	 */
	private int printType;
	
	/**
	 * Seitenzahl
	 */
	private int pageCount;
	
	/**
	 * Verlag des Magazines
	 */
	private String publisher;
	
	/**
	 * URL to preview this volume on the Google Books site.
	 */
	private String previewLink;
	/**
	 * 
	 */
}
