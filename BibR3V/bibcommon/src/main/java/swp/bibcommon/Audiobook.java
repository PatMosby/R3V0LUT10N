package swp.bibcommon;

import java.io.Serializable;

/**
 * Repräsentiert ein Hörbuch. Hat alle Attribute eines Hörbuchs, sowie 
 * die getter und setter Methoden
 * @author Pupat
 *
 */
public class Audiobook extends Medium implements Serializable {
	
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
	 * @param CD
	 *            the CD whose values are to be copied
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

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
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
}
