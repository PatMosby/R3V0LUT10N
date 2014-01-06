package swp.bibcommon;

import java.io.Serializable;

/**
 * Klasse Hörbuch mit allen Attributen 
 * @author Pupat
 *
 */
public class Hörbücher extends Medium implements Serializable {
	/**
	 * Verlag des Hörbuches
	 */
	private String publisher;
	
	/**
	 * Anzahl der Datenträger
	 */
	private int medium;
	
	/**
	 * Spieldauer des Hörbuches 
	 */
	private int playTime;

}
