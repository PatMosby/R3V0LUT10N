package swp.bibcommon;

import java.io.Serializable;
/**
 * Repräsentiert die Klasse der CD. Besitzt alle Attribute einer CD und die
 * getter und setter Methoden
 * @author Pupat
 *
 */
public class CD extends Medium implements Serializable{
	
	private static final long serialVersionUID = -2835684051415448363L;
	
	/**
	 * Künsterliste
	 */
	private String artistList;
	
	/**
	 * Label der CD
	 */
	private String label;
	
	/**
	 * Spieldauer der CD
	 */
	private int playTime;
	
	/**
	 * Anzahl der Titel
	 */
	private int titleCount;
	
	/**
	 * Anzahl der Datenträger
	 */
	private int media;
	
	/**
	 * Constructor required for DBUtils.
	 */
	public CD() {
	}
	
	/**
	 * Copy constructor.
	 *
	 * IMPORTANT NOTE: Do not forget to add an assignment for every new field.
	 *
	 * @param CD
	 *            the CD whose values are to be copied
	 */
	public CD(CD cd){
		super(cd);
		this.artistList     = copyString(cd.artistList);
		this.label          = copyString(cd.label);
		this.playTime       = cd.playTime;
		this.titleCount     = cd.titleCount;
		this.media          = cd.media;
	}
	
	/* ******************
	 * getter und setter
	 * ******************/
	
	/**
	 * Gibt die Spielzeit zurück.
	 *
	 * @return Spielzeit der CD
	 */
	public int getPlayTime() {
		return playTime;
	}

	/**
	 * Setzt die Spielzeit
	 *
	 * @param spielzeit
	 */
	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}
	
	/**
	 * Gibt die Künstlerliste zurück.
	 *
	 * @return Künstlerliste der CD
	 */
	public String getArtistList() {
		return artistList;
	}

	/**
	 * Setzt die Künstlerliste
	 *
	 * @param künstlerliste
	 */
	public void setArtistList(String artistList) {
		this.artistList = artistList;
	}
	
	/**
	 * Gibt das Label zurück.
	 *
	 * @return label der CD
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Setzt das Label
	 *
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Gibt die Titelanzahl zurück.
	 *
	 * @return titelanzahl der CD
	 */
	public int getTitleCount() {
		return titleCount;
	}

	/**
	 * Setzt die titelanzahl
	 *
	 * @param titelanzahl
	 */
	public void setTitleCount(int titleCount) {
		this.titleCount = titleCount;
	}
	
	/**
	 * Gibt die Anzahl der Datenträger zurück.
	 *
	 * @return datenträgeranzahl der CD
	 */
	public int getMedia() {
		return media;
	}

	/**
	 * Setzt die datenträgeranzahl
	 *
	 * @param datenträgeranzahl
	 */
	public void setMedia(int media) {
		this.media = media;
	}
	
}
