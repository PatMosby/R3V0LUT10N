
package swp.bibcommon;

import java.io.Serializable;

/**
 * Repräsentiert die Klasse der Magazine. Enthält alle Attribute, sowie 
 * die getter und setter Methoden
 * @author Pupat
 *
 */
public class Magazine extends Medium implements Serializable {

	private static final long serialVersionUID = -2835684051415448367L;
	
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
	 * Constructor required for DBUtils.
	 */
	public Magazine() {
	}
	
	/**
	 * Copy constructor.
	 *
	 * IMPORTANT NOTE: Do not forget to add an assignment for every new field.
	 *
	 * @param CD
	 *            the CD whose values are to be copied
	 */
	public Magazine(Magazine magazine){
		super(magazine);
		this.editorList     = copyString(magazine.editorList);
		this.ISSN           = copyString(magazine.ISSN);
		this.printType      = magazine.printType;
		this.pageCount      = magazine.pageCount;
		this.publisher      = copyString(magazine.publisher);
		this.previewLink    = magazine.previewLink;
	}

	/* *****************
	 * getter und setter
	 ********************/
    /**
	 * Gibt die Editorenliste zurück.
	 *
	 * @return Editorenliste des Magazines
	 */
	public String getEditorList() {
		return editorList;
	}
	/**
	 * Setzt die Editorenliste.
	 *
	 * @param editorList
	 *              Editorenliste des Magazines
	 */
	public void setEditorList(String editorList) {
		this.editorList = editorList;
	}
	/**
	 * Gibt die ISSN zurück.
	 *
	 * @return ISSN des Magazines
	 */
	public String getISSN() {
		return ISSN;
	}
	/**
	 * Setzt die ISSN.
	 *
	 * @param iSSN
	 *              ISSN des Magazines
	 */
	public void setISSN(String iSSN) {
		ISSN = iSSN;
	}
	/**
	 * Gibt die Ausgabe zurück.
	 *
	 * @return Ausgabe des Magazines
	 */
	public int getPrintType() {
		return printType;
	}
	/**
	 * Setzt die Ausgabe.
	 *
	 * @param printType
	 *              Ausgabe des Magazines
	 */
	public void setPrintType(int printType) {
		this.printType = printType;
	}
	/**
	 * Gibt die Seitenanzahl zurück.
	 *
	 * @return Seitenanzahl des Magazines
	 */
	public int getPageCount() {
		return pageCount;
	}
	/**
	 * Setzt die Seitenzahl.
	 *
	 * @param pageCount
	 *              Seitenzahl des Magazines
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	/**
	 * Gibt den Herausgeber zurück.
	 *
	 * @return Herausgeber des Magazines
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * Setzt den Herausgeber.
	 *
	 * @param publisher
	 *              Herausgeber des Magazines
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	/**
	 * Gibt den Link zur Google Seite zurück.
	 *
	 * @return Link zur Google Seite
	 */
	public String getPreviewLink() {
		return previewLink;
	}
	/**
	 * Setzt den Link zu Google.
	 *
	 * @param previewLink
	 *              Link zu Google
	 */
	public void setPreviewLink(String previewLink) {
		this.previewLink = previewLink;
	}
}
