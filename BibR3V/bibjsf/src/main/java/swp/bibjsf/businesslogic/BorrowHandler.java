package swp.bibjsf.businesslogic;

import java.util.Date;
import java.util.List;

import swp.bibcommon.Reader;
import swp.bibcommon.Medium;
import swp.bibjsf.persistence.Data;


/**
 * Ein Businesshandler der die Ausleihe realisiert.
 * 
 * @author Tobias
 */
public class BorrowHandler {

  private Date date;
  
  
  /**
   * Berechnet Rückgabedatum .
   * Berücksichtigt Öffnungszeiten der Bibliothek.
   * @return date das Rückgabedatum
   */
  public Date calculateDate(Medium medium){
    return date;
  }
  
  /**
   * Ordnet dem String 'id' einem existierenden Reader zu.
   * Ordnet dem String 'list' existierende Medien zu. 
   * @param id ist ID eines Readers
   * @param list enthält die ID's von einem oder mehreren Medien.
   */
  public void getInfo(String id, String list ){
 
  }
  
  /**
   * Fügt die Medien aus mediumList der Ausleihliste LENDING zu.
   * Reader wird in LENDING seinen ausgeliehenen Medien zugeordnet.
   * @param reader der Ausleihende
   * @param mediumList Liste der auszuleihenden Medien
   */
  public void borrowMedium(Reader reader, List<Medium> mediumList){
    
  }
  
  /**
   * Markiert Medien als ausgeliehen.
   * @param mediumList die zu markierenden Medien
   */
  public void setBorrowed(List<Medium> mediumList){
   
  }
  
  /**
   * Berechnet Mahngebühren eines Reader.
   * @param readerId die eindeutige ID des Reader
   * @return Mahngebühren des Reader
   */
  public double calculateFines(int readerId){
    return 0;
  }
  
  /**
   * Gibt eine Liste mit überfälligen Medien mithilfe der Ausleihliste LENDING zurück.
   * @param readerId die eindeutige ID des Reader
   * @return Liste der überfälligen Medien 
   */
  public List<Medium> overdueMediaList(int readerId){
    return null ;
  }
  
  
  /**
   * Prüft, ob es überfällige Medien gibt.
   * @return Boolean
   */
  public boolean isOverdue(){
 return true;
  }
  
  
  
}