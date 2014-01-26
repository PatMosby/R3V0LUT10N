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

@ManagedBean
@SessionScoped
public class BorrowerForm extends BusinessObjectForm<Borrower>{

	
	private static final long serialVersionUID = 380665113958410405L;

	@Override
	 public String save(String table) {
	    	logger.debug("request to save reader " + ((element == null) ? "NULL" : element.toString()));
	    	if (element != null) {
	    		try {
	    			BorrowHandler bh = BorrowHandler.getInstance();	    			
	    			return success(bh.add(element, table));
	    		} catch (Exception e) {
	    			return failure(e);
	    		}
	    	} else {
	    		return failure(Messages.get("elementNotSet"));
	    	}
	    }
	
	public String wawa(){
		return null;
	}
	
	public String getReaderID() {
        return element.getReaderID();
    }

    /**
     * Sets last name.
     *
     * @param lastName
     *            new last name
     */
    public void setLReaderID(final String readerID) {
        element.setReaderID(readerID);
    } 
    
    public String getMediumID() {
        return element.getReaderID();
    }

    /**
     * Sets last name.
     *
     * @param lastName
     *            new last name
     */
    public void setMediumID(final String mediumID) {
        element.setMediumID(mediumID);
    } 
}
