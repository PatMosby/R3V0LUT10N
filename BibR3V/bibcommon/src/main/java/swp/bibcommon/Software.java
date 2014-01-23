package swp.bibcommon;

import java.io.Serializable;
/**
 * Klasse Software mit allen Attributen
 * @author Pupat
 *
 */
public class Software extends Medium implements Serializable {
	
	private static final long serialVersionUID = -2835684051415448368L;
	
	/**
	 * Verlag der Software
	 */
	private String publisher;
	
	/**
	 * Anzahl der Datenträger
	 */
	private int media;

	
	/**
	 * Constructor required for DBUtils.
	 */
	public Software() {
	}
	
	/**
	 * Copy constructor.
	 *
	 * IMPORTANT NOTE: Do not forget to add an assignment for every new field.
	 *
	 * @param CD
	 *            the CD whose values are to be copied
	 */
	public Software(Software software){
		super(software);
		this.publisher      = copyString(software.publisher);
		this.media          = software.media;
	}
	
	
	/* ********************
	 * getter und setter
	 ***********************/
	 
	 	
	/**
	 * Gibt den Herausgeber zurück.
	 *
	 * @return Herausgeber der Software
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * Setzt den Herausgeber.
	 *
	 * @param publisher
	 *              Herausgeber der Software
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	/**
	 * Gibt die Anzahl der Datenträger zurück.
	 *
	 * @return datenträgeranzahl der Software
	 */
	public int getMedia() {
		return media;
	}
	/**
	 * Setzt die Anzahl der Datenträger.
	 *
	 * @param media
	 *              Anzahl der Datenträger
	 */
	public void setMedia(int media) {
		this.media = media;
	}

}
