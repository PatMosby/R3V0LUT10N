package swp.bibcommon;

import java.io.Serializable;
/**
 * Die Klasse Kassette repräsentiert eine Kassette in der Bibliothek. Sie enthält 
 * alle getter und setter Methoden und kann vom Client und vom Server verwendet werden
 * @author Pupat
 *
 */
public class Cassette extends Medium implements Serializable{

	private static final long serialVersionUID = -2835684051415448362L;
	
	/**
	 * Spieldauer
	 */
	private int playTime;
	
	/**
	 * Verlag
	 */
	private String publisher;
	
	/**
	 * Constructor required for DBUtils.
	 */
	public Cassette() {
	}
	
	/**
	 * Copy constructor.
	 *
	 * IMPORTANT NOTE: Do not forget to add an assignment for every new field.
	 *
	 * @param cassette
	 *            the cassette whose values are to be copied
	 */
	public Cassette(Cassette cassette){
		super(cassette);
		this.playTime       = cassette.playTime;
		this.publisher      = copyString(cassette.publisher);
	}
	
	/* ******************
	 * getter und setter
	 * ******************/
	
	/**
	 * Gibt die Spielzeit zurück.
	 *
	 * @return Spielzeit der Kassette
	 */
	public int getPlayTime() {
		return playTime;
	}

	/**
	 * Setzt die Spielzeit.
	 *
	 * @param note
	 */
	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}
	
	/**
	 * Gibt den Herausgeber zurück.
	 *
	 * @return Herausgeber der Kassette
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * Setzt den Herausgeber
	 *
	 * @param herausgeber
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
}
