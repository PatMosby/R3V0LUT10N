package swp.bibjsf.presentation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import swp.bibjsf.businesslogic.BorrowHandler;
import swp.bibcommon.Borrower;
import swp.bibjsf.utils.Messages;

import java.sql.SQLException;
import java.util.List;

@ManagedBean(name= "myBean")
@SessionScoped
public class AddBorrowerForm extends BorrowerForm{

	Borrower borrower = new Borrower();
	
	
	
	@Override
	 public String save() {
	    	logger.debug("request to save borrower " + ((element == null) ? "NULL" : element.toString()));
	    //	if (element != null) {
	    		try {
	    			BorrowHandler bh = BorrowHandler.getInstance(); // Singelton
	    		
	    		return success(bh.add(borrower));
	    		//	return "success";
	    		} catch (Exception e) {
	    			return "save Methode Fehlgeschlagen : " +failure(e);
	    	//	}
	    	} //else {
	    		//return failure(Messages.get("elementNotSet"));
	    	//} 
	    }
	
	public void returnMedium() {
    	logger.debug("Rückgabe_Form ");
    //	if (element != null) {
    		try {
    			BorrowHandler bh = BorrowHandler.getInstance();	    			
    			bh.returnLending(borrower.getBookID());
    		} catch (Exception e) {
    			
    	//	}
    	} //else {
    		//return failure(Messages.get("elementNotSet"));
    	//}
    }
	
	
	
	public String getReaderID() {
		System.out.println("getreader");
        return borrower.getReaderID();
    }


    public void setReaderID(final String readerID) {
    	System.out.println("setreader");
    	borrower.setReaderID(trim(readerID));
    }
    
    
    public String getMediumID() {
        return borrower.getBookID();
    }

    
    public void setMediumID(final String mediumID) {
    	System.out.println("setmedium");
        borrower.setBookID(trim(mediumID));
    }
    
    public List<String> getMediumIDs() {
        return borrower.getBookIDs();
    }

    
    public void setMediumIDs(final List<String> mediumIDs) {
    	System.out.println("setmedium");
        borrower.setBookIDs(mediumIDs);
    }

	@Override
	public boolean getSelectable() {
		// TODO Auto-generated method stub
		return false;
	}
}
