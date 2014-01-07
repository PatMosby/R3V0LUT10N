package swp.bibcommon;

import java.io.Serializable;
/**
 * Repräsentiert die Klasse Film, welche in der Bibliothek vorhanden ist
 * besitzt alle Attribute und alle getter und setter Methoden eines Filmes
 * @author Pupat
 *
 */
public class Movie extends Medium implements Serializable{
	
	private static final long serialVersionUID = -2835684051415448364L;
	
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
	private int media;
	
	/**
	 * FSK
	 */
	private int fsk;
	
	/**
	 * Constructor required for DBUtils.
	 */
	public Movie() {
	}
	
	/**
	 * Copy constructor.
	 *
	 * IMPORTANT NOTE: Do not forget to add an assignment for every new field.
	 *
	 * @param CD
	 *            the CD whose values are to be copied
	 */
	public Movie(Movie movie){
		super(movie);
		this.regisseur      = copyString(movie.regisseur);
		this.filmPublisher  = copyString(movie.filmPublisher);
		this.playTime       = movie.playTime;
		this.media          = movie.media;
		this.fsk            = movie.fsk;
	}
	
}
