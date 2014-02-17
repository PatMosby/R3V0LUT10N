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

import swp.bibcommon.Borrower;
import swp.bibcommon.Reader;
import swp.bibjsf.businesslogic.BorrowHandler;
import swp.bibjsf.businesslogic.ReaderHandler;
import swp.bibjsf.exception.DataSourceException;
import swp.bibjsf.renderer.Content;
import swp.bibjsf.renderer.IDCardPrinter;
import swp.bibjsf.renderer.IDContent;
import swp.bibjsf.renderer.Printer;
import swp.bibjsf.utils.Messages;

/**
 * A table form for readers. Supports selection, sorting, filtering,
 * file upload, CSV export, and lazy loading. Editing is not supported.
 * There will be extra forms for that.
 *
 * @author koschke
 *
 */
@ManagedBean
@SessionScoped
public class LendingTable extends TableForm<Borrower> {

    private static final long serialVersionUID = -4323158046564482542L;

    public LendingTable() throws DataSourceException {
        super(BorrowHandler.getInstance());
        try {
            model = new TableDataModel<Borrower>(handler);
        } catch (DataSourceException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    Messages.get("noLendingFound"), e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

	@Override
	protected Printer getPrinter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Content> getContent() {
		// TODO Auto-generated method stub
		return null;
	}

    

}

