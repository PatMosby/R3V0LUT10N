package swp.bibjsf.businesslogic;

import javax.naming.NamingException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import swp.bibcommon.Charges;
import swp.bibcommon.Reader;
import swp.bibcommon.Book;

import swp.bibjsf.exception.BusinessElementAlreadyExistsException;
import swp.bibjsf.exception.DataSourceException;

import swp.bibjsf.utils.Constraint;

import swp.bibjsf.utils.OrderBy;



public class ChargesHandler extends BusinessObjectHandler<Charges>{

  private Date date;
  private static final long serialVersionUID = -5653849921779676752L;
  
  private static volatile ChargesHandler instance;
    
  protected ChargesHandler() throws DataSourceException, NamingException {
      super();
  }
  
  /**
   * Berechnet Rückgabedatum .
   * Berücksichtigt Öffnungszeiten der Bibliothek.
   * @return date das Rückgabedatum
   */
  public Date calculateDate(){
    return date;
  }
  
  /**
   * Ordnet dem String 'id' einem existierenden Reader zu.
   * Ordnet dem String 'list' existierende Medien zu. 
   * @param id ist ID eines Readers
   * @param list enthält die ID's von einem oder mehreren Medien.
   */
  public void getInfo(String id, String list ){
    System.out.println("" +id + ""+ list);
  }
  
  /**
   * Fügt die Medien aus mediumList der Ausleihliste LENDING zu.
   * Reader wird in LENDING seinen ausgeliehenen Medien zugeordnet.
   * @param reader der Ausleihende
   * @param mediumList Liste der auszuleihenden Medien
   */
  public void borrowMedium(Reader reader, List<Book> mediumList){
    
  }
  
  /**
   * Markiert Medien als ausgeliehen.
   * @param mediumList die zu markierenden Medien
   */
  public void setBorrowed(List<Book> mediumList){
   
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
  public List<Book> overdueMediaList(int readerId){
    return null ;
  }
  
  
  /**
   * Prüft, ob es überfällige Medien gibt.
   * @return Boolean
   */
  public boolean isOverdue(){
 return true;
  }
  
  
  
  public static synchronized ChargesHandler getInstance()
          throws DataSourceException {

      if (instance == null) {
          try {
              instance = new ChargesHandler();
          } catch (Exception e) {
              throw new DataSourceException(e.getMessage());
          }
      }
      return instance;
  }
  
  public synchronized List<Charges> get(List<Constraint> constraints, final int from, final int to, List<OrderBy> order)
          throws DataSourceException {
      return null; //persistence.getReaders(constraints, from, to, order);
  }
  
  /**
   * Adds reader to database. Reader must not yet exist.
   *
   * @param reader
   *            new reader to be added
   * @return the ID of the added reader
   * @throws DataSourceException
   *             in case of problems with data source
   * @throws BusinessElementAlreadyExistsException
   *             if the reader exists already in the database
   */
  @Override
  public synchronized int add(Charges charges) throws DataSourceException,
      BusinessElementAlreadyExistsException {
	  logger.debug("CHARGEHANDLER");
	  logger.debug(charges.getTyp()+ "," + charges.getCharges());
      try{
    	 // int retrn = persistence.addCharges(charges);
    	 // persistence.updateLending();
    	  persistence.addCharges(charges);
    	  return 1;}
      catch(Exception e)  { 
    	  logger.debug("FAIL!");
    	  return -1;
      }
  }

@Override
public Charges get(int id) throws DataSourceException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int getNumber(List<Constraint> constraints) throws DataSourceException {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int update(int ID, Charges newValue) throws DataSourceException {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public Charges getPrototype() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void delete(List<Charges> elements) throws DataSourceException {
	// TODO Auto-generated method stub
	
}

@Override
public void exportCSV(OutputStream outStream) throws DataSourceException {
	// TODO Auto-generated method stub
	
}

@Override
public int importCSV(InputStream inputstream) throws DataSourceException {
	// TODO Auto-generated method stub
	return 0;
}
  
}