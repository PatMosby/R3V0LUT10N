package swp.bibcommon;
import java.io.Serializable;
import java.util.Date;


public class Charges extends BusinessObject implements Serializable, Cloneable {
 
	private static final long serialVersionUID = -2835684051415448375L; 

	public String typ;
	public String charges;
	public String expireDate;
	public String tolerant;
 
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	
	public String getCharges(){
		return charges;
	}
	public void setCharges(String charges) {
		this.charges = charges;
	}
	public String getTolerant() {
		return tolerant;
	}
	public void setTolerant(String tolerant) {
		this.tolerant = tolerant;
	}
	
 }