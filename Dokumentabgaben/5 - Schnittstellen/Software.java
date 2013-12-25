package swp.bibcommon;

import java.io.Serializable;
/**
 * Klasse Software mit allen Attributen
 * @author Pupat
 *
 */
public class Software extends Medium implements Serializable {
	
	/**
	 * Verlag der Software
	 */
	private String publisher;
	
	/**
	 * Anzahl der Datenträger
	 */
	private int medium;

}
