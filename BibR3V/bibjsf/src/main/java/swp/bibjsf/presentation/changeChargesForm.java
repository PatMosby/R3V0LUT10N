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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import swp.bibcommon.Charges;
import swp.bibjsf.businesslogic.ChargesHandler;
import swp.bibjsf.utils.Messages;

/**
 * A form to add charges.
 *
 * @author Damrow
 *
 */
@ManagedBean
@SessionScoped
public class changeChargesForm extends ChargesForm {
	
    /**
     * Unique serial number.
     */
    private static final long serialVersionUID = 8483587892305412383L;

    /**
     * Constructor.
     */
    public changeChargesForm() {
        element = new Charges();
    	// set default date of addition (today)
        double dfines = 0.0;
  	    String fines = Double.toString(dfines);
        element.setCharges(fines);
    }

    /**
     * Saves a book in the data source.
     *
     * @return "success" in case of success, otherwise "error"; used
     * for navigation; cf. faces-config.xml.
     */
    @Override
    public String save() {
    	logger.debug("request to save new book " + ((element == null) ? "NULL" : element.toString()));
    	if (element != null) {
    		try {
    			ChargesHandler ch = ChargesHandler.getInstance();
    			int newID = ch.add(element);
    			// reset so that the next book can be added without need to press a button
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
        Charges newElement = new Charges();
    	// set default date of addition (today)
        //newElement.setDateOfAddition(new Date());
        //mergeSelectedAttributes(newElement, element);
        element = newElement;
        return super.reset();
    }

	@Override
	public String getTyp() {
		return element.getTyp();
 	}

	@Override
	public void setTyp(String typ) {
		element.setTyp(typ);
		
	}

	@Override
	public String getCharges() {
		return element.getCharges();
	}

	@Override
	public void setCharges(String charges) {
		element.setCharges(charges);
	}
	
	@Override
	public String getExpireDate() {
		return element.getExpireDate();
	}

	@Override
	public void setExpireDate(String expireDate) {
		element.setExpireDate(expireDate);
	}

}
