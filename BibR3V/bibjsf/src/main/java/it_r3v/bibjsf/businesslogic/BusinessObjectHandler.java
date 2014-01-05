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

package it_r3v.bibjsf.businesslogic;

import it_r3v.bibjsf.exception.BusinessElementAlreadyExistsException;
import it_r3v.bibjsf.exception.DataSourceException;
import it_r3v.bibjsf.utils.Constraint;
import it_r3v.bibjsf.utils.OrderBy;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.naming.NamingException;

/**
 * Common superclass of all business handlers dealing with business objects
 * (Reader, Book). Introduces shared interface for these.
 *
 * @author koschke
 *
 * @param <BusinessObject>
 */
public abstract class BusinessObjectHandler<BusinessObject> extends BusinessHandler {

    /**
	 * Unique ID for serialization.
	 */
	private static final long serialVersionUID = -3910397141038577381L;

	protected BusinessObjectHandler() throws DataSourceException,
            NamingException {
        super();
    }

    /**
     * Returns the element with the given id.
     *
     * @param id ID of the element to be retrieved
     * @return element with the given id
     * @throws DataSourceException thrown if there is any problem with the data source
     */
    public abstract BusinessObject get(int id) throws DataSourceException;

    /**
     * Returns a list of elements fulfilling the given constraints.
     * @param constraints a list of the constraints for this query
     * @param order
     * @return list of all readers of the library fulfilling all constraints
     * @throws DataSourceException thrown if there is any problem with the data source
     */
    public abstract List<BusinessObject> get(List<Constraint> constraints, final int from, final int to, List<OrderBy> order)
       throws DataSourceException;

    /**
     * The number of elements fulfilling given constraints.
     * @param constraints the set of constraints to be fulfilled to be counted
     * @return number of elements fulfilling given constraints.
     * @throws DataSourceException thrown if there is any problem with the data source
     */
    public abstract int getNumber(List<Constraint> constraints) throws DataSourceException;

	/**
	 * Adds element to the data source. The element must not exist yet.
	 *
	 * @param element the element to be added as new
	 * @return the ID of the added element
	 * @throws DataSourceException thrown if there is any problem with the data source
	 * @throws BusinessElementAlreadyExistsException thrown if the element to be added exists already
	 */
	public abstract int add(BusinessObject element) throws DataSourceException, BusinessElementAlreadyExistsException;

    /**
     * Updates the element with given ID by all values in newValue.
     *
     * @param ID the unique identifier of the element to be updated
     * @param newValue an element holding all the values (attributes) used for the update
     * @return the number of updated elements (should be 1, but can be 0 if element was
     * not found or greater than 1 if the ID is not unique)
     * @throws DataSourceException thrown if there is any problem with the data source
     */
    public abstract int update(int ID, BusinessObject newValue) throws DataSourceException;

    /**
     * Returns a instance of the type of object this handler handles. This
     * information is needed for some generic solutions. Do not use this
     * result for anything but retrieving type information at runtime,
     * i.e., getPrototype().class.
     *
     * @return a prototype
     */
    public abstract BusinessObject getPrototype();

    /**
     * Deletes all elements in the data source.
     *
     * @param elements the elements to be deleted
     * @throws DataSourceException thrown if there is any problem with the data source
     */
    public abstract void delete(List<BusinessObject> elements) throws DataSourceException;

    /**
     * Exports all elements in the data source to outStream.
     *
     * @param outStream where to write the elements
     * @throws DataSourceException thrown if there is any problem with the data source
     */
    public abstract void exportCSV(OutputStream outStream) throws DataSourceException;

    /**
     * Reads all CSV data from input and stores them into the table.
     *
     * @param input the stream from where to read the CSV data
     * @throws DataSourceException thrown if the data cannot be stored in the data source
     * @return the number of read and inserted elements
     */
    public abstract int importCSV(InputStream inputstream) throws DataSourceException;

}
