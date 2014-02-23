/*
 * Copyright (c) 2013 AG Softwaretechnik, University of Bremen, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package swp.bibjsf.presentation;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;


import swp.bibjsf.businesslogic.BusinessHandler;
import swp.bibjsf.exception.DataSourceException;
import swp.bibjsf.utils.Messages;

/**
 * A form to add books.
 *
 * @author koschke
 *
 */
@ManagedBean
@SessionScoped
public class DurationForm extends BusinessHandler {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2586205953618665827L;
	String typ;
	String duration;
	
	public DurationForm() throws DataSourceException, NamingException {
		super();
	}
	
    /**
     * Saves a duration in the data source.
     *
     * @return "success" in case of success, otherwise "error"; used
     * for navigation; cf. faces-config.xml.
     */
    public String save() {
    	if (typ != null && duration != null) {
    		try {
    			persistence.setDuration(typ, duration);
    			// reset so that the next book can be added without need to press a button
    			reset();
    			return success(1);

    		} catch (Exception e) {
    			return failure(e);
    		}
    	} else {
    		return failure("Could not save!");
    	}
    }

    public void reset() {
        typ = null;
        duration = null;
    }

	public String getTyp() {
		return typ;
 	}

	public void setTyp(String typ) {
		this.typ = typ;
	}
	
		/**
	     * Called in case of successful addition of an element.
	     *
	     * @param result the number of added elements for the report
	     * @return "success" for navigation
	     */
	    protected String success(int result) {
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,  Messages.get("success"), Messages.get("id") + " = " + result);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	      //  logger.debug("saved " + element); 
	        return "success";
	    }

	    /**
	     * For failure of addition.
	     *
	     * @param e thrown exception
	     * @return "error" for navigation
	     */
	    protected String failure(Exception e) {
	        logger.debug("could not save: " + typ + " and " + duration);
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.get("savefailed") + " " + typ + " and " + duration,
	                                            e.getLocalizedMessage());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        return "error";	
	
	}
	    /**
	     * For failure of addition.
	     *
	     * @param message error message to be emitted
	     * @return "error" for navigation
	     */
	    protected String failure(final String message) {
	        logger.debug("could not save: " + message);
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.get("savefailed") + " " + typ,
	                                            message);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        return "error";
	    }

	public String getDuration() {
		return duration;
	}

	
	public void setDuration(String duration) {
		this.duration = duration;
	}

}
