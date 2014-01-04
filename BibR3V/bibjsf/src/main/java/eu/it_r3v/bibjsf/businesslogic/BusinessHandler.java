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

package eu.it_r3v.bibjsf.businesslogic;

import java.io.Serializable;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import eu.it_r3v.bibjsf.exception.DataSourceException;
import eu.it_r3v.bibjsf.persistence.Data;
import eu.it_r3v.bibjsf.persistence.Persistence;

/**
 * Implements business logic of library. Different specialized business handlers
 * are derived from this class. This class implements the shared features.
 *
 * @author K. Hölscher
 *
 */
public abstract class BusinessHandler implements Serializable {

    /**
     * Unique ID for serialization.
     */
    private static final long serialVersionUID = -6995431155069989782L;

    /**
     * The logger to record all events.
     */
    protected final Logger logger = Logger.getLogger(BusinessHandler.class);

    /**
     * The persistence component.
     */
    protected final Persistence persistence;

    /**
     * The CSV separator for files containing uploaded data.
     */
    protected static final String columnSeparator = ";";

    /**
     * Private constructor.
     *
     * @throws NamingException
     * 			if there is an exception during the look-up of the JNDI-names.
     *
     * @throws DataSourceException
     *            if there is a problem with the persistence.
     */
    protected BusinessHandler() throws DataSourceException, NamingException {
        persistence = new Data();
    }

    /**
     * Thrown if an uploaded file cannot be completely processed.
     *
     * @author koschke
     *
     */
    public static class InputException extends Exception {

        /**
         * Unique number for serialization.
         */
        private static final long serialVersionUID = 6487027977226496026L;

        public InputException() {
            super();
        }

        public InputException(String message) {
            super(message);
        }
    }

    /**
     * An event tag that tells a listeners that all data may have been changed.
     * Intended to be used as parameter 'property' in notifyListeners.
     * In case of such an event, the old and new value of the event are both null.
     */
    public final static String ALLDATA = "ALLDATA";

    /**
     * An event tag that tells a listener that a new entry was added that did not
     * exist before. Intended to be used as parameter 'property' in notifyListeners.
     * In case of such an event, the old value of the event will be null and the
     * new value will be the added entry.
     */
    public final static String NEWENTRY = "NEWENTRY";

    /**
     * Concatenates and returns all strings in 'strings'.
     *
     * @param strings
     * @return the concatenation of all strings in 'strings'.
     */
    protected String toString(String[] strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);
            sb.append(columnSeparator);
        }
        return sb.toString();
    }

}
