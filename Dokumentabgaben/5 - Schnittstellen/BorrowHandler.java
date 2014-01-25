package eu.it_r3v.bibjsf.businesslogic;

import java.util.Date;
import java.util.List;

import eu.it_r3v.bibcommon.Reader;
import eu.it_r3v.bibcommon.Medium;

public class BorrowHandler {                                              

  private Date date;
  
  
  /**
   * Berechnet das Abgabedatum bei der Ausleihe. Berücksichtigt die
   * Öffnungszeiten der Bibliothek.
   * @return Das Abgabedatum
   */
  public Date calculateDate(){
    return date;
  }
  
  /**
   * Trägt das Medium in der Datenbank als ausgeliehen ein.
   * @param reader Der Ausleiher
   * @param mediumList Liste der Medien, die ausgeliehen werden
   */
  public void borrowMedium(Reader reader, List<Medium> mediumList){
    
  }
  
  /**
   * Berechnet die Mahngebühren.
   * @return Die Mahngebühren
   */
  public int calculateFees(){
    return 0;
  }
  
  /**
   * Gibt ein Medium zurück.
   * @param reader Der Ausleiher
   * @param mediumList Die Liste der Medienm die zurückgegeben werden.
   */
  public void returnMedium(Reader reader, List<Medium> mediumList){
    
  }
  
  /**
   * Überprüft die Fälligkeiten der ausgeliehenen Medien.
   * @return Die Liste der Überfälligen Medien
   */
  public List<Medium> checkOverdue(){
    return null ;
  }
  
  
  
}
