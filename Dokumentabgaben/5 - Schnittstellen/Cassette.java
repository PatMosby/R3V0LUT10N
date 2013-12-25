package swp.bibcommon;

import java.io.Serializable;
/**
 * Attribut der Klasse Kassette
 * @author Pupat
 *
 */
public class Cassette extends Medium implements Serializable{

	/**
	 * Spieldauer
	 */
	private int playTime;
	
	/**
	 * Verlag
	 */
	private String publisher;
	
}
