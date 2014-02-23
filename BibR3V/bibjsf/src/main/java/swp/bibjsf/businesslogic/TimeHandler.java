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

import swp.bibcommon.Times;

import swp.bibjsf.exception.BusinessElementAlreadyExistsException;
import swp.bibjsf.exception.DataSourceException;

import swp.bibjsf.utils.Constraint;
import swp.bibjsf.utils.Messages;
import swp.bibjsf.utils.OrderBy;

/**
 * Book handler fulfilling requests related to books.
 *
 * @author Dierk Lüdemann, Karsten Hölscher, Rainer Koschke
 *
 */
public class TimeHandler extends BusinessObjectHandler<Times> {

    /**
	 * Unique ID for serialization.
	 */
	private static final long serialVersionUID = 7540662453149221843L;
	
	/**
     * Is a singleton.
     *
     */
    private static volatile TimeHandler instance;


    protected TimeHandler() throws DataSourceException, NamingException {
        super();
        this.timesList = getMonday();
    }
    
    private List<String> getMonday() throws DataSourceException {
		return persistence.getMonday();
	}

	public List<String> timesList;

	public List<String> getList(){
		return this.timesList;
	}
	
	public void setList(List<String> list){
		this.timesList = list;
	}
    /**
     *
     * Returns the one possible instance of this class. If there is no instance, a new one is created. (Singleton)
     *
     * @return the one instance of this class.
     *
     * @throws DataSourceException
     * 				is thrown if there are issues with the persistence component.
     */
    public static synchronized TimeHandler getInstance()
            throws DataSourceException {

        if (instance == null) {
            try {
                instance = new TimeHandler();
            } catch (Exception e) {
                throw new DataSourceException(e.getMessage());
            }
        }
        return instance;
    }

    /* (non-Javadoc)
     * @see swp.bibjsf.businesslogic.BusinessObjectHandler#add(java.lang.Object)
     */
    @Override
    public synchronized int add(Times times) throws DataSourceException,
        BusinessElementAlreadyExistsException {
    	logger.info("add times: " + times);
        int result = persistence.addTimes(times);
        if (result < 0) {
            throw new DataSourceException(Messages.get("timesAdditionFailure"));
        }
        return result;
    }

    /**
     * Updates book.
     *
     * @param book book to be updated
     * @return the number of updated books
     * @throws DataSourceException in case of problems with the data source
     */
    public synchronized int update(Times times) throws DataSourceException {
        logger.info("update times " + times.toString());
        return persistence.updateTime(times);
    }

    /**
	 * An instance of Times acting as the prototype of objects handled by this handler.
	 */
	private static Times prototype = new Times();

	/* (non-Javadoc)
	 * @see swp.bibjsf.businesslogic.BusinessHandler#getPrototype()
	 */
	@Override
	public synchronized Times getPrototype() {
	    return prototype;
	}

    @Override
    public void delete(List<Times> elements) throws DataSourceException {
        persistence.deleteAll(elements);
    }

	@Override
	public Times get(int id) throws DataSourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Times> get(List<Constraint> constraints, int from, int to,
			List<OrderBy> order) throws DataSourceException {
		return null;
	}

	@Override
	public int getNumber(List<Constraint> constraints)
			throws DataSourceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(int ID, Times newValue) throws DataSourceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void exportCSV(OutputStream outStream) throws DataSourceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int importCSV(InputStream inputstream) throws DataSourceException {
		// TODO Auto-generated method stub
		return 0;
	}

}
