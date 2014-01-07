package swp.bibcommon;

import java.io.Serializable;

/**
 * Die Klasse Sonstiges repräsentiert alle medien die sich sonst noch in
 * der Bibliothek befinden
 * @author Pupat
 *
 */
public class Other extends Medium implements Serializable{
	
	private static final long serialVersionUID = -2835684051415448371L;

	/**
	 * Autorenliste
	 */
	private String author;
	
	/**
	 * Hersteller
	 */
	private String producer;
	
	/**
	 * Constructor required for DBUtils.
	 */
	public Other() {
	}
	
	/**
	 * Copy constructor.
	 *
	 * IMPORTANT NOTE: Do not forget to add an assignment for every new field.
	 *
	 * @param CD
	 *            the CD whose values are to be copied
	 */
	public Other(Other other){
		super(other);
		this.author         = copyString(other.author);
		this.producer       = copyString(other.producer);
	}
	
	
	/* ********************
	 * getter und setter
	 *********************/

	public String getAutorenliste() {
		return author;
	}

	public void setAutorenliste(String author) {
		this.author = author;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

}
