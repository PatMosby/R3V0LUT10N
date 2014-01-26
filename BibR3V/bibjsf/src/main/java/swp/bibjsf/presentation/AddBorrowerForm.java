package swp.bibjsf.presentation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import swp.bibjsf.businesslogic.BorrowHandler;
import swp.bibjsf.utils.Messages;

@ManagedBean(name= "myBean")
@SessionScoped
public class AddBorrowerForm extends BorrowerForm{

	
	
	
	@Override
	 public String save() {
	    	logger.debug("request to save reader " + ((element == null) ? "NULL" : element.toString()));
	    	if (element != null) {
	    		try {
	    			BorrowHandler bh = BorrowHandler.getInstance();	    			
	    			return success(bh.add(element));
	    		} catch (Exception e) {
	    			return failure(e);
	    		}
	    	} else {
	    		return failure(Messages.get("elementNotSet"));
	    	}
	    }
}
