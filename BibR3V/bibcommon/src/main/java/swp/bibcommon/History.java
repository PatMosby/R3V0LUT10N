package swp.bibcommon;
import java.io.Serializable;
import java.util.Date;


public class History  {
 
	

	public String histDate;
	public String histReaderID;
	public String histBookID;
 
	public String getDate() {
		return histDate;
	}
	public void setDate(String expireDate) {
		histDate = expireDate;
	}
	public String getBookID() {
		return histBookID;
	}
	public void setBookID(String typ) {
		histBookID = typ;
	}
	
	public String getReaderID(){
		return histReaderID;
	}
	public void setReaderID(String charges) {
		histReaderID = charges;
	}
 }