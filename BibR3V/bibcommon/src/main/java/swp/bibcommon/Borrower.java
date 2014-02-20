package swp.bibcommon;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//TODO Feldvariablen wieder privaten setzen
public class Borrower extends BusinessObject implements Serializable, Cloneable {
 
 private static final long serialVersionUID = -2835684051415448375L; 
 
  /**
     * Card ID of a reader.
     */
 public String readerID;
 
  /**
     * ID of a medium.
     */
 public String mediumID; //bookid
 private List<String> mediumIDs;
 public  String fines;
 public String date;
 
 public String getFines() {
	 
	return fines;
}

public void setFines(String fines) {
	System.out.println("REACHED FINNNNNNNNNNNNEEEESSSSS");
	this.fines = fines;
}

public String getDate() {
	return date;
}

public void setDate(String date) {
    System.out.println("REACHED setDate!!!!!:::::");
	this.date = date;
}


 
 
 public String getReaderID(){
	 return readerID;
 }
 
 public void setReaderID(String readerID){
	 System.out.println("REACHED rEADERidd1111111111");
	 this.readerID = readerID;
 }
 
 public String getBookID(){
	 return mediumID;
 }
 
 public void setBookID(String mediumID){
	 System.out.println("REACHED Bookidd1111111111");
	 this.mediumID = mediumID;
 }
 
 public List<String> getBookIDs(){
	 return mediumIDs;
 }
 
 public void setBookIDs(List<String> mediumID){
	 this.mediumIDs = mediumID;
 }
	/**
	 * Berechnet Mahngebühren eines Reader.
	 * 
	 * @param readerId
	 *            die eindeutige ID des Reader
	 * @return Mahngebühren des Reader
	 */
	public String calculateFines() {
		double dfines = 0.00;
		String fines = Double.toString(dfines);
	 this.fines = fines;
	 
		return fines;
	}
	
	/**
	 * Berechnet Rückgabedatum . Berücksichtigt Öffnungszeiten der Bibliothek.
	 * 
	 * @return date das Rückgabedatum
	 */
	public String calculateDate() {

		// Create an instance of SimpleDateFormat used for formatting
		// the string representation of date (month/day/year)
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();
		// Using DateFormat format method we can create a string
		// representation of a date with the defined format.
		String reportDate = df.format(today);
		this.date = reportDate;
		return reportDate;
	}

}
