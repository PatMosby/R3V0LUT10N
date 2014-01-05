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
import it_r3v.bibjsf.exception.NoSuchBookExistsException;
import it_r3v.bibjsf.utils.Constraint;
import it_r3v.bibjsf.utils.Messages;
import it_r3v.bibjsf.utils.OrderBy;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.naming.NamingException;

import swp.bibcommon.Book;
import swp.bibcommon.IllegalRating;

/**
 * Book handler fulfilling requests related to books.
 *
 * @author Dierk Lüdemann, Karsten Hölscher, Rainer Koschke
 *
 */
public class BookHandler extends BusinessObjectHandler<Book> {

    /**
	 * Unique ID for serialization.
	 */
	private static final long serialVersionUID = 624376111239263931L;
	/**
     * Is a singleton.
     *
     */
    private static volatile BookHandler instance;


    protected BookHandler() throws DataSourceException, NamingException {
        super();
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
    public static synchronized BookHandler getInstance()
            throws DataSourceException {

        if (instance == null) {
            try {
                instance = new BookHandler();
            } catch (Exception e) {
                throw new DataSourceException(e.getMessage());
            }
        }
        return instance;
    }

    /**
     * Returns the list of all books in the system.
     *
     * @return list of all books in the system.
     * @throws DataSourceException
     *             is thrown if there are issues with the persistence component.
     */
    public synchronized List<Book> getAllBooks() throws DataSourceException {
        List<Book> books = persistence.getAllBooks();
        return books;
    }

    /* (non-Javadoc)
     * @see it_r3v.bibjsf.businesslogic.BusinessObjectHandler#add(java.lang.Object)
     */
    @Override
    public synchronized int add(Book book) throws DataSourceException,
        BusinessElementAlreadyExistsException {
    	logger.info("add book: " + book);
        if (book.hasId() && persistence.getBook(book.getId()) != null) {
            logger.info("book already exists and could not be added: "
                    + book);
            throw new BusinessElementAlreadyExistsException(Messages.get("bookexists") + " "
            		+ Messages.get("id") + " = " + book.getId());
        }
        int result = persistence.addBook(book);
        if (result < 0) {
            throw new DataSourceException(Messages.get("bookAdditionFailure"));
        }
        return result;
    }

    /**
     * Creates a book-object for the committed ID.
     *
     * @param bookID
     * 				the ID of the book to be created
     * @return the new book.
     * @throws NoSuchBookExistsException
     * 				if there is no book for this id.
     * @throws DataSourceException
     *            is thrown if there are issues with the persistence component.
     */
    private Book getBookForID(final Integer bookID) throws DataSourceException,
            NoSuchBookExistsException {
        logger.info("return book for id: " + bookID);
        Book book = persistence.getBook(bookID);
        return book;
    }

    /**
     * Adds a book with the transfered ID.
     *
     * @param bookID
     * 			the ID of the book to be rated.
     *
     * @param stars
     *            the rating
     * @throws DataSourceException
     *              is thrown if there are issues with the persistence component.
     * @throws NoSuchBookExistsException
     *             if there is no book for this id.
     */
    public synchronized void rateBook(final Integer bookID, final Integer stars)
            throws DataSourceException, NoSuchBookExistsException {
        logger.info("rate with " + stars + " stars the book with id: " + bookID);
        Book book = getBookForID(bookID);
        if (book == null) {
            throw new NoSuchBookExistsException();
        }
        Double rating = book.getAvgRating();
        Integer noOfVotes = book.getVotes();
        rating = ((rating * noOfVotes) + stars) / (++noOfVotes);
        try {
			book.setAvgRating(rating);
		} catch (IllegalRating e) {
			// should never occur
			logger.error("BookHandler.rateBook(): invalid average rating " + rating);
			e.printStackTrace();
		}
        book.setVotes(noOfVotes);
        update(book);
    }

    /**
     * Updates book.
     *
     * @param book book to be updated
     * @return the number of updated books
     * @throws DataSourceException in case of problems with the data source
     */
    public synchronized int update(Book book) throws DataSourceException {
        logger.info("update book " + book.toString());
        return persistence.updateBook(book);
    }

    /* (non-Javadoc)
     * @see it_r3v.bibjsf.businesslogic.BusinessObjectHandler#update(int, java.lang.Object)
     */
    @Override
    public synchronized int update(int ID, Book book) throws DataSourceException {
        logger.info("update book " + book.toString());
        return persistence.updateBook(ID, book);
    }

	/* (non-Javadoc)
	 * @see it_r3v.bibjsf.businesslogic.BusinessHandler#get(int)
	 */
	@Override
	public synchronized Book get(int id) throws DataSourceException {
		return persistence.getBook(id);
	}

    /* (non-Javadoc)
     * @see it_r3v.bibjsf.businesslogic.BusinessObjectHandler#exportCSV(java.io.OutputStream)
     */
    @Override
    public synchronized void exportCSV(OutputStream outStream) throws DataSourceException {
        persistence.exportBooks(outStream);
    }

    /* (non-Javadoc)
     * @see it_r3v.bibjsf.businesslogic.BusinessObjectHandler#importCSV(java.io.InputStream)
     */
    @Override
    public synchronized int importCSV(InputStream input) throws DataSourceException {
        return persistence.importBooks(input);
    }

	/* (non-Javadoc)
	 * @see it_r3v.bibjsf.businesslogic.BusinessObjectHandler#get(java.util.List, int, int, java.util.List)
	 */
	@Override
	public synchronized List<Book> get(List<Constraint> constraints, int from, int to,
			List<OrderBy> order) throws DataSourceException {
		return persistence.getBooks(constraints, from, to, order);
	}

	/* (non-Javadoc)
	 * @see it_r3v.bibjsf.businesslogic.BusinessObjectHandler#getNumber(java.util.List)
	 */
	@Override
	public synchronized int getNumber(List<Constraint> constraints)	throws DataSourceException {
		return persistence.getNumberOfBooks(constraints);
	}

	/**
	 * An instance of Book acting as the prototype of objects handled by this handler.
	 */
	private static Book prototype = new Book();

	/* (non-Javadoc)
	 * @see it_r3v.bibjsf.businesslogic.BusinessHandler#getPrototype()
	 */
	@Override
	public synchronized Book getPrototype() {
	    return prototype;
	}

    @Override
    public void delete(List<Book> elements) throws DataSourceException {
        persistence.deleteAll(elements);
    }

	/**
	 * Returns a list of books from the data source that have the given ISBN/ISSN.
	 * List may be empty if none exists.
	 *
	 * @param identifier ISBN/ISSN
	 * @return list of books
	 * @throws DataSourceException thrown in case of problems with the data source
	 */
	public synchronized List<Book> getByIndustrialIdentifier(String identifier) throws DataSourceException {
		return persistence.getBookByIndustrialIdentifier(identifier);
	}

}
