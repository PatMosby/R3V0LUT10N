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
	
	/********************
	* getter und setter
	*********************/
	/**
	 * Gibt den regisseur zurück.
	 *
	 * @return regisseur des Filmes
	 */
	public String getRegisseur() {
		return regisseur;
	}
	/**
	 * Setzt den regisseur.
	 *
	 * @param regisseur
	 *              regisseur des Filmes
	 */
	public void setRegisseur(String regisseur) {
		this.regisseur = regisseur;
	}
		/**
	 * Gibt den Herausgeber zurück.
	 *
	 * @return Herausgeber des Filmes
	 */
	public String getFilmPublisher() {
		return filmPublisher;
	}

	/**
	 * Setzt den Herausgeber
	 *
	 * @param herausgeber des Filmes
	 */
	public void setFilmPublisher(String filmPublisher) {
		this.filmPublisher = filmPublisher;
	}
	/**
	 * Gibt die Spielzeit zurück.
	 *
	 * @return spielzeit des Filmes
	 */
	public int getPlayTime() {
		return playTime;
	}
	/**
	 * Setzt die Spieldauer.
	 *
	 * @param playtime
	 *              Spieldauer des Filmes
	 */
	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}
		/**
	 * Gibt die Anzahl der Datenträger zurück.
	 *
	 * @return datenträgeranzahl des Filmes
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
	 * Gibt die Altersbeschränkung zurück.
	 *
	 * @return Altersbeschränkung des Filmes
	 */
	public int getFsk() {
		return fsk;
	}
	/**
	 * Setzt die Altersbeschränkung.
	 *
	 * @param fsk
	 *              Altersbeschränkung
	 */
	public void setFsk(int fsk) {
		this.fsk = fsk;
	}
}
