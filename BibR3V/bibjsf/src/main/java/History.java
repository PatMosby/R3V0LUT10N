
package swp.bibcommon;
import java.io.Serializable;
import java.util.Date;


public class History  {
 
	public String histID;
	public String histDate;
 
	public String getExpireDate() {
		return expireDate;
	}
	
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	
	public String getCharges(){
		return charges;
	}
	
	public void setCharges(String charges) {
		this.charges = charges;
	}
 }