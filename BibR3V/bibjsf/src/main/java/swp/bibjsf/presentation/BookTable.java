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

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import swp.bibcommon.Book;
import swp.bibjsf.businesslogic.BookHandler;
import swp.bibjsf.exception.DataSourceException;
import swp.bibjsf.renderer.BookTagContent;
import swp.bibjsf.renderer.BookTagPrinter;
import swp.bibjsf.renderer.Content;
import swp.bibjsf.utils.Messages;

/**
 * A table form for books. Supports selection, sorting, filtering,
 * CSV import and export, and lazy loading. Editing is not supported.
 * There will be extra forms for that.
 *
 * @author koschke
 *
 */
@ManagedBean
@SessionScoped
public class BookTable extends TableForm<Book> {

    private static final long serialVersionUID = -4323158046564482542L;

    public BookTable() throws DataSourceException {
        super(BookHandler.getInstance());
        try {
            model = new TableDataModel<Book>(handler);
        } catch (DataSourceException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    Messages.get("noBooksFound"), e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /********************************************************************
     * PDF generation
     *******************************************************************/

    /* (non-Javadoc)
     * @see swp.bibjsf.presentation.TableForm#getPrinter()
     */
    @Override
	protected BookTagPrinter getPrinter() {
		return new BookTagPrinter();
	}

    /* (non-Javadoc)
     * @see swp.bibjsf.presentation.TableForm#getContent()
     */
    @Override
	protected List<Content> getContent() {
		List<Content> idcontent = new ArrayList<Content>();
        for(Book b : selectedElements) {
            idcontent.add(new BookTagContent(b.getId(), b.getTitle(), b.getIndustrialIdentifier()));
        }
		return idcontent;
	}
}

