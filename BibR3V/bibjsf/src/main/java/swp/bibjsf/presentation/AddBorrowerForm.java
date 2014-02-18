package swp.bibjsf.presentation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import swp.bibjsf.businesslogic.BorrowHandler;
import swp.bibcommon.Borrower;
import swp.bibjsf.persistence.Data;
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
	    			if (( (borrower.getReaderID() == null))) {
	    				return "save Methode Fehlgeschlagen : " +failure("Reader ID nicht vorhanden");
	    				
					}
	    			if (borrower.getBookID() == null ) {
	    				return "save Methode Fehlgeschlagen : " +failure("Medium ID nicht vorhanden");
					}else{
						logger.debug("Anomalie MediumID: "+borrower.getBookID() );
					}
	    			
	    			
	    			
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
		
		logger.debug("Getter readerID");
        return borrower.getReaderID();
    }


    public void setReaderID(final String readerID) {
    	if (Data.checkUserId(readerID)) { //Check, ob es die ReaderID gibt
			logger.debug("Id geprueft");
		   	System.out.println("setreader");
	    	borrower.setReaderID(trim(readerID));
	    	
	    	logger.debug("setter readerID: "+ readerID);
		}
 
    }
    
    
    public String getMediumID() {
        return borrower.getBookID();
    }

    
    public void setMediumID(final String mediumID) {
    	if (Data.checkMediumID(mediumID)) { //Check, ob es die MediumID gibt
	System.out.println("setmedium");
    borrower.setBookID(trim(mediumID));
	
}
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
