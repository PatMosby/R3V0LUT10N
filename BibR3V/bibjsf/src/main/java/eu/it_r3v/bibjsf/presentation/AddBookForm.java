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

package eu.it_r3v.bibjsf.presentation;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.it_r3v.bibcommon.Book;
import eu.it_r3v.bibjsf.businesslogic.BookHandler;
import eu.it_r3v.bibjsf.utils.Messages;

/**
 * A form to add books.
 *
 * @author koschke
 *
 */
@ManagedBean
@SessionScoped
public class AddBookForm extends BookForm {

    /**
     * Unique serial number.
     */
    private static final long serialVersionUID = 8483587892305412383L;

    /**
     * Constructor.
     */
    public AddBookForm() {
        element = new Book();
    	// set default date of addition (today)
        element.setDateOfAddition(new Date());
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
    			BookHandler bh = BookHandler.getInstance();
    			int newID = bh.add(element);
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
     * @see eu.it_r3v.bibjsf.presentation.BusinessObjectForm#reset()
     */
    @Override
    public String reset() {
        Book newElement = new Book();
    	// set default date of addition (today)
        newElement.setDateOfAddition(new Date());
        mergeSelectedAttributes(newElement, element);
        element = newElement;
        return super.reset();
    }

}
