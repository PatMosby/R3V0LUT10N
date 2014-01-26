package swp.bibjsf.presentation;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import swp.bibcommon.Borrower;
import swp.bibjsf.businesslogic.BorrowHandler;
import swp.bibjsf.utils.Messages;

public abstract class BorrowerForm extends BusinessObjectForm<Borrower>{

	
	private static final long serialVersionUID = 380665113958410405L;

	public abstract String getReaderID();

    public abstract void  setReaderID(final String readerID);
        
    public abstract String getMediumID();
          
    public abstract void setMediumID(final String mediumID);   	

}
