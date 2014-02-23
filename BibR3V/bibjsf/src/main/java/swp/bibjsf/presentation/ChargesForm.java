package swp.bibjsf.presentation;

import java.util.Date;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import swp.bibcommon.Charges;
import swp.bibjsf.businesslogic.ChargesHandler;
import swp.bibjsf.utils.Messages;

/**
 * 
 * @author Damrow
 *
 */

public abstract class ChargesForm extends BusinessObjectForm<Charges>{

	
	private static final long serialVersionUID = 380665113958410405L;

	
	
	public abstract String getTyp();

    public abstract void  setTyp(final String typ);
        
    public abstract String getCharges();
          
    public abstract void setCharges(final String charges);  
    
    public abstract String getExpireDate();

    public abstract void  setExpireDate(final String expireDate);

}
