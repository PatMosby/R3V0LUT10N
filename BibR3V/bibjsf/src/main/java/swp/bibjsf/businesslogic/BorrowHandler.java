package swp.bibjsf.businesslogic;

import java.util.Date;
import java.util.List;

import swp.bibcommon.Reader;
import swp.bibcommon.Book;
import java.util.List;



public class BorrowHandler {

  private Date date;
  
  
  /**
   * Calculates date of return for borrowed media.
   * Considers opening times of library.
   * @return Date of return
   */
  public Date calculateDate(){
    return date;
  }
  
  /**
   * Assigns the String id to a reader.
   * Assigns the String list to media.
   * @param id of the borrowing reader
   * @param list contains the ID's of one or more media
   */
  public void getInfo(String id, String list ){
	
  }
  
  /**
   * Adds reader to a list of borrower, if not already contained.
   * Reader gets mediumList as attribute.
   * @param reader The Borrower
   * @param mediumList List of borrowed media
   */
  public void borrowMedium(Reader reader, List<Book> mediumList){
    
  }
  
  /**
   * Marks medium as borrowed.
   * @param medium the medium to be marked
   */
  public void setBorrowed(Book book){
	  
  }
  
  /**
   * Calculates fines of a user.
   * @param userId unique identifier of a user
   * @return the fines
   */
  public double calculateFines(int userId){
    return 0;
  }
  
  /**
   * returns a list of overdue media from the mediaList of the reader .
   * @param userId unique identifier of a reader.
   * @return list of overdue media
   */
  public List<Book> overdueMediaList(Reader reader){
    return null ;
  }
  
  
  /**
   * 
   * @return
   */
  public boolean isOverdue(){
	return true;
  }
  
  
  
}
