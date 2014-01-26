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

package swp.bibjsf.persistence;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import swp.bibcommon.Book;
import swp.bibcommon.BusinessObject;
import swp.bibcommon.Reader;
import swp.bibcommon.Borrower;
import swp.bibjsf.exception.BusinessElementAlreadyExistsException;
import swp.bibjsf.exception.DataSourceException;
import swp.bibjsf.utils.Constraint;
import swp.bibjsf.utils.OrderBy;

/**
 * Defines the interface that the persistence component has to provide.
 *
 * @author K. Hölscher
 *
 */
public interface Persistence {

	/*****************************************************************************
	 * Books
	 *****************************************************************************/

	/**
	 * Return the list of all books in the system.
	 *
	 * @return the list of all books.
	 * @throws DataSourceException
	 *             if there are problems with the database.
	 */
	public List<Book> getAllBooks() throws DataSourceException;

	/**
	 * Inserts a new book into the system. The same book must not exist.
	 *
	 * @param book
	 *            the inserted book.
	 * @return the number of books updated.
	 * @throws DataSourceException
	 *             if there are problems with the database.
	 */
	public int addBook(final Book book, String tablename) throws DataSourceException;

	/**
	 * Creates a book-object for the transmitted book-ID.
	 *
	 * @param bookID
	 *            the ID of the book.
	 * @return the created book-object
	 * @throws DataSourceException
	 *             if there are problems with the database or if there is no
	 *             book for the provided ID.
	 */
	public Book getBook(Integer bookID) throws DataSourceException;

	/**
	 * Updates a book in the system. Therefore, a new book, with the values that should be updated, is delivered.
	 * Hence, the ID cannot be updated.
	 *
	 * @param book
	 * 			the book with values that have to be updated.
	 * @return the number of updated books
	 * @throws DataSourceException
	 *              if there are problems with the database.
	 */
	public int updateBook(Book book) throws DataSourceException;

	/**
	 * Returns a list of all books of the library in the range of from .. to.
	 * The first book is indexed by 0. The range parameters support pagination.
	 *
	 * @param constraints
	 *            a list of the constraints for this query
	 * @param order
	 *            ordering specification
	 * @param index
	 *            of the first book in the list of matching books to be
	 *            retrieved
	 * @param index
	 *            of the last books in the list of matching books to be
	 *            retrieved
	 * @return list of all books fulfilling all constraints
	 * @throws DataSourceException
	 *             thrown in case of problems with the data source
	 */
	public List<Book> getBooks(List<Constraint> constraints, final int from,
			final int to, List<OrderBy> order) throws DataSourceException;

	/**
	 * Returns a list of books from the data source that have the given
	 * identifier ISBN/ISSN. List may be empty if none exists.
	 *
	 * @param identifier
	 *            ISBN/ISSN
	 * @return list of books
	 * @throws DataSourceException
	 *             thrown in case of problems with the data source
	 */
	public List<Book> getBookByIndustrialIdentifier(String identifier)
			throws DataSourceException;

	/**
	 * Returns the number of books fulfilling the given constraints.
	 *
	 * @param constraints
	 *            constraints to be fulfilled by a book; if null, all books are
	 *            counted
	 * @return number of books fulfilling the given constraints
	 * @throws DataSourceException
	 *             thrown in case of problems with the data source
	 */
	public int getNumberOfBooks(List<Constraint> constraints)
			throws DataSourceException;

	/**
	 *
	 * Updates a book identified by <code>ID</code> by all values of
	 * <code>book</code>. Because we might even want to update the ID, we
	 * separate the ID from the newValue parameter as the latter's ID is assumed
	 * to be the new ID for the update.
	 *
	 * @param ID
	 *            id of the book to be updated
	 * @param newValue
	 *            new values for this update
	 * @return the number updated readers
	 * @throws DataSourceException
	 *             in case of problems with data source
	 */
	public int updateBook(int iD, Book book) throws DataSourceException;

	/**
	 * Deletes <code>book</code> from database.
	 *
	 * @param book
	 *            book to be deleted
	 * @throws DataSourceException
	 *             thrown when the book cannot be deleted
	 */
	public void deleteBook(Book book) throws DataSourceException;

	/**
	 * Saves all books in CSV format in <code>outStream</code>.
	 *
	 * @param outStream
	 *            where to save the data
	 * @throws DataSourceException
	 *             in case of problems with data source
	 */
	public void exportBooks(OutputStream outStream) throws DataSourceException;

	/**
	 * Reads all CSV data from input and stores them into the book table. The
	 * operation is atomic: all books or none is added.
	 *
	 * @param input
	 *            the stream from where to read the CSV data
	 * @return the number of entries read from the file
	 * @throws DataSourceException
	 *             thrown if the data cannot be stored in the data source or the
	 *             input data cannot be processed completely
	 */
	public int importBooks(InputStream input) throws DataSourceException;

	/*****************************************************************************
	 * Readers
	 *****************************************************************************/

	/**
	 * Returns a list of all registered readers of the library in the range of
	 * from .. to. The first reader is indexed by 0. The range parameters
	 * support pagination.
	 *
	 * @param constraints
	 *            a list of the constraints for this query
	 * @param order
	 *            ordering specification
	 * @param index
	 *            of the first reader in the list of matching readers to be
	 *            retrieved
	 * @param index
	 *            of the last reader in the list of matching readers to be
	 *            retrieved
	 * @return list of all readers fulfilling all constraints
	 * @throws DataSourceException
	 *             thrown in case of problems with the data source
	 */
	public List<Reader> getReaders(List<Constraint> constraints,
			final int from, final int to, List<OrderBy> order)
			throws DataSourceException;
	
	/**
	 * Returns the number of readers fulfilling the given constraints.
	 *
	 * @param constraints
	 *            constraints to be fulfilled by a reader; if null, all readers
	 *            are counted
	 * @return number of readers fulfilling the given constraints
	 * @throws DataSourceException
	 *             thrown in case of problems with the data source
	 */
	public int getNumberOfReaders(List<Constraint> constraints)
			throws DataSourceException;

	/**
	 * Adds a new reader to the list of registered readers. If the reader does
	 * not yet have a valid ID, it will receive a new unique ID.
	 *
	 * @param reader
	 *            new reader to be added
	 * @return the number of added readers
	 * @throws DataSourceException
	 *             in case of problems with data source
	 * @throws BusinessElementAlreadyExistsException
	 *             in case the reader to be added exists already
	 */
	public int addReader(Reader reader) throws DataSourceException,
			BusinessElementAlreadyExistsException;
	
	/**
	 * Adds a new librarian to the list of registered readers. If the lirbarian does
	 * not yet have a valid ID, it will receive a new unique ID.
	 *
	 * @param librarian
	 *            new librarian to be added
	 * @return the number of added librarians
	 * @throws DataSourceException
	 *             in case of problems with data source
	 * @throws BusinessElementAlreadyExistsException
	 *             in case the reader to be added exists already
	 */
	public int addLibrarian(Reader reader) throws DataSourceException,
			BusinessElementAlreadyExistsException;

	/**
	 * Adds either all readers in 'readers' or none.
	 *
	 * @param readers
	 *            the list of readers to be added; none may exist yet.
	 * @throws DataSourceException
	 *             in case of problems with data source
	 * @throws BusinessElementAlreadyExistsException
	 *             in case a reader to be added exists already
	 */
	public void addReaders(List<Reader> readers) throws DataSourceException,
			BusinessElementAlreadyExistsException;

	/**
	 *
	 * Updates a reader identified by <code>ID</code> by all values of
	 * <code>newValue</code>. Because we might even want to update the ID, we
	 * separate the ID from the newValue parameter as the latter's ID is assumed
	 * to be the new ID for the update.
	 *
	 * @param ID
	 *            id of the reader to be updated
	 * @param newValue
	 *            new values for this update
	 * @return the number updated readers
	 * @throws DataSourceException
	 *             in case of problems with data source
	 */
	public int updateReader(int ID, Reader newValue) throws DataSourceException;

	/**
	 * Returns a reader with given id.
	 *
	 * @param id
	 *            ID of the reader to be retrieved
	 * @return the found reader or null if none is found
	 * @throws DataSourceException
	 *             in case of problems with data source
	 */
	public Reader getReader(int id) throws DataSourceException;

	/**
	 * Returns a reader with given username.
	 *
	 * @param username
	 *            the user name to be retrieved
	 * @return reader with given username or null if none exists.
	 * @throws DataSourceException
	 *             in case of problems with data source
	 */
	public Reader getReaderByUsername(String username)
			throws DataSourceException;

	/**
	 * Deletes all <code>elements</code> at once or none at all in case any of
	 * these cannot be deleted.
	 *
	 * @param elements
	 *            readers to be deleted
	 */
	public void deleteAll(List<? extends BusinessObject> elements) throws DataSourceException;

	/**
	 * Deletes <code>reader</code> from database.
	 *
	 * @param reader
	 *            reader to be deleted
	 * @throws DataSourceException
	 *             thrown when the reader cannot be deleted
	 */
	public void deleteReader(Reader reader) throws DataSourceException;

	/**
	 * Saves all readers in CSV format in <code>outStream</code>.
	 *
	 * @param outStream
	 *            where to save the data
	 * @throws DataSourceException
	 *             in case of problems with data source
	 */
	public void exportReaders(OutputStream outStream)
			throws DataSourceException;

	/**
	 * Reads all CSV data from input and stores them into the reader table. The
	 * operation is atomic: all readers or none is added.
	 *
	 * @param input
	 *            the stream from where to read the CSV data
	 * @return the number of entries read from the file
	 * @throws DataSourceException
	 *             thrown if the data cannot be stored in the data source or the
	 *             input data cannot be processed completely
	 */
	public int importReaders(InputStream input) throws DataSourceException;

	/**********************************************************************
	 * Global database administration
	 *********************************************************************/

	/**
	 * Resets the database to its original empty state.
	 *
	 * @throws DataSourceException
	 *             thrown if the database cannot be reset
	 */
	public void reset() throws DataSourceException;

	/**
	 * Saves database content on disk for backup. These data will be used by
	 * restore().
	 *
	 * @throws DataSourceException
	 *             thrown if backup cannot be created
	 */
	public void backup() throws DataSourceException;

	/**
	 * Restores database content from the last backup on disk created by
	 * backup(). Existing content is deleted first.
	 *
	 * @throws DataSourceException
	 *             thrown if the database cannot be restored, for instance,
	 *             because there was no backup found
	 */
	public void restore() throws DataSourceException;
	
	public void addLending(int bookID, int readerID, Date date, double charges) throws DataSourceException, SQLException;
	
	public void addLendings(String bookIDs, int readerID, String dates) throws DataSourceException;
	
	public void updateLending(Date date)throws DataSourceException;
	
	public void deleteLending(int bookID) throws DataSourceException;
	
	public List<Book> getLendings(Borrower borrower) throws DataSourceException;
	
	public List<Book> getOverdueLendings(Date date) throws DataSourceException;
	
	public Reader getLendingReader(int bookID) throws DataSourceException;
	

}
