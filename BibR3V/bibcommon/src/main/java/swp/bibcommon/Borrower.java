package swp.bibcommon;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();
		// Using DateFormat format method we can create a string
		// representation of a date with the defined format.
		String reportDate = df.format(today);
		return reportDate;
	}

}
