package swp.bibcommon;

import java.io.Serializable;
/**
 * Klasse CD mit allen Attributen
 * @author Pupat
 *
 */
public class CD extends Medium implements Serializable{
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
	private int medium;
}
