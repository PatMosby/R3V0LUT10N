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

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


import swp.bibcommon.News;

import swp.bibjsf.businesslogic.NewsHandler;
import swp.bibjsf.exception.DataSourceException;

import swp.bibjsf.renderer.Content;
import swp.bibjsf.renderer.Printer;
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
public class NewsTable extends TableForm<News> {

    private static final long serialVersionUID = 3218901191825906316L;

	public NewsTable() throws DataSourceException {
        super(NewsHandler.getInstance());
        try {
            model = new TableDataModel<News>(handler);
            logger.debug("newsTable TRY ");
        } catch (DataSourceException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    Messages.get("noNewsFound"), e.getLocalizedMessage());
            logger.debug("newsTable CATCH ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    @Override
	protected Printer getPrinter() {
		return null;
	}

	@Override
	protected List<Content> getContent() {
		return null;
	}
	
}

