package swp.bibcommon;
import java.io.Serializable;
import java.util.List;


public class Borrower extends BusinessObject implements Serializable, Cloneable {
 
 private static final long serialVersionUID = -2835684051415448375L; 
 
  /**
     * Card ID of a reader.
     */
 private String readerID;
 
  /**
     * ID of a medium.
     */
 private String mediumID;
 private List<String> mediumIDs;
 
 public String getReaderID(){
	 return readerID;
 }
 
 public void setReaderID(String readerID){
	 this.readerID = readerID;
 }
 
 public String getBookID(){
	 return mediumID;
 }
 
 public void setBookID(String mediumID){
	 this.mediumID = mediumID;
 }
 
 public List<String> getBookIDs(){
	 return mediumIDs;
 }
 
 public void setBookIDs(List<String> mediumID){
	 this.mediumIDs = mediumID;
 }

}
