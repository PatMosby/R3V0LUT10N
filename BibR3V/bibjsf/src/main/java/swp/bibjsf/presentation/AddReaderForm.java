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

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import swp.bibcommon.Reader;
import swp.bibjsf.businesslogic.ReaderHandler;
import swp.bibjsf.utils.Messages;

/**
 * AddReaderForm is a ManagedBean serving as model for reader/add.xhtml that
 * allows a user to create new readers.
 *
 * @author koschke
 *
 */
@ManagedBean
@SessionScoped
public class AddReaderForm extends ReaderForm {

    /**
     * Unique ID for serialization.
     */
    private static final long serialVersionUID = -494454216495818377L;
    
    

    public AddReaderForm() {
        super();
        resetReader();
    }

    /**
     * Creates a new reader whose data are to be filled in by this form.
     */
    private void resetReader() {
        element = new Reader();
        element.setEntryDate(new Date());
        element.setLastUse(element.getEntryDate());
    }

    /**
     * Saves a reader.
     *
     * @return "success" or "error" as a string. Is used for
     *         navigation. See faces-config.xml.
     */
    @Override
    public String save(String tablename) {
    	logger.debug("request to save reader " + ((element == null) ? "NULL" : element.toString()));
    	if (element != null) {
    		try {
    			ReaderHandler bh = ReaderHandler.getInstance();
    			return success(bh.add(element, tablename));
    		} catch (Exception e) {
    			return failure(e);
    		}
    	} else {
    		return failure(Messages.get("elementNotSet"));
    	}
    }

    @Override
    public String reset() {
        resetReader();
        return super.reset();
    }
}
