package swp.bibcommon;
import java.io.Serializable;
import java.util.Date;


public class Borrower extends BusinessObject implements Serializable, Cloneable {
 
 private static final long serialVersionUID = -2835684051415448375L; 
 
  /**
     * Card ID of a reader.
     */
 private String readerID = "2";
 
  /**
     * ID of a medium.
     */
 private String mediumID= "30001";
 
 public String getReaderID(){
	 return readerID;
 }
 
 public void setReaderID(String readerID){
	 this.readerID = readerID;
 }
 
 public String getMediumID(){
	 return mediumID;
 }
 
 public void setMediumID(String mediumID){
	 this.mediumID = mediumID;
 }

}
