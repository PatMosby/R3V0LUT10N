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

package swp.bibjsf.businesslogic;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.naming.NamingException;

import swp.bibcommon.Reader;
import swp.bibjsf.exception.BusinessElementAlreadyExistsException;
import swp.bibjsf.exception.DataSourceException;
import swp.bibjsf.utils.Constraint;
import swp.bibjsf.utils.Messages;
import swp.bibjsf.utils.OrderBy;

/**
 * A business handler for all reader data. It implements the business
 * rules that apply to readers.
 *
 * @author koschke
 *
 */
public class ReaderHandler extends BusinessObjectHandler<Reader> {

    /**
	 * Unique ID for serialization.
	 */
	private static final long serialVersionUID = -5653849921779676759L;
	/**
     * The only instance of this class (singleton).
     */
    private static volatile ReaderHandler instance;

    protected ReaderHandler() throws DataSourceException, NamingException {
        super();
    }

    /**
     * Returns unique instance of this class.
     *
     * @return unique instance of this class
     *
     * @throws DataSourceException
     *      thrown in case of problems with the data source
     */
    public static synchronized ReaderHandler getInstance()
            throws DataSourceException {

        if (instance == null) {
            try {
                instance = new ReaderHandler();
            } catch (Exception e) {
                throw new DataSourceException(e.getMessage());
            }
        }
        return instance;
    }

    /**
     * Returns a list of readers of the library fulfilling the given constraints.
     * @param constraints a list of the constraints for this query
     * @param order
     * @return list of all readers of the library fulfilling all constraints
     * @throws DataSourceException
     *             thrown if there is any problem with the data source
     */
    @Override
    public synchronized List<Reader> get(List<Constraint> constraints, final int from, final int to, List<OrderBy> order)
            throws DataSourceException {
        return persistence.getReaders(constraints, from, to, order);
    }
    
    /**
     * Adds reader to database. Reader must not yet exist.
     *
     * @param reader
     *            new reader to be added
     * @return the ID of the added reader
     * @throws DataSourceException
     *             in case of problems with data source
     * @throws BusinessElementAlreadyExistsException
     *             if the reader exists already in the database
     */
    @Override
    public synchronized int add(Reader reader) throws DataSourceException,
        BusinessElementAlreadyExistsException {
        logger.info("add reader: " + reader);
        if (reader.hasId() && persistence.getReader(reader.getId()) != null) {
            logger.info("reader already exists and could not be added: "
                    + reader);
            throw new BusinessElementAlreadyExistsException(Messages.get("readerexists") + " "
            		+ Messages.get("id") + " = " + reader.getId());
        }
        // if reader has no assigned user name, he/she gets a default user name
        final String username = reader.getUsername();
		if (username == null || username.isEmpty()) {
        	reader.setUsername(defaultUsername(reader));
        }
        // assert: reader has a user name
        if (persistence.getReaderByUsername(username) != null) {
        	throw new BusinessElementAlreadyExistsException(Messages.get("readerexists")
        			+ " " + Messages.get("username") + " = " + username);
        }
        int result = persistence.addReader(reader);
        if (result < 0) {
            throw new DataSourceException(Messages.get("readernotadded"));
        }
        return result;
    }
    
    /**
     * Adds Librarian to database. Librarian must not yet exist.
     *
     * @param librarian
     *            new librarian to be added
     * @return the ID of the added librarian
     * @throws DataSourceException
     *             in case of problems with data source
     * @throws BusinessElementAlreadyExistsException
     *             if the librarian exists already in the database
     */
    public synchronized int addLibrarian(Reader reader) throws DataSourceException,
        BusinessElementAlreadyExistsException {
        logger.info("add librarian: " + reader);
        if (reader.hasId() && persistence.getReader(reader.getId()) != null) {
            logger.info("librarian already exists and could not be added: "
                    + reader);
            throw new BusinessElementAlreadyExistsException(Messages.get("readerexists") + " "
            		+ Messages.get("id") + " = " + reader.getId());
        }
        // if reader has no assigned user name, he/she gets a default user name
        final String username = reader.getUsername();
		if (username == null || username.isEmpty()) {
        	reader.setUsername(defaultUsername(reader));
        }
        // assert: reader has a user name
        if (persistence.getReaderByUsername(username) != null) {
        	throw new BusinessElementAlreadyExistsException(Messages.get("readerexists")
        			+ " " + Messages.get("username") + " = " + username);
        }
        int result = persistence.addLibrarian(reader);
        if (result < 0) {
            throw new DataSourceException(Messages.get("readernotadded"));
        }
        return result;
    }

	/**
	 * Creates a default username as a concatenation of the the user's first letter
	 * of his/her first name and the last name all in lower case. If a user has neither
	 * first nor last name, a random name is generated.
	 *
	 * @param reader reader whose user name is to be created
	 * @return default user name
	 */
	private String defaultUsername(Reader reader) {
		final String firstname = reader.getFirstName();
		final String lastname  = reader.getLastName();
		if (firstname != null && !firstname.isEmpty()) {
			if (lastname != null && !lastname.isEmpty()) {
				return (firstname.charAt(0) + lastname).toLowerCase();
			} else {
				return firstname.toLowerCase();
			}
		} else {
			if (lastname != null && !lastname.isEmpty()) {
				return lastname.toLowerCase();
			} else {
				// neither first nor last name given
				return Messages.get("user") + ((Integer)(int)Math.abs(Math.random() * 1000 + 1)).toString();
			}
		}
	}

    /**
     * Reads all CSV data from input and stores them into the reader table.
     *
     * @param input the stream from where to read the CSV data
     * @throws InputException thrown in case the input data cannot be processed completely
     * @throws DataSourceException thrown if the data cannot be stored in the data source
     * @return the number of read and inserted readers
     */
    @Override
    public synchronized int importCSV(InputStream input) throws DataSourceException {
        return persistence.importReaders(input);
    }

    /* (non-Javadoc)
     * @see swp.bibjsf.businesslogic.BusinessObjectHandler#update(int, java.lang.Object)
     */
    @Override
    public synchronized int update(int ID, Reader newValue) throws DataSourceException {
        return persistence.updateReader(ID, newValue);
    }


    /* (non-Javadoc)
     * @see swp.bibjsf.businesslogic.BusinessObjectHandler#export(java.io.OutputStream)
     */
    @Override
    public synchronized void exportCSV(OutputStream outStream) throws DataSourceException {
        persistence.exportReaders(outStream);
    }

    /**
     * Deletes all elements in the data source.
     *
     * @param elements the elements to be deleted
     * @throws DataSourceException
     */
    @Override
    public synchronized void delete(List<Reader> elements) throws DataSourceException {
        persistence.deleteAll(elements);
    }

    /**
     * The number of elements fulfilling given constraints.
     * @param constraints the set of constraints to be fulfilled to be counted
     * @return number of elements fulfilling given constraints.
     * @throws DataSourceException
     */
    @Override
    public synchronized int getNumber(List<Constraint> constraints) throws DataSourceException {
        return persistence.getNumberOfReaders(constraints);
    }

    /**
     * Returns the element with the given id.
     *
     * @param id ID of the element to be retrieved
     * @return element with the given id
     * @throws DataSourceException
     */
    @Override
    public synchronized Reader get(int id) throws DataSourceException {
    	return persistence.getReader(id);
    }
    
    public synchronized Reader getReaderByUsername(String username) throws DataSourceException {
    	return persistence.getReaderByUsername(username);
    }

    /**
     * An instance of Reader acting as the prototype of objects handled by this handler.
     */
    private static Reader prototype = new Reader();

    /* (non-Javadoc)
     * @see swp.bibjsf.businesslogic.BusinessHandler#getPrototype()
     */
    @Override
    public synchronized Reader getPrototype() {
        return prototype;
    }

}
