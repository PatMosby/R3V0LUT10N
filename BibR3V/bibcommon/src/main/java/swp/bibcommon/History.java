package swp.bibcommon;
import java.io.Serializable;
import java.util.Date;


public class History  {
 
	

	public String Date;
	public String ReaderID;
	public String BookID;
 
	public String getDate() {
		return Date;
	}
	public void setDate(String expireDate) {
		Date = expireDate;
	}
	public String getBook() {
		return BookID;
	}
	public void setBook(String typ) {
	  BookID = typ;
	}
	
	public String getReader(){
		return ReaderID;
	}
	public void setReader(String charges) {
		ReaderID = charges;
	}
 }