package swp.bibjsf.presentation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import swp.bibjsf.businesslogic.BorrowHandler;
import swp.bibcommon.Borrower;
import swp.bibjsf.utils.Messages;

@ManagedBean(name= "myBean")
@SessionScoped
public class AddBorrowerForm extends BorrowerForm{

	Borrower borrower = new Borrower();
	
	
	
	@Override
	 public String save() {
	    	logger.debug("request to save reader " + ((element == null) ? "NULL" : element.toString()));
	    //	if (element != null) {
	    		try {
	    			Borrower borrower = new Borrower();
	    			BorrowHandler bh = BorrowHandler.getInstance();	    			
	    			return success(bh.add(borrower));
	    		} catch (Exception e) {
	    			return failure(e);
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
}
