package swp.bibcommon;

import java.io.Serializable;

/**
 * Repräsentiert ein Hörbuch. Hat alle Attribute eines Hörbuchs, sowie 
 * die getter und setter Methoden
 * @author Pupat
 *
 */
public class Audiobook extends Book implements Serializable {
	
	private static final long serialVersionUID = -2835684051415448366L;

	/**
	 * Verlag des Hörbuches
	 */
	private String publisher;
	
	/**
	 * Anzahl der Datenträger
	 */
	private int media;
	
	/**
	 * Spieldauer des Hörbuches 
	 */
	private int playTime;

	/**
	 * Constructor required for DBUtils.
	 */
	public Audiobook() {
	}
	
	/**
	 * Copy constructor.
	 *
	 * IMPORTANT NOTE: Do not forget to add an assignment for every new field.
	 *
	 * @param audiobook
	 *            the audiobook whose values are to be copied
	 */
	public Audiobook(Audiobook audiobook){
		super(audiobook);
		this.publisher      = copyString(audiobook.publisher);
	    this.playTime       = audiobook.playTime;
		this.media          = audiobook.media;
	}
	
	/* ********************
	 * getter und setter
	 **********************/
	/**
	 * Gibt die den Herausgeber zurück.
	 *
	 * @return herausgeber des Magazines
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * Setzt den Herausgeber.
	 *
	 * @param publisher
	 *              Herausgeber des Hörbuches
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	/**
	 * Gibt die Anzahl der Datenträger zurück.
	 *
	 * @return datenträgeranzahl des Hörbuches
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
	/**
	 * Gibt die Spielzeit zurück.
	 *
	 * @return spielzeit des Hörbuches
	 */
	public int getPlayTime() {
		return playTime;
	}
	/**
	 * Setzt die Spieldauer.
	 *
	 * @param playtime
	 *              Spieldauer des Hörbuches
	 */
	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}
}
