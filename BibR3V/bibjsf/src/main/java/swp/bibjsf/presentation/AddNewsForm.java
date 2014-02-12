package swp.bibjsf.presentation;  

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import swp.bibcommon.News;
import swp.bibjsf.businesslogic.NewsHandler;
import swp.bibjsf.utils.Messages;

@ManagedBean
@SessionScoped
public class AddNewsForm extends NewsForm{  
  
    private static final long serialVersionUID = -1726023834231615982L;
    
    
    /**
     * Constructor.
     */
    public AddNewsForm() {
        element = new News();
    }
    
    /**
     * Saves a new post in the data source.
     *
     * @return "success" in case of success, otherwise "error"; used
     * for navigation; cf. faces-config.xml.
     */
    @Override
    public String save() {
    	logger.debug("request to save new post " + ((element == null) ? "NULL" : element.toString()));
    	if (element != null) {
    		try {
    			NewsHandler nh = NewsHandler.getInstance();
    			element.setDateOfAddition(new Date());
    			int newID = nh.add(element);
    			reset();
    			return success(newID);

    		} catch (Exception e) {
    			return failure(e);
    		}
    	} else {
    		return failure(Messages.get("elementNotSet"));
    	}
    }
    
    /* (non-Javadoc)
     * @see swp.bibjsf.presentation.BusinessObjectForm#reset()
     */
    @Override
    public String reset() {
        News newElement = new News();
    	// set default date of addition (today)
        newElement.setDateOfAddition(new Date());
        mergeSelectedAttributes(newElement, element);
        element = newElement;
        return super.reset();
    }
} 