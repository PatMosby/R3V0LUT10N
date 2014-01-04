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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.it_r3v.Reader;
import eu.it_r3v.bibjsf.businesslogic.ReaderHandler;

/**
 * ChangeReaderForm is a ManagedBean serving as model for reader/change.xhtml that
 * allows a user to modify existing readers.
 *
 * @author koschke
 *
 */
@ManagedBean
@SessionScoped
public class ChangeReaderForm extends ReaderForm {

    /**
     * Unique ID for serialization.
     */
    private static final long serialVersionUID = 376217922786376395L;

    public ChangeReaderForm() {
        super();
    }

    /**
     * The ID of the reader at the time when this form was opened
     * for a reader (call of edit() below). This ID is needed because
     * a user could also change the ID of the reader, but we need the
     * original ID of the reader under change to identify the reader
     * in the data source.
     */
    private int oldID;

    /**
     * Sets reader to be changed to <code>newReader</code>.
     *
     * @param newReader new reader modified by this form
     * @return "edit" as navigation case for faces-config.xml
     */
    public String edit(Reader newReader) {
        element = newReader;
        oldID  = element.getId();
        return "edit";
    }

    /* (non-Javadoc)
     * @see eu.it_r3v.bibjsf.presentation.ReaderForm#save()
     */
    @Override
    public String save() {
        try {
            ReaderHandler bh = ReaderHandler.getInstance();
            return success(bh.update(oldID, element));
        } catch (Exception e) {
            return failure(e);
        }
    }

}
