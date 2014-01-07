
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
	
	public String getEditorList() {
		return editorList;
	}

	public void setEditorList(String editorList) {
		this.editorList = editorList;
	}

	public String getISSN() {
		return ISSN;
	}

	public void setISSN(String iSSN) {
		ISSN = iSSN;
	}

	public int getPrintType() {
		return printType;
	}

	public void setPrintType(int printType) {
		this.printType = printType;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
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
}
