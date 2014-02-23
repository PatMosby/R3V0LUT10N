package swp.bibjsf.businesslogic;

import javax.naming.NamingException;
import javax.swing.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Calendar;
import java.sql.SQLException;
import java.text.DateFormat;
import java.lang.Object;
import java.text.Format;
import java.text.SimpleDateFormat;

import swp.bibcommon.Borrower;
import swp.bibcommon.Reader;
import swp.bibcommon.Book;
import swp.bibjsf.businesslogic.BusinessHandler.InputException;
import swp.bibjsf.exception.BusinessElementAlreadyExistsException;
import swp.bibjsf.exception.DataSourceException;
import swp.bibjsf.persistence.Data;
import swp.bibjsf.presentation.BusinessObjectForm;
import swp.bibjsf.utils.Constraint;
import swp.bibjsf.utils.Messages;
import swp.bibjsf.utils.OrderBy;

public class BorrowHandler extends BusinessObjectHandler<Borrower> {

	private Date date;
	private static final long serialVersionUID = -5653849921779676752L;
	SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
	

	private static volatile BorrowHandler instance;

	protected BorrowHandler() throws DataSourceException, NamingException {
		super();
	}



	/**
	 * Ordnet dem String 'id' einem existierenden Reader zu. Ordnet dem String
	 * 'list' existierende Medien zu.
	 * 
	 * @param id
	 *            ist ID eines Readers
	 * @param list
	 *            enthält die ID's von einem oder mehreren Medien.
	 */
	public void getInfo(String id, String list) {
		System.out.println("" + id + "" + list);
	}

	/**
	 * Fügt die Medien aus mediumList der Ausleihliste LENDING zu. Reader wird
	 * in LENDING seinen ausgeliehenen Medien zugeordnet.
	 * 
	 * @param reader
	 *            der Ausleihende
	 * @param mediumList
	 *            Liste der auszuleihenden Medien
	 */
	public void borrowMedium(Reader reader, List<Book> mediumList) {

	}

	/**
	 * Markiert Medien als ausgeliehen.
	 * 
	 * @param mediumList
	 *            die zu markierenden Medien
	 */
	public void setBorrowed(List<Book> mediumList) {

	}



	/**
	 * Gibt eine Liste mit überfälligen Medien mithilfe der Ausleihliste LENDING
	 * zurück.
	 * 
	 * @param readerId
	 *            die eindeutige ID des Reader
	 * @return Liste der überfälligen Medien
	 */
	public List<Book> overdueMediaList(int readerId) {
		return null;
	}

	/**
	 * Prüft, ob es überfällige Medien gibt.
	 * 
	 * @return Boolean
	 */
	public boolean isOverdue() {
		return true;
	}

	public static synchronized BorrowHandler getInstance()
			throws DataSourceException {

		if (instance == null) {
			try {
				instance = new BorrowHandler();
			} catch (Exception e) {
				throw new DataSourceException(e.getMessage());
			}
		}
		return instance;
	}

	public synchronized List<Borrower> get(List<Constraint> constraints,
			final int from, final int to, List<OrderBy> order)
			throws DataSourceException {
		return null; // persistence.getReaders(constraints, from, to, order);
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
	public synchronized int add(Borrower borrower) throws DataSourceException,
			BusinessElementAlreadyExistsException {
		
//	if (persistence.getBook(Integer.valueOf(borrower.getBookID())) != null) {
//		 throw new BusinessElementAlreadyExistsException(Messages.get("bookIDexists") + " "
//         		+ Messages.get("id") + " = " + borrower.getId());
//		
//	}
//	
//	if (persistence.getBook(Integer.valueOf(borrower.getReaderID()))  != null) {
//		 throw new BusinessElementAlreadyExistsException(Messages.get("readerIDexists") + " "
//	         		+ Messages.get("id") + " = " + borrower.getId());
//	}
	
		logger.debug("BorrowHandler reached");
		try {

			logger.debug("BorrowHandler: " + borrower.getBookID());
			logger.debug("BorrowHandler: " + borrower.getReaderID());
			logger.debug("BorrowHandler: " + borrower.calculateDate());
			logger.debug("BorrowHandler: " + borrower.calculateFines());

			logger.debug("BorrowHandler reached_2");
			// int result = persistence.addLending(borrower.getBookID(),
			// borrower.getReaderID(), calculateDate(), calculateFines());
			logger.debug("calculateDate" + borrower.calculateDate());
			String date = calculateDate(borrower.getBookID());
			String fines =borrower.calculateFines();
			return persistence.addLending(borrower.getBookID(),
					borrower.getReaderID(),date, fines);

		} catch (Exception e) {

			logger.debug("BorrowHandler add Methode catch " + e.getMessage());
			
		}
		logger.debug("Fehler: Keine Doppelte Ausleihe möglich! Medium ist bereits verliehen.");
		return 0;
	}
	
	
	/**
	 * @author Bredehöft
	 * @param bookID
	 * @return string Datum
	 * @throws NumberFormatException
	 * @throws DataSourceException
	 * @throws SQLException
	 */
	public String calculateDate(String bookID) throws NumberFormatException, DataSourceException, SQLException{
		
		Book book = persistence.getBook(Integer.valueOf(bookID));
		String typ = book.getTyp();
		logger.debug(typ + "= DER TYP");
		
		int duration = Integer.valueOf(persistence.getDuration(typ));
		logger.debug(duration + " =DIE DAUER");
		//int duration =1;
		//return "wäwä";
		
		Calendar calendar = new GregorianCalendar();

		Date today = Calendar.getInstance().getTime();
		logger.debug("echtes Datum " + today);

		calendar.setTime(today);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1; // Jan = 0, dec = 11
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		logger.debug("actual Date: " + dayOfMonth + " " + month + " " + year);

		if (today != null)
			try {
				calendar.add(Calendar.DAY_OF_MONTH, duration);
				logger.debug("calendar.add(dayofmonth) " + Calendar.DAY_OF_MONTH);
				
				if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					String s = sdf.format(calendar.getTime());
					logger.debug(s + "if(SUNDAY)");
					return s;
				} else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
					calendar.add(Calendar.DAY_OF_MONTH, 2);
					String s = sdf.format(calendar.getTime());
					logger.debug(s + "if(saturday)");
					return s;
				} else {
					String s = sdf.format(calendar.getTime());
					logger.debug(s + " else " + calendar.getTime());
					return s;
				}

			} catch (Exception e) {
				logger.debug(e + "catch-exception");				
			}
		logger.debug("return null in borrowhandler");
		return null;
				
	}

	public void returnLending(String bookID) {
		logger.debug("Rückgabe_Handler");
		try {
			persistence.deleteLending(bookID);
		} catch (Exception e) {
		}
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
	public synchronized int addLibrarian(Reader reader)
			throws DataSourceException, BusinessElementAlreadyExistsException {
		logger.info("add librarian: " + reader);
		if (reader.hasId() && persistence.getReader(reader.getId()) != null) {
			logger.info("librarian already exists and could not be added: "
					+ reader);
			throw new BusinessElementAlreadyExistsException(
					Messages.get("readerexists") + " " + Messages.get("id")
							+ " = " + reader.getId());
		}
		// if reader has no assigned user name, he/she gets a default user name
		final String username = reader.getUsername();
		if (username == null || username.isEmpty()) {
			reader.setUsername(defaultUsername(reader));
		}
		// assert: reader has a user name
		if (persistence.getReaderByUsername(username) != null) {
			throw new BusinessElementAlreadyExistsException(
					Messages.get("readerexists") + " "
							+ Messages.get("username") + " = " + username);
		}
		int result = persistence.addLibrarian(reader);
		if (result < 0) {
			throw new DataSourceException(Messages.get("readernotadded"));
		}
		return result;
	}

	/**
	 * Creates a default username as a concatenation of the the user's first
	 * letter of his/her first name and the last name all in lower case. If a
	 * user has neither first nor last name, a random name is generated.
	 * 
	 * @param reader
	 *            reader whose user name is to be created
	 * @return default user name
	 */
	private String defaultUsername(Reader reader) {
		final String firstname = reader.getFirstName();
		final String lastname = reader.getLastName();
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
				return Messages.get("user")
						+ ((Integer) (int) Math.abs(Math.random() * 1000 + 1))
								.toString();
			}
		}
	}

	/**
	 * Reads all CSV data from input and stores them into the reader table.
	 * 
	 * @param input
	 *            the stream from where to read the CSV data
	 * @throws InputException
	 *             thrown in case the input data cannot be processed completely
	 * @throws DataSourceException
	 *             thrown if the data cannot be stored in the data source
	 * @return the number of read and inserted readers
	 */
	@Override
	public synchronized int importCSV(InputStream input)
			throws DataSourceException {
		return persistence.importReaders(input);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see swp.bibjsf.businesslogic.BusinessObjectHandler#update(int,
	 * java.lang.Object)
	 */
	@Override
	public synchronized int update(int ID, Borrower newValue)
			throws DataSourceException {
		return 0; // persistence.updateReader(ID, newValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * swp.bibjsf.businesslogic.BusinessObjectHandler#export(java.io.OutputStream
	 * )
	 */
	@Override
	public synchronized void exportCSV(OutputStream outStream)
			throws DataSourceException {
		persistence.exportReaders(outStream);
	}

	/**
	 * Deletes all elements in the data source.
	 * 
	 * @param elements
	 *            the elements to be deleted
	 * @throws DataSourceException
	 */
	@Override
	public synchronized void delete(List<Borrower> elements)
			throws DataSourceException {
		persistence.deleteAll(elements);
	}

	/**
	 * The number of elements fulfilling given constraints.
	 * 
	 * @param constraints
	 *            the set of constraints to be fulfilled to be counted
	 * @return number of elements fulfilling given constraints.
	 * @throws DataSourceException
	 */
	@Override
	public synchronized int getNumber(List<Constraint> constraints)
			throws DataSourceException {
		return persistence.getNumberOfReaders(constraints);
	}

	/**
	 * Returns the element with the given id.
	 * 
	 * @param id
	 *            ID of the element to be retrieved
	 * @return element with the given id
	 * @throws DataSourceException
	 */
	@Override
	public synchronized Borrower get(int id) throws DataSourceException {
		return null; // persistence.getReader(id);
	}

	/**
	 * An instance of Reader acting as the prototype of objects handled by this
	 * handler.
	 */
	private static Reader prototype = new Reader();

	/*
	 * (non-Javadoc)
	 * 
	 * @see swp.bibjsf.businesslogic.BusinessHandler#getPrototype()
	 */
	@Override
	public synchronized Borrower getPrototype() {
		return null; // prototype;
	}

}