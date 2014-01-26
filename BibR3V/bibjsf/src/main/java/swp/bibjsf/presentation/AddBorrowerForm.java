package swp.bibjsf.presentation;

import swp.bibjsf.businesslogic.BorrowHandler;
import swp.bibjsf.utils.Messages;

public class AddBorrowerForm extends BorrowerForm{

	
	
	
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
}
