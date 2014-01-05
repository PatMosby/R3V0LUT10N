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

package it_r3v.bibjsf.presentation;

import it_r3v.bibjsf.businesslogic.ReaderHandler;
import it_r3v.bibjsf.exception.DataSourceException;
import it_r3v.bibjsf.renderer.Content;
import it_r3v.bibjsf.renderer.IDCardPrinter;
import it_r3v.bibjsf.renderer.IDContent;
import it_r3v.bibjsf.utils.Messages;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import swp.bibcommon.Reader;

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
public class ReaderTable extends TableForm<Reader> {

    private static final long serialVersionUID = -4323158046564482542L;

    public ReaderTable() throws DataSourceException {
        super(ReaderHandler.getInstance());
        try {
            model = new TableDataModel<Reader>(handler);
        } catch (DataSourceException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    Messages.get("noReadersFound"), e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /********************************************************************
     * PDF generation
     *******************************************************************/

    /* (non-Javadoc)
     * @see it_r3v.bibjsf.presentation.TableForm#getPrinter()
     */
    @Override
	protected IDCardPrinter getPrinter() {
		return new IDCardPrinter();
	}

    /* (non-Javadoc)
     * @see it_r3v.bibjsf.presentation.TableForm#getContent()
     */
    @Override
	protected List<Content> getContent() {
		List<Content> idcontent = new ArrayList<Content>();
        for(Reader r : selectedElements) {
            idcontent.add(new IDContent(r.getFirstName(), r.getLastName(), ((Integer)r.getId()).toString()));
        }
		return idcontent;
	}

}

