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

import swp.bibcommon.Times;
import swp.bibjsf.businesslogic.TimeHandler;
import swp.bibjsf.utils.Messages;

/**
 * A form to add books.
 *
 * @author koschke
 *
 */
@ManagedBean
@SessionScoped
public class SetTimesForm extends TimesForm {
	
    private static final long serialVersionUID = 5407579582351710994L;

	/**
     * Constructor.
     */
    public SetTimesForm() {
        element = new Times();
    	
    }

    /**
     * Saves a book in the data source.
     *
     * @return "success" in case of success, otherwise "error"; used
     * for navigation; cf. faces-config.xml.
     */
    @Override
    public String save() {
    	logger.debug("request to save new time " + ((element == null) ? "NULL" : element.toString()));
    	if (element != null) {
    		try {
    			TimeHandler th = TimeHandler.getInstance();
    			int newID = th.add(element);
    			// reset so that the next book can be added without need to press a button
    			//reset();
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
        Times newElement = new Times();
    	element = newElement;
        return super.reset();
    }
}
