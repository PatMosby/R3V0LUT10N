package swp.bibcommon;
import java.io.Serializable;
import java.util.Date;


public class Charges extends BusinessObject implements Serializable, Cloneable {
 
	private static final long serialVersionUID = -2835684051415448375L; 

	public String typ;
	public Double charges;
 
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	
	public Double getCharges(){
		return charges;
	}
	public void setCharges(Double charges) {
		this.charges = charges;
	}
 }