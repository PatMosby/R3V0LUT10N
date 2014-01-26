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

import swp.bibcommon.Book;
import swp.bibjsf.businesslogic.BookHandler;
import swp.bibjsf.utils.Messages;

/**
 * Updates an existing book.
 *
 * @author koschke
 *
 */
@ManagedBean
@SessionScoped
public class ChangeBookForm extends BookForm {

    /**
     * Unique serial number.
     */
    private static final long serialVersionUID = -5486861182834276525L;

    /* (non-Javadoc)
     * @see swp.bibjsf.presentation.BookForm#getSelectable()
     *
     * @return always false (no selection supported)
     */
    @Override
    public boolean getSelectable() {
        return false;
    }

    /**
     * The ID of the book at the time when this form was opened
     * for a book (call of edit() below). This ID is needed because
     * a user could also change the ID of the book, but we need the
     * original ID of the book under change to identify the book
     * in the data source.
     */
    private int oldID;

    /**
     * Sets book to be changed to <code>newBook</code>.
     *
     * @param newBook new book modified by this form
     * @return "edit" as navigation case for faces-config.xml
     */
    public String edit(Book newBook) {
        element = newBook;
        oldID  = element.getId();
        return "edit";
    }

    /**
     * Updates modified book in the data source.
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
                return success(bh.update(oldID, element));

            } catch (Exception e) {
                return failure(e);
            }
        } else {
            return failure(Messages.get("elementNotSet"));
        }
    }

}
