package swp.bibcommon;

import java.io.Serializable;
/**
 * Attribut der Klasse Film
 * @author Pupat
 *
 */
public class Film extends Medium implements Serializable{
	/**
	 * Liste der Regisseuren
	 */
	private String regisseur;
	
	/**
	 * Der Filmvertreiber
	 */
	private String filmPublisher;
	
	/**
	 * Spieldauer
	 */
	private int playTime;
	
	/**
	 * Anzahl der Datenträger
	 */
	private int medium;
	
	/**
	 * FSK
	 */
	private int fsk;
	
}
