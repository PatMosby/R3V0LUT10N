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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import swp.bibcommon.Book;
import swp.bibcommon.BusinessObject;
import swp.bibcommon.Reader;
import swp.bibjsf.exception.BusinessElementAlreadyExistsException;
import swp.bibjsf.exception.DataSourceException;
import swp.bibjsf.utils.CSVReader;
import swp.bibjsf.utils.CSVReader.CorruptInput;
import swp.bibjsf.utils.CSVReader.UnknownColumn;
import swp.bibjsf.utils.Constraint;
import swp.bibjsf.utils.LikeConstraint;
import swp.bibjsf.utils.Messages;
import swp.bibjsf.utils.OrderBy;
import swp.bibjsf.utils.Reflection;

/**
 * Data is used to communicate with the database.
 *
 * @author K. Hölscher, D. Lüdemann, R. Koschke
 *
 */
public class Data implements Persistence {

	/*
	 * GENERAL ADVICE:
	 *
	 * Use prepared statements whenever parts of the SQL statement are derived
	 * from user input in order to avoid SQL injections.
	 *
	 * IMPORTANT NOTES ON RESOURCES: Connections, statements, and result sets
	 * must be closed after use. To make sure they are closed under all
	 * circumstances (exceptions) follow the advice given here:
	 * http://blog.shinetech.com/2007/08/04/how-to-close-jdbc-resources-properly-every-time/
	 *
	 * Here is the summary in a nutshell:
	 *
	 * 1) Do not initialize the connection variable to null -- always assign the
	 * real connection object to it immediately. 2) On the very next line of
	 * code, start a try/finally block that will use that connection and then
	 * close it. 3) When you get a statement from the connection, don’t use the
	 * same try/finally block to manage it. Instead, repeat steps 1 and 2, but
	 * this time apply them to the statement instead of the connection. In other
	 * words, initialize the statement immediately and start a new, nested
	 * try/finally block on the next line of code.
	 *
	 * Example:
	 *
	 * Connection connection = dataSource.getConnection(); try { Statement
	 * statement = connection.createStatement(); try { // Do stuff with the
	 * statement } finally { statement.close(); } } finally {
	 * connection.close(); }
	 *
	 * Unfortunately, it is not that simple because getConnection() as well as
	 * createStatement() may throw SQL exceptions, too.
	 *
	 * If a connection is closed, all its associated statements and result sets
	 * are implicitly closed, too. So we do not need to close them explicitly
	 * ourselves unless a connection is opened for many statements and result
	 * sets, and we would run out of these resources if we wouldn't close them
	 * early enough.
	 */

	/**
	 * The column name that stores the user name.
	 */
	private static final String UsernameField = "username";
	/**
	 * The name of the table in the database where the books are stored.
	 */
	private final static String bookTableName = "BOOK";
	/*
	 * The minimal ID a book can have. We are using different ranges for book
	 * and reader IDs to avoid user input mistakes. By making sure the ID ranges
	 * do not overlap, we can check whether a user inputs a book ID for a reader
	 * or vice versa.
	 */
	private final static int bookMinID = 30000;
	/**
	 * Name of the table column for the ISBN/ISSN of a book/magazine. Must equal
	 * the field name of Book.industrialIdentifier.
	 */
	private final static String industrialIdentifier = "industrialIdentifier";
	/**
	 * Name of the database table for readers.
	 */
	private final static String readerTableName = "READER";
	/**
	 * The minimal ID a reader can have. Default admin has ID 0. See comment for
	 * bookMinID.
	 */
	private final static int readerMinID = 0;
	/**
	 * User groups.
	 */
	private final static String groupTableName = "grouptable";
	/**
	 * The ID of the admin in reader table. Admin must always exist.
	 */
	private static final int AdminID = 0;
	/**
	 * Name of admin.
	 */
	private static final String ADMIN = "admin";
	/**
	 * Group of adminstrators.
	 */
	private static final String ADMINGROUP = "ADMIN";
	/**
	 * Gruppe der Bibliothekare.
	 */
	private static final String LIBRARIANGROUP = "LIBRARIAN";
	/**
	 * Group of normal users.
	 */
	private static final String USERGROUP = "USER";
	/**
	 * Die Datenquelle zur Persistierung der Daten.
	 */
	private final DataSource dataSource;
	/**
	 * Basis JNDI Pfad.
	 */
	private final String databaselookup = "java:comp/env";
	/**
	 * JNDI Name der Ressource für die Datenbank.
	 */
	private final String databasename = "jdbc/libraryDB";
	/**
	 * Der Query Runner. Vereinfacht den Umgang mit der Datenbank.
	 */
	private final QueryRunner run;
	/**
	 * Logger für Log-Ausgaben.
	 */
	private static final Logger logger = Logger.getLogger(Data.class);

	/************************************************************************
	 * Database structure.
	 ************************************************************************/

	/**
	 *
	 * Creates a new instance of this class. It is checked, whether the
	 * datasource can be provided by the application container and whether the
	 * database has the right structure.
	 *
	 * @throws DataSourceException
	 *             is thrown if there is no datasource found with this name.
	 *
	 *             * @throws NamingException is thrown if there are problems
	 *             during the JNDI-name look-up.
	 */
	public Data() throws DataSourceException, NamingException {
		logger.debug("create new Data object");
		Context envCtx;
		Context initCtx = new InitialContext();
		logger.debug("lookup database: " + databaselookup + ", " + databasename);

		envCtx = (Context) initCtx.lookup(databaselookup);
		dataSource = (DataSource) envCtx.lookup(databasename);
		run = new QueryRunner(dataSource);
		try {
			checkDatabaseStructure(true);
		} catch (SQLException e) {
			logger.error("check database structure failure: " + e.getMessage());
			throw new DataSourceException(e.getLocalizedMessage());
		}
	}

	/**
	 * Checks whether database has expected tables. If not, such tables are
	 * created. Checks whether admin is contained in the table. If not, such a
	 * user is added.
	 *
	 * @throws SQLException in case of an SQL problem
	 * @throws DataSourceException problem with the data source
	 */
	private synchronized void checkDatabaseStructure(boolean createAdmin)
			throws SQLException, DataSourceException {
		logger.debug("check database structure");

		// **************************************************************************
		// IMPORTANT NOTE:
		// The column names must be identical to the field names used in the
		// facelets
		// that can be searched for in datatables because these names will be
		// transfered from filter fields in datatables as constraints to the
		// query operations.
		//
		// Attributes of type Enum must be mapped onto Strings.
		// **************************************************************************

		List<String> tableNames = getTableNames();
		for (String name : tableNames) {
			logger.debug("database table " + name + " exists");
		}
		if (!tableExists(tableNames, bookTableName)) {
			logger.debug("database table " + bookTableName
					+ " does not exist, creating new one");
			// The table of all books in the library.
			run.update("CREATE TABLE " + bookTableName
					+ "	(ID INT PRIMARY KEY CHECK (ID >= " + bookMinID + " ), "
					+ "authors VARCHAR(256), " + "avgrating DOUBLE, "
					+ "categories VARCHAR(128), " + "dateOfAddition DATE, "
					+ "dateOfPublication DATE, " + "description LONG VARCHAR, "
					+ "note LONG VARCHAR, " + "imageURL VARCHAR(128), "
					+ industrialIdentifier + " VARCHAR(29), "
					+ "language VARCHAR(2), " + "location VARCHAR(64), "
					+ "pageCount INT, " + "previewLink VARCHAR(128), "
					+ "price DECIMAL(10,2), " + "printType VARCHAR(64), "
					+ "publisher VARCHAR(64), " + "subtitle VARCHAR(128), "
					+ "title VARCHAR(256), " + "votes INT" + ")");
		}
		if (!tableExists(tableNames, readerTableName)) {
			logger.debug("database table " + readerTableName
					+ " does not exist, creating new one");
			// the table of all readers of the library; includes special user
			// 'admin'
			run.update("CREATE TABLE " + readerTableName
					+ " (ID INT PRIMARY KEY CHECK (" + readerMinID
					+ " <= ID AND ID < " + bookMinID + "), " + UsernameField
					+ " VARCHAR(128) NOT NULL UNIQUE, "
					+ "password varchar(128), "
					+ "firstname VARCHAR(256) NOT NULL, "
					+ "lastname VARCHAR(256) NOT NULL, " + "birthday DATE, "
					+ "street VARCHAR(128), " + "zipcode VARCHAR(12), "
					+ "city VARCHAR(50), " + "phone VARCHAR(30), "
					+ "email VARCHAR(128), " + "entrydate DATE, "
					+ "lastuse DATE, " + "note LONG VARCHAR)");
		}

		if (!tableExists(tableNames, groupTableName)) {
			logger.debug("database table " + groupTableName
					+ " does not exist, creating new one");
			// for group security mechanism, see e.g.:
			// http://stackoverflow.com/questions/7944963/glassfish-3-security-form-based-authentication-using-a-jdbc-realm

			// the table of group membership for all readers (either USER or
			// ADMIN)
			run.update("CREATE TABLE "
					+ groupTableName
					+ "("
					+ UsernameField
					+ " varchar(128) NOT NULL, groupid  varchar(128) NOT NULL, "
					+ "CONSTRAINT GROUP_PK PRIMARY KEY(" + UsernameField
					+ ", groupid), " + "CONSTRAINT USER_FK FOREIGN KEY("
					+ UsernameField + ") REFERENCES " + readerTableName + "("
					+ UsernameField + ") ON DELETE CASCADE ON UPDATE RESTRICT)");
		}
		if (createAdmin) {
			insertAdmin();
		}
	}

	/**
	 * True if tableName is contained in tableNames (both in lowercase).
	 *
	 * @param tableNames
	 *            list of table names
	 * @param tableName
	 *            name of a table
	 * @return true if tableName is contained in tableNames (both in lowercase)
	 */
	private boolean tableExists(List<String> tableNames, String tableName) {
		return tableNames.contains(tableName.toLowerCase());
	}

	/**
	 * Adds admin to reader table and user and admin group tables if it does not
	 * exist yet.
	 *
	 * @throws DataSourceException
	 * @throws SQLException
	 */
	private void insertAdmin() throws DataSourceException, SQLException {
		if (getReaderByUsername(ADMIN) == null) {
			logger.debug("inserting admin");
			run.update("insert into " + readerTableName + "(id, "
					+ UsernameField
					+ ", password, firstname, lastname, birthday) values ("
					+ AdminID + ", '" + ADMIN
					+ "', '21232f297a57a5a743894a0e4a801fc3', '" + ADMIN
					+ "','" + ADMIN + "','"
					+ new java.sql.Date(System.currentTimeMillis()) + "')");
			run.update("insert into " + groupTableName + "(" + UsernameField
					+ ",groupid) values ('" + ADMIN + "', '" + USERGROUP + "')");
			run.update("insert into " + groupTableName + "(" + UsernameField
					+ ",groupid) values ('" + ADMIN + "', '" + ADMINGROUP
					+ "')");
		}
	}

	/**
	 * Returns the lower-case names of all tables in the database.
	 *
	 * @return list of lower-case names of all tables in the database
	 * @throws SQLException
	 *             falls ein Fehler beim Zugriff auf die Datenbank auftritt
	 */
	private List<String> getTableNames() throws SQLException {
		logger.debug("get table names");
		DatabaseMetaData dbMeta;
		List<String> result = new ArrayList<String>();
		Connection dbConnection = dataSource.getConnection();
		try {
			dbMeta = dbConnection.getMetaData();
			logger.debug("URL of database " + dbMeta.getURL());
			logger.debug("database version: major="
					+ dbMeta.getDatabaseMajorVersion() + " minor="
					+ dbMeta.getDatabaseMinorVersion() + " product_version="
					+ dbMeta.getDatabaseProductVersion() + " product_name="
					+ dbMeta.getDatabaseProductName());

			ResultSet rs = dbMeta.getTables(null, null, null,
					new String[] { "TABLE" });
			try {
				while (rs.next()) {
					String theTableName = rs.getString("TABLE_NAME");
					result.add(theTableName.toLowerCase());
				}
			} finally {
				rs.close();
			}
			return result;
		} finally {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				logger.debug("error while trying to close the database connection.");
				// ignore, nothing to be done here anyway
			}
		}
	}

	/************************************************************************
	 * Generic queries
	 ************************************************************************/

	/**
	 * Returns a value in the range of MinID .. java.lang.Integer.MAX_VALUE that
	 * may act as a primary key (ID) for given table. Warnings: (1) This method
	 * may take a long time if it is forced to run a brute-force search for a
	 * primary key. (2) The primary key is not reserved in any way, that is, the
	 * returned key may already be taken upon completion of this method (or
	 * thereafter).
	 *
	 * @param table
	 *            table for which primary key is needed; expected to have column
	 *            'id'
	 * @param minID
	 *            the minimal value the new key should have
	 * @return new primary key
	 * @throws SQLException
	 *             if no primary key can be found
	 */
	private int getNewId(String table, int minID) throws SQLException {
		final long max = singleResultQuery("select MAX(id) from " + table);
		int result;

		// ID is specified in the create statement as INT, which is a four-byte
		// number
		// corresponding to java.lang.Integer whereas max is a long integer
		if (max >= java.lang.Integer.MAX_VALUE) {
			// reached upper bound; try minimum instead
			final long min = singleResultQuery("select MIN(id) from " + table
					+ " where ID >= " + minID);
			if (min > minID) {
				// there is a valid number at the lower end
				result = (int) min - 1;
			} else {
				// we must search brute force
				logger.warn("running a brute-force search for a primary key for table "
						+ table);
				for (int i = (int) min + 1; i < (int) max; i++) {
					logger.debug("getNewId trying " + i);
					long value = singleResultQuery("select count(*) from "
							+ table + " where id = " + i);
					logger.debug("getNewId value = " + value);
					if (value == 0) {
						logger.debug("getNewId = " + i);
						return i;
					}
				}
				throw new SQLException(
						"no primary key available anymore for table " + table);
			}
		} else if (max < minID) {
			// the maximum is lesser than the required minimum; that can only
			// mean that no entry was added if the database is consistent
			result = minID;
		} else {
			// fits in the int range
			result = (int) max + 1;
		}
		logger.debug("getNewId = " + result);
		return result;
	}

	/**
	 * Executes a simple query that results only a single result. Useful to
	 * query for COUNT, MAX, MIN, and the like.
	 *
	 * @param query
	 *            SQL statement
	 * @return result of the query
	 * @throws SQLException
	 *             in case of errors
	 */
	private long singleResultQuery(final String query) throws SQLException {
		Connection connection = dataSource.getConnection();
		try {
			Statement stmt = connection.createStatement();
			try {
				ResultSet rs = stmt.executeQuery(query);
				try {
					// go to first row
					if (rs.next()) {
						return rs.getLong(1);
					} else {
						logger.error("SQL result has no row");
						throw new SQLException("SQL result has no row");
					}
				} finally {
					rs.close();
				}
			} finally {
				stmt.close();
			}
		} finally {
			connection.close();
		}
	}

	/**
	 * Returns the number of elements fulfilling constraints in given table.
	 *
	 * @param table
	 * @param constraints
	 * @return
	 * @throws DataSourceException
	 */
	public int getNumberOfElements(String table, List<Constraint> constraints)
			throws DataSourceException {
		logger.debug("get number of elements for table " + table);
		try {
			Connection connection = dataSource.getConnection();
			try {
				String query = "SELECT COUNT(*) FROM " + table
						+ toQuery(constraints);
				logger.debug("getNumberOfReaders: " + query);

				PreparedStatement stmt = connection.prepareStatement(query);
				try {
					fillInArguments(constraints, stmt);
					ResultSet rs = stmt.executeQuery();
					try {
						// go to first row
						if (rs.next()) {
							int count = rs.getInt(1);
							return count;
						} else {
							logger.error("SQL result has no row");
							return 0;
						}
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataSourceException(e.getLocalizedMessage());
		}
	}

	/**
	 * Generic retrieval of elements from a given table fulfilling given
	 * constraints and sorted by given order. Only the elements from...to in
	 * that order are returned.
	 *
	 * @param constraints
	 *            constraints to be fulfilled
	 * @param from
	 *            index of first relevant element (index of very first element
	 *            is 0)
	 * @param to
	 *            index of last relevant element
	 * @param order
	 *            the ordering
	 * @param table
	 *            name of the table from which to retrieve the elements
	 * @param clazz
	 *            the class of the elements to be retrieved, i.e., Element.class
	 * @return matching elements
	 * @throws DataSourceException
	 */
	public <Element extends BusinessObject> List<Element> getElements(
			List<Constraint> constraints, final int from, final int to,
			List<OrderBy> order, String table, Class<Element> clazz)
			throws DataSourceException {

		// We want to retrieve only some of the matching results, but not
		// all. For a very large data set, we might otherwise run into
		// memory problems. And since this code is run by a server serving
		// multiple clients at once, memory consumption and computing
		// time is an issue.
		//
		// In Derby 10.7 upward, limiting the search for certain number of
		// results would be possible using the FETCH and OFFSET keywords as
		// follows:
		//
		// Sort T using column I, then fetch rows 11 through 20 of the sorted
		// rows (inclusive)
		// SELECT * FROM T ORDER BY I OFFSET 10 ROWS FETCH NEXT 10 ROWS ONLY
		//
		// Glashfish 3.1, however, ships with Derby 10.6.2.1. That version of
		// Derby
		// does not support FETCH/OFFSET.
		// If we ever migrate to a more current Derby version, we should use
		// FETCH/OFFSET
		// instead.
		//
		// For this reason, we follow a pagination strategy described at:
		// http://www.onjava.com/pub/a/onjava/2007/01/31/tuning-derby.html
		//
		// Notice that we set the max rows value to the last row that we need
		// (incremented by one). So, with this solution we fetch not only the
		// rows that we wanted (from - to), but first fetched a all rows up to
		// 'to'
		// and then filter to the rows of interest. Unfortunately, there is no
		// way
		// to tell the JDBC driver to start with a certain row, so we must
		// specify the
		// maximum row of the page that will be displayed. This means that
		// performance will be good for early pages and drop in performance as
		// the user browses results. The good news is that in most cases, the
		// user
		// will not go far, but will usually either find what he's looking for
		// in
		// the first few pages or refine the search query.

		logger.debug("get elements for table " + table);

		ArrayList<Element> allResults = new ArrayList<Element>();

		try {
			Connection connection = dataSource.getConnection();
			try {
				String query = "SELECT * FROM " + table + toQuery(constraints)
						+ toOrderByClause(order);
				logger.debug("getElements " + query);

				PreparedStatement stmt = connection.prepareStatement(query);
				try {
					try {
						stmt.setMaxRows(to + 1);
					} catch (SQLException e) {
						// ignore this exception and try to run the query anyway
					}

					fillInArguments(constraints, stmt);

					ResultSet rs = stmt.executeQuery();

					try {
						// Use the BeanHandler implementation to convert the
						// first
						// ResultSet row into a Reader JavaBean.
						ResultSetHandler<Element> handler = new BeanHandler<Element>(
								clazz);

						int i = 0;
						Element reader;

						while ((reader = handler.handle(rs)) != null) {
							if (from <= i && i <= to) {
								allResults.add(reader);
							} else if (i > to) {
								break;
							}
							i++;
						}
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			logger.error(e.getLocalizedMessage());
			throw new DataSourceException(e.getLocalizedMessage());
		}
		return allResults;
	}

	/**
	 * Fills in the arguments (properties) given in <code>constraints</code>
	 * into <code>stmt</code> positionally.
	 *
	 * @param constraints
	 *            the properties from which arguments are to be filled in
	 * @param stmt
	 *            the preparted statement into which the arguments are to be
	 *            filled
	 * @throws SQLException
	 *             thrown in case of an SQL error
	 */
	private void fillInArguments(List<Constraint> constraints,
			PreparedStatement stmt) throws SQLException {
		// fill in arguments
		if (constraints != null) {
			int parameterIndex = 1;
			for (Constraint constraint : constraints) {
				stmt.setString(parameterIndex, constraint.getProperty());
				parameterIndex++;
			}
		}
	}

	/**
	 * Returns an ORDER BY clause for an sql select statement for the given
	 * <code>order</code>.
	 *
	 * Note: The fields in <code>order</code> are data that are derived from
	 * field names in the facelets and, hence, cannot be influenced by the user,
	 * can it? For this reason, they need to be sanitized for potential SQL
	 * injections, do they?
	 *
	 * @param order
	 *            the order specification
	 * @return ORDER BY clause if <code>order</code> specifies an order or empty
	 *         string if <code>order</code> is null or empty.
	 *
	 */
	private String toOrderByClause(List<OrderBy> order) {
		if (order == null || order.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(" ORDER BY ");
		for (OrderBy o : order) {
			sb.append(o.getAttribute() + (o.isAscending() ? " ASC," : " DESC,"));
		}
		// remove last comma
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * Returns the WHERE clause for <code>constraints</code> where the field
	 * names are used from each constraint, the equality operator (like or =) is
	 * chosen according to the constraint type (LikeConstraint or
	 * EqualConstraint) and a placeholder ? is inserted for each attribute
	 * value. The real attribute values will later be added by the caller by
	 * filling the prepared statement for which this method returns the WHERE
	 * clause. If constraints is null or empty, the empty string is returned.
	 *
	 * Note: The properties in <code>constraints</code> are data that is input
	 * by a user and, hence, a potential source of SQL injections. For this
	 * reason, they are sanitized here by way of prepared statements. The
	 * attribute names in <code>constraints</code>, on the other hand, are
	 * derived from field names in the facelets and, hence, cannot be influenced
	 * by the user, can it?
	 *
	 * @param constraints constraints that should be transformed into SQL syntax
	 * @return where clause
	 */
	private String toQuery(List<Constraint> constraints) {
		if (constraints == null || constraints.size() == 0) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder(" WHERE ");
			for (Constraint constraint : constraints) {
				if (constraint instanceof LikeConstraint) {
					sb.append(constraint.getAttribute() + " like ? AND ");
				} else {
					sb.append(constraint.getAttribute() + " = ? AND ");
				}
			}
			// remove last "AND "
			final int len = sb.length();
			sb.delete(len - 4, len);
			return sb.toString();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see swp.bibjsf.persistence.Persistence#deleteAll(swp.bibcommon.Reader[])
	 */
	@Override
	public void deleteAll(List<? extends BusinessObject> elements) throws DataSourceException {
		logger.debug("delete all given elements");

		Connection con;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new DataSourceException(e.getLocalizedMessage());
		}
		try {
			con.setAutoCommit(false);
			try {
				for (BusinessObject r : elements) {
					if (r instanceof Reader) {
						deleteReader((Reader) r);
					} else if (r instanceof Book) {
						deleteBook((Book) r);
					} else {
						throw new DataSourceException(
								"unhandled business object type in Data.deleteAll(): "
										+ r.getClass().getCanonicalName());
					}
				}
				con.commit();
				logger.debug("deletion finalized");
			} catch (SQLException e) {
				con.rollback();
				logger.error("deletion rolled back");
			}
		} catch (SQLException e) {
			throw new DataSourceException(e.getLocalizedMessage());
		} finally {
			closeConnection(con);
		}
	}

	/**
	 * Disables auto-commit and closes {@code con}.
	 *
	 * @param con connection to be closed
	 * @throws DataSourceException
	 */
	protected void closeConnection(Connection con) throws DataSourceException {
		if (con != null) {
			try {
				try {
					con.setAutoCommit(true);
				} catch (SQLException e) {
					throw new DataSourceException(e.getLocalizedMessage());
				} finally {
					con.close();
				}
			} catch (SQLException e) {
				throw new DataSourceException(e.getLocalizedMessage());
			}
		}
	}

	/************************************************************************
	 * Books
	 ************************************************************************/

	/**
	 * Returns all books that are stored in the database.
	 *
	 * @return list of books
	 * @throws DataSourceException
	 *             if there is a problem with the database.
	 */
	@Override
    public final List<Book> getAllBooks() throws DataSourceException {
		logger.debug("get all books");
		try {
			ResultSetHandler<List<Book>> resultSetHandler = new BeanListHandler<Book>(
					Book.class);

			List<Book> books = run.query("SELECT * FROM " + bookTableName,
					resultSetHandler);
			return books;
		} catch (SQLException e) {
			logger.error("list books failure");
			throw new DataSourceException(e.getLocalizedMessage());
		}
	}

	/**
	 * Adds a new book to the database.
	 *
	 * @param book
	 *            the book to be added.
	 * @return the ID of the book (either the original one or an auto-generated
	 *         one)
	 * @throws DataSourceException
	 *             if there is a problem with the database.
	 */
	@Override
    public final int addBook(final Book book) throws DataSourceException {
		logger.debug("add book " + book);
		try {
			Set<String> toIgnore = new HashSet<String>();
			HashMap<String, Object> replace = new HashMap<String, Object>();
			return insertByID(book, bookTableName, bookMinID, toIgnore, replace);
		} catch (SQLException e) {
			logger.error("add book failure");
			throw new DataSourceException(e.getMessage());
		}
	}

	/**
	 * Inserts element into table. If element has no unique ID, this method will
	 * assign it a new one if possible. If that is not possible, an exception is
	 * thrown.
	 *
	 * @param element
	 *            element to be added
	 * @param table
	 *            the table in which to insert the element
	 * @param toIgnore
	 *            attributes in this list will be ignored in the insert
	 * @param replace
	 *            replaces the actual values of given attributes by those in
	 *            this list
	 * @return the new unique ID
	 * @throws SQLException
	 *             in case of errors
	 * @throws DataSourceException
	 *             in case of errors
	 */
	private int insertByID(final BusinessObject element, final String table,
			final int minID, Set<String> toIgnore,
			HashMap<String, Object> replace) throws SQLException,
			DataSourceException {
		if (!element.hasId()) {
			// element has no ID yet => we search for one

			// try several attempts because of potential race conditions
			for (int i = 1; i <= 10; i++) {
				final int newId = getNewId(table, minID);
				element.setId(newId);

				try {
					insert(table, element, toIgnore, replace);
					logger.debug("element " + element
							+ " added with created ID + " + newId);
					return newId;
				} catch (SQLException e) {
					logger.debug("Data.insertByID: " + e.getMessage());
				}
			}
			throw new DataSourceException(Messages.get("noPrimaryKeyAvailable"));
		} else {
			// element has an ID set
			insert(table, element, toIgnore, replace);
			logger.debug("element " + element + " added with given ID");
			return element.getId();
		}
	}

	/**
	 * Creates a book with the transmitted Book-ID.
	 *
	 * @param id
	 *            the id of the book to be created.
	 * @return das neu erzeugte Buch
	 * @throws DataSourceException
	 *             if there is a problem with the database.
	 */
	@Override
    public final Book getBook(final Integer id) throws DataSourceException {
		logger.debug("get book");

		try {
			if (id < 0) {
				throw new IllegalArgumentException(Messages.get("idnegative"));
			}
			ResultSetHandler<List<Book>> resultSetHandler = new BeanListHandler<Book>(
					Book.class);
			String sqlQuery = String.format("SELECT * FROM %s WHERE id=%d",
					bookTableName, id);
			List<Book> books;
			books = run.query(sqlQuery, resultSetHandler);
			if (books == null || books.size() == 0) {
				throw new DataSourceException(Messages.get("noBookForId") + " "
						+ id);
			} else {
				return books.get(0);
			}
		} catch (SQLException e) {
			throw new DataSourceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * swp.bibjsf.persistence.Persistence#getBookByIndustrialIdentifier(java
	 * .lang.String)
	 */
	@Override
	public List<Book> getBookByIndustrialIdentifier(String identifier)
			throws DataSourceException {
		try {
			ResultSetHandler<List<Book>> resultSetHandler = new BeanListHandler<Book>(
					Book.class);
			String sqlQuery = "SELECT * FROM " + bookTableName + " WHERE "
					+ industrialIdentifier + " = '" + identifier + "'";
			return run.query(sqlQuery, resultSetHandler);
		} catch (SQLException e) {
			throw new DataSourceException(e.getMessage());
		}
	}

	/**
	 * Update the book in the database in such way that the books
	 * gets all the values of the book provided as parameter. A Book with the
	 * same ID as the provided book must already exist. If the parameter is
	 * {@code null}, a new {@code IllegalArgumentException} is thrown.
	 *
	 * @param book
	 * 			the book with the updated values.
	 *
	 * @return {@code true}, if the update was successful,
	 *         {@code false} otherwise.
	 *         	 * @throws DataSourceException
	 *             if there a problems with the database.
	 */
	@Override
    public final int updateBook(final Book book) throws DataSourceException {
		logger.debug("update book");
		try {
			if (book == null) {
				logger.error("book was null, no update possible");
				throw new IllegalArgumentException(Messages.get("nobook"));
			}
			return update(bookTableName, book, "id", book.getId(), null, null);
		} catch (SQLException e) {
			logger.error("update book failure");
			throw new DataSourceException(e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see swp.bibjsf.persistence.Persistence#getBooks(java.util.List, int,
	 * int, java.util.List)
	 */
	@Override
	public List<Book> getBooks(List<Constraint> constraints, int from, int to,
			List<OrderBy> order) throws DataSourceException {
		logger.debug("get books");
		return getElements(constraints, from, to, order, bookTableName,
				Book.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see swp.bibjsf.persistence.Persistence#getNumberOfBooks(java.util.List)
	 */
	@Override
	public int getNumberOfBooks(List<Constraint> constraints)
			throws DataSourceException {
		logger.debug("get number of books");
		return getNumberOfElements(bookTableName, constraints);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see swp.bibjsf.persistence.Persistence#updateBook(int,
	 * swp.bibcommon.Book)
	 */
	@Override
	public int updateBook(int ID, Book newValue) throws DataSourceException {
		logger.error("updating book " + ID + " by " + newValue);
		try {
			return update(bookTableName, newValue, "id", ID, null, null);
		} catch (SQLException e) {
			logger.error("failure in updating book " + e.getErrorCode());
			throw new DataSourceException(e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see swp.bibjsf.persistence.Persistence#deleteBook(swp.bibcommon.Book)
	 */
	@Override
	public void deleteBook(Book book) throws DataSourceException {
		logger.info("deleting book " + book);
		try {
			run.update("DELETE FROM " + bookTableName + " WHERE ID = ?",
					book.getId());
		} catch (SQLException e) {
			logger.error("failure in deleting book " + e.getErrorCode());
			throw new DataSourceException(e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * swp.bibjsf.persistence.Persistence#exportReaders(java.io.OutputStream)
	 */
	@Override
	public void exportBooks(OutputStream outStream) throws DataSourceException {
		logger.debug("export books");
		export(outStream, bookTableName);
	}

	@Override
	public int importBooks(InputStream input) throws DataSourceException {
		logger.info("import books");
		return importTable(input, bookTableName);
	}

	/************************************************************************
	 * Readers
	 ************************************************************************/

	/*
	 * (non-Javadoc)
	 *
	 * @see swp.bibjsf.persistence.Persistence#getReaders(java.util.List)
	 */
	@Override
	public List<Reader> getReaders(List<Constraint> constraints,
			final int from, final int to, List<OrderBy> order)
			throws DataSourceException {
		logger.debug("get readers");
		return getElements(constraints, from, to, order, readerTableName,
				Reader.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see swp.bibjsf.persistence.Persistence#addReader(swp.bibcommon.Reader)
	 */
	@Override
	public int addReader(Reader reader) throws DataSourceException,
			BusinessElementAlreadyExistsException {
		logger.debug("add reader " + reader);
		try {
			if (getReader(reader.getId()) != null) {
				// ID must be unique
				throw new BusinessElementAlreadyExistsException(
						Messages.get("readerexists") + " " + Messages.get("id")
								+ " = " + reader.getId());
			} else if (!reader.getUsername().isEmpty()
					&& getReaderByUsername(reader.getUsername()) != null) {
				// user name must be unique if defined
				throw new BusinessElementAlreadyExistsException(
						Messages.get("readerexists") + Messages.get("username")
								+ " = " + reader.getUsername());
			} else {
				logger.debug("reader " + reader
						+ " does not yet exist; has ID: " + reader.hasId());
				try {
					final String password = hashPassword(reader);
					Set<String> toIgnore = new HashSet<String>();
					HashMap<String, Object> replace = new HashMap<String, Object>();
					replace.put("password", password);
					return insertByID(reader, readerTableName, readerMinID,
							toIgnore, replace);
				} catch (NoSuchAlgorithmException e) {
					logger.error("MD5 problem");
					throw new DataSourceException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.error("add reader failure");
			throw new DataSourceException(e.getMessage());
		}
	}

	/**
	 * Updates the row in <code>table</code> identified by <code>key</code> by
	 * the values given in <code>object</code> excluding those listed in
	 * <code>toIgnore</code>. The row is identified by looking up all rows whose
	 * value of column <code>key</code> equals <code>keyValue</code>.
	 *
	 * If a field of <code>object</code> is found in <code>replace</code>, the
	 * corresponding replacement value in <code>replace</code> is used instead
	 * of the actual value of the field. Otherwise the field's value is stored.
	 *
	 * @param table
	 *            name of the table in which <code>object</code> is inserted
	 * @param object
	 *            the object to be stored
	 * @param key
	 *            key to identify the row to be updated
	 * @param keyValue
	 *            required value for the key
	 * @param toIgnore
	 *            field names that should not be stored
	 * @param replace
	 *            values to be replaced for storing
	 * @return the number of inserted rows (0 or 1)
	 * @throws SQLException
	 *             thrown in case the object cannot be inserted
	 */
	private int update(String table, Object object, String key,
			Object keyValue, Set<String> toIgnore,
			HashMap<String, Object> replace) throws SQLException {
		logger.debug("update table " + table);
		StringBuilder stmt = new StringBuilder("UPDATE " + table + " SET ");
		int numberOfFields = 0;

		// append stmt by "field1 = ?, field2 = ?, ..., fieldN = ?,"
		// where field<i> is a non-static field not to be ignored
		// count the number of such fields in numberOfFields
		HashMap<String, Field> fieldsOfObject = Reflection.getTransitiveFields(
				new HashMap<String, Field>(), object.getClass());
		for (Field f : fieldsOfObject.values()) {
			if (relevantField(toIgnore, f)) {
				stmt.append(f.getName());
				stmt.append(" = ?,");
				numberOfFields++;
			}
		}

		// fields =
		// "UPDATE <table> SET field1 = ?, field2 = ?, ..., fieldN = ?,"
		if (numberOfFields > 0) {
			// remove last comma
			stmt.deleteCharAt(stmt.length() - 1);

			// append where clause
			stmt.append(" WHERE " + key + " = ?");

			try {
				// create the list of values to be filled in;
				// the length of this argument list is numberOfFields + 1

				// other arguments
				Object[] collectedValues = getValues(object, toIgnore, replace,
						numberOfFields);

				// add key value at the end
				Object[] values = new Object[collectedValues.length + 1];
				for (int i = 0; i < collectedValues.length; i++) {
					values[i] = collectedValues[i];
				}
				values[collectedValues.length] = keyValue;
				logger.debug(stmt.toString());
				logger.debug(toString(values));
				// finally run query
				return run.update(stmt.toString(), values);
			} catch (IllegalArgumentException e) {
				// internal error message, hence, we are not using
				// Messages.get(...)
				throw new SQLException(key + " is an illegal argument: "
						+ e.getMessage());
			} catch (SecurityException e) {
				// internal error message, hence, we are not using
				// Messages.get(...)
				throw new SQLException("cannot access field " + key + ": "
						+ e.getMessage());
			}
		} else {
			return 0;
		}
	}

	/**
	 * Returns values as string. Useful for debugging.
	 *
	 * @param values
	 * @return values as a string
	 */
	private Object toString(Object[] values) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			result.append(values[i] == null ? "<NULL>" : values[i].toString());
			result.append(" | ");
		}
		return result.toString();
	}

	/**
	 * Inserts <code>object</code> into <code>table</code> retrieving all fields
	 * of the object. Only those objects are actually stored that are not listed
	 * in <code>toIgnore</code>. If a field is found in <code>replace</code>,
	 * the corresponding replacement value in <code>replace</code> is used
	 * instead of the actual value of the field. Otherwise the field's value is
	 * stored.
	 *
	 * @param table
	 *            name of the table in which <code>object</code> is inserted
	 * @param object
	 *            the object to be stored
	 * @param toIgnore
	 *            field names that should not be stored
	 * @param replace
	 *            values to be replaced for storing
	 * @throws SQLException
	 *             thrown in case the object cannot be inserted
	 */
	private void insert(String table, Object object, Set<String> toIgnore,
			HashMap<String, Object> replace) throws SQLException {
		logger.debug("insert into table " + table);

		StringBuilder fields = new StringBuilder("INSERT INTO " + table + " (");
		StringBuilder placeholders = new StringBuilder("VALUES (");
		int numberOfFields = 0;

		// append fields by "field1, field2, ..., fieldN"
		// append placeholders by "?, ?, ..., ?"
		// where field1 is a non-static field
		// count the number of such fields in numberOfFields
		HashMap<String, Field> fieldsOfObject = Reflection.getTransitiveFields(
				new HashMap<String, Field>(), object.getClass());
		for (Field f : fieldsOfObject.values()) {
			if (relevantField(toIgnore, f)) {
				fields.append(f.getName());
				fields.append(",");
				placeholders.append("?,");
				numberOfFields++;
			}
		}

		// fields = "INSERT INTO <table> (field1, field2, ..., fieldN,"
		// placeholders = "VALUES (?, ?, ..., ?,"
		if (numberOfFields > 0) {
			// remove last commas
			fields.deleteCharAt(fields.length() - 1);
			placeholders.deleteCharAt(placeholders.length() - 1);

			// close statement parts
			fields.append(")");
			placeholders.append(")");

			// fields = "INSERT INTO <table> (field1, field2, ..., fieldN)"
			// placeholders = "VALUES (?, ?, ..., ?)"
			String sqlInsert = fields.toString() + placeholders.toString();
			// sqlInsert = "INSERT INTO <table> (field1, field2, ..., fieldN)
			//                 VALUES (?, ?, ..., ?)"

			// now collect the values of object's fields
			Object[] values = getValues(object, toIgnore, replace, numberOfFields);
			// sqlInsert is built from fields collected from object, hence,
			// cannot be tainted and sqlInsert is safe.
			Connection connection = dataSource.getConnection();
			try {
				PreparedStatement stmt = connection.prepareStatement(sqlInsert);
				try {
					// fill parameters of the statement
					for (int i = 0; i < values.length; i++) {
						if (values[i] == null) {
							stmt.setNull(i + 1, java.sql.Types.VARCHAR);
						} /*
						 * We are currently not using enums. They would need to
						 * be mapped onto int or strings. Here is the code for
						 * the int mapping:
						 *
						 * else if (values[i] instanceof Enum) { // enums are
						 * mapped onto integer Enum e = (Enum)values[i];
						 * //stmt.setInt(i + 1, e.ordinal()); stmt.setString(i +
						 * 1, values[i].toString());
						 *
						 * Yet, the conversion back from the database to the
						 * bean is more complicated. We would need to provide
						 * our own BeanHandler for this conversion. }
						 */
						else {
							stmt.setObject(i + 1, values[i]);
						}
						logger.debug("parameter "
								+ i
								+ ": "
								+ ((values[i] == null) ? "NULL" : values[i]
										.toString()));
					}
					logger.debug("stmt = " + stmt.toString());
					stmt.executeUpdate();
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} else {
			// we treat this as an error; it does not make sense to insert an
			// object with no fields
			throw new SQLException("entity " + object + " has no fields");
		}
	}

	/**
	 * Returns the values of all non-static fields of <code>object</code> not
	 * contained in <code>toIgnore</code>. If a field is found in
	 * <code>replace</code>, the corresponding replacement value in
	 * <code>replace</code> is used instead of the actual value of the field.
	 * Otherwise the field's value is used.
	 *
	 * @param object
	 *            the object whose fields are to be returned
	 * @param toIgnore
	 *            field names that should not be stored
	 * @param replace
	 *            values to be replaced for storing
	 * @param numberOfFields
	 *            the number of fields returned
	 * @return the list of field values (has length numberOfFields)
	 * @throws SQLException
	 *             thrown in case the object cannot be inserted
	 */
	private Object[] getValues(Object object, Set<String> toIgnore,
			HashMap<String, Object> replace, int numberOfFields)
			throws SQLException {
		Object values[] = new Object[numberOfFields];
		int i = 0;

		HashMap<String, Field> fields = Reflection.getTransitiveFields(
				new HashMap<String, Field>(), object.getClass());

		for (Field f : fields.values()) {
			String fieldName = f.getName();
			if (relevantField(toIgnore, f)) {
				try {
					f.setAccessible(true);
					values[i] = f.get(object);
					if (replace != null) {
						final Object replacement = replace.get(fieldName);
						if (replacement != null) {
							values[i] = replacement;
						}
					}
					if (values[i] instanceof Date) {
						// Date must be converted
						values[i] = toDateFormat((Date) values[i]);
					}
					// System.out.println("Data.dumpFields() " + fieldName + " "
					// + f.get(object) + " " + values[i]);
					i++;
				} catch (IllegalArgumentException e) {
					// internal error message, hence, we are not using
					// Messages.get(...)
					throw new SQLException(fieldName
							+ " is an illegal argument: " + e.getMessage());
				} catch (IllegalAccessException e) {
					// internal error message, hence, we are not using
					// Messages.get(...)
					throw new SQLException("cannot access field " + fieldName
							+ ": " + e.getMessage());
				}
			}
		}
		return values;
	}

	/**
	 * True if <code>field</code> should be ignored.
	 *
	 * @param toIgnore
	 *            field names that should not be stored (may be null)
	 * @param field
	 *            field examined
	 * @return if <code>field</code> should be ignored
	 */
	private boolean shouldBeIgnored(Set<String> toIgnore, String field) {
		return toIgnore != null && toIgnore.contains(field);
	}

	/**
	 * True if <code>field</code> is relevant for update or insert: neither a
	 * static field nor a field to be ignored.
	 *
	 * @param toIgnore
	 *            field names that should not be stored (may be null)
	 * @param field
	 * @return if <code>field</code> is relevant
	 */
	private boolean relevantField(Set<String> toIgnore, Field field) {
		return !Modifier.isStatic(field.getModifiers())
				&& !shouldBeIgnored(toIgnore, field.getName());
	}

	/**
	 * Returns an MD5 hash of reader's password.
	 *
	 * @param reader
	 *            reader whose password is to be hashed
	 * @return MD5 hash of reader's password
	 * @throws NoSuchAlgorithmException
	 *             thrown if there is no MD5 algorithm
	 */
	private String hashPassword(Reader reader) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");

		String readerPassword = reader.getPassword();
		if (readerPassword == null) {
			readerPassword = "";
		}
		byte[] bpassword;
		try {
			bpassword = md.digest(readerPassword.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new NoSuchAlgorithmException("no UTF-8 encoding possible: " + e.getLocalizedMessage());
		}
		StringBuffer password = new StringBuffer();
		for (int i = 0; i < bpassword.length; i++) {
			password.append(Integer.toString((bpassword[i] & 0xff) + 0x100, 16).substring(1));
		}
		return password.toString();
	}

	/**
	 * Returns a date in the SQL date format.
	 *
	 * @param date
	 * @return date in SQL date format
	 */
	private String toDateFormat(Date date) {
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see swp.bibjsf.persistence.Persistence#addReaders(java.util.List)
	 */
	@Override
	public void addReaders(List<Reader> readers) throws DataSourceException,
			BusinessElementAlreadyExistsException {
		logger.debug("add readers");

		try {
			Connection con = dataSource.getConnection();
			try {
				con.setAutoCommit(false);
				for (Reader reader : readers) {
					addReader(reader);
				}
				con.commit();
				logger.info("added all readers");
			} catch (Exception e) {
				logger.error("failure in adding readers: " + e.getMessage());
				con.rollback();
			} finally {
				closeConnection(con);
			}
		} catch (SQLException e) {
			throw new DataSourceException(e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see swp.bibjsf.persistence.Persistence#getReader(int)
	 */
	@Override
	public Reader getReader(int id) throws DataSourceException {
		logger.debug("get reader with ID=" + id);
		return getReaderWhere("ID", id);
	}

	/**
	 * Returns a single reader whose attribute <code>fieldName</code> has the
	 * value <code>fieldValue</code>.
	 *
	 * @param fieldName
	 *            column name for the query
	 * @param fieldValue
	 *            the value the row must have for the column
	 * @return the reader if one is found, otherwise null
	 * @throws DataSourceException
	 *             thrown in case of problems with the data source or in case
	 *             multiple readers exist for this query
	 */
	private Reader getReaderWhere(String fieldName, Object fieldValue)
			throws DataSourceException {
		logger.debug("get reader where");
		try {
			ResultSetHandler<List<Reader>> queryResultHandler = new BeanListHandler<Reader>(
					Reader.class);
			List<Reader> result = run.query("SELECT * FROM " + readerTableName
					+ " WHERE " + fieldName + " = ?", queryResultHandler,
					fieldValue);
			if (result != null) {
				if (result.size() == 1) {
					return result.get(0);
				} else if (result.size() == 0) {
					return null;
				} else {
					// this should never happen
					logger.error("database inconsistency: multiple readers with same "
							+ fieldName + " having value " + fieldValue);
					// internal error message, hence, Messages.get() is not used
					// here
					throw new DataSourceException(
							"database inconsistency: multiple readers with same "
									+ fieldName + " having value " + fieldValue);
				}
			} else {
				return null;
			}
		} catch (SQLException e) {
			logger.error("failure in getting reader");
			throw new DataSourceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * swp.bibjsf.persistence.Persistence#getReaderByUsername(java.lang.String)
	 */
	@Override
	public Reader getReaderByUsername(String username)
			throws DataSourceException {
		logger.debug("get reader by username");
		return getReaderWhere(UsernameField, username);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * swp.bibjsf.persistence.Persistence#updateReader(swp.bibcommon.Reader,
	 * swp.bibcommon.Reader)
	 */
	@Override
	public int updateReader(int ID, Reader newValue) throws DataSourceException {
		logger.debug("update reader " + ID);
		if (ID == AdminID) {
			logger.error("attempt to update admin");
			throw new DataSourceException(Messages.get("adminmustnotbechanged"));
		}
		logger.error("updating reader " + ID + " by " + newValue);
		try {
			return update(readerTableName, newValue, "id", ID, null, null);
		} catch (SQLException e) {
			logger.error("failure in updating reader " + e.getErrorCode());
			throw new DataSourceException(e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * swp.bibjsf.persistence.Persistence#getNumberOfReaders(java.util.List)
	 */
	@Override
	public int getNumberOfReaders(List<Constraint> constraints)
			throws DataSourceException {
		logger.debug("get number of readers");
		return getNumberOfElements(readerTableName, constraints);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * swp.bibjsf.persistence.Persistence#deleteReader(swp.bibcommon.Reader)
	 */
	@Override
    public void deleteReader(Reader reader) throws DataSourceException {
		logger.debug("delete reader " + reader);
		if (reader.getId() == AdminID) {
			logger.info("attempt to delete admin " + reader + ": ignored");
			throw new DataSourceException(Messages.get("adminmustnotbechanged"));
		} else {
			logger.info("deleting reader " + reader);
			try {
				run.update("DELETE FROM " + readerTableName + " WHERE ID = ?",
						reader.getId());
			} catch (SQLException e) {
				logger.error("failure in deleting reader " + e.getErrorCode());
				throw new DataSourceException(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * swp.bibjsf.persistence.Persistence#exportReaders(java.io.OutputStream)
	 */
	@Override
	public void exportReaders(OutputStream outStream)
			throws DataSourceException {
		logger.debug("export readers");
		export(outStream, readerTableName);
	}

	@Override
    public int importReaders(InputStream input) throws DataSourceException {
		logger.info("import readers");
		return importTable(input, readerTableName);
	}

	/******************************************************************
	 * generic CSV import/export
	 ******************************************************************/

	/**
	 * The format for imported/exported dates.
	 */
	private static final String DATEFORMAT = "dd.MM.yyyy";
	/**
	 * The default separator of subsequent columns in the imported/exported CSV
	 * data.
	 */
	private static final char DEFAULT_SEPARATOR = ';';

	/**
	 * The symbol used to quote CVS column data.
	 */
	private static final String DEFAULT_QUOTE = "\"";

	/**
	 * Exports all rows in <code>table</code> to <code>out</code> in CSV format.
	 *
	 * @param out
	 *            the output stream
	 * @param table
	 *            the name of the table to be exported
	 * @throws DataSourceException
	 *             thrown in case of problems with the data source
	 */
	public void export(OutputStream out, final String table)
			throws DataSourceException {
		final String query = "SELECT * from " + table;
		try {
			Connection connection = dataSource.getConnection();
			try {
				logger.debug("export " + query);
				Statement stmt = connection.createStatement();
				try {
					ResultSet set = stmt.executeQuery(query);
					try {
						final PrintStream printer = newPrintStream(out);
						final SimpleDateFormat df = new SimpleDateFormat(DATEFORMAT);
						final int numberOfColumns = set.getMetaData()
								.getColumnCount();
						{ // print header row
							ResultSetMetaData metaData = set.getMetaData();
							for (int column = 1; column <= numberOfColumns; column++) {
								printer.print(quote(metaData
										.getColumnLabel(column)));
								if (column < numberOfColumns) {
									printer.print(DEFAULT_SEPARATOR);
								}
							}
							printer.println();
						}
						// print data rows
						while (set.next()) {
							for (int column = 1; column <= numberOfColumns; column++) {
								Object value = set.getObject(column);
								if (value != null) {
									// null should appear as empty string
									if (value instanceof Date) {
										printer.print(quote(df
												.format((Date) value)));
									} else {
										printer.print(quote(value.toString()));
									}
								}
								if (column < numberOfColumns) {
									printer.print(DEFAULT_SEPARATOR);
								}
							}
							printer.println();
						}
					} finally {
						set.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataSourceException(e.getLocalizedMessage());
		}
	}

	/**
	 * Returns a new print stream for out.
	 *
	 * @param out output stream for which to create the print stream
	 * @return new print stream or null if there is no UTF-8 or ISO-8859-1
	 * encoding.
	 */
	protected PrintStream newPrintStream(OutputStream out) {
		try {
			return new PrintStream(out, true, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// should not occur; we will try ISO-8859-1 instead
			try {
				return new PrintStream(out, true, "ISO-8859-1");
			} catch (UnsupportedEncodingException e1) {
				return null;
			}
		}
	}

	/**
	 * Quotes every quote in string and surrounds the whole string by quotes.
	 *
	 * @param string
	 *            input string
	 * @return "string"
	 */
	private String quote(String string) {
		return DEFAULT_QUOTE
				+ string.replace(DEFAULT_QUOTE, DEFAULT_QUOTE + DEFAULT_QUOTE)
				+ DEFAULT_QUOTE;
	}

	/**
	 * A descriptor of a table column.
	 *
	 */
	private static class ColumnDescriptor {
		public String label; // label of a column
		public int type;     // one of java.sql.Types
	}

	/**
	 * Retrieves the column information from <code>table</code>.
	 *
	 * @param table
	 *            the name of the table whose columns need to be known
	 * @return descriptors for each table column
	 * @throws DataSourceException
	 *             thrown in case of problems with the data source
	 */
	private ColumnDescriptor[] getColumns(String table)
			throws DataSourceException {
		final String query = "SELECT * from " + table;
		try {
			Connection connection = dataSource.getConnection();
			try {
				logger.debug("getColumns " + query);
				Statement stmt = connection.createStatement();
				try {
					ResultSet set = stmt.executeQuery(query);
					try {
						final int numberOfColumns = set.getMetaData()
								.getColumnCount();
						ColumnDescriptor[] result = new ColumnDescriptor[numberOfColumns];
						{ // get columns
							ResultSetMetaData metaData = set.getMetaData();
							for (int column = 1; column <= numberOfColumns; column++) {
								result[column - 1] = new ColumnDescriptor();
								result[column - 1].type = metaData
										.getColumnType(column);
								result[column - 1].label = metaData
										.getColumnLabel(column);
							}
						}
						return result;
					} finally {
						set.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataSourceException(e.getLocalizedMessage());
		}
	}

	/**
	 * Reduces <code>columns</code> to the list of table contained therein.
	 *
	 * @param columns
	 *            columns whose labels are to be gathered
	 * @return only the labels in columns
	 */
	private static String[] toLabels(ColumnDescriptor[] columns) {
		String[] result = new String[columns.length];
		for (int i = 0; i < columns.length; i++) {
			result[i] = columns[i].label;
		}
		return result;
	}

	/**
	 * Reads CSV data from <code>input</code> and inserts them into
	 * <code>table</code>.
	 *
	 * @param input
	 *            input stream with CSV data to be imported
	 * @param table
	 *            name of table to be filled in
	 * @throws DataSourceException
	 *             thrown in case of problems in adding the entries to the table
	 *             or in reading the values from the input
	 */
	public int importTable(InputStream input, String table)
			throws DataSourceException {
		final ColumnDescriptor[] columns = getColumns(table);
		final String[] expectedColumns = toLabels(columns);
		if (expectedColumns.length == 0) {
			// Pathological case.
			logger.debug("table with no columns");
			return 0;
		}
		try {
			CSVReader csvReader = new CSVReader(input, DEFAULT_SEPARATOR,
					DEFAULT_QUOTE);
			if (!csvReader.hasColumns(expectedColumns)) {
				throw new DataSourceException(Messages.get("unexpectedHeader")
						+ ": " + toString(expectedColumns));
			}
			// logger.debug("attempt to read " + csvReader.numberOfRows() +
			// " data rows");
			if (csvReader.numberOfRows() == 0) {
				// That is not necessarily an error, although it may appear
				// to be strange that somebody would want to upload an empty
				// file. Nevertheless, we do not treat this as an error,
				// because importTable might be called internally by restoring
				// a backed up database state that has empty tables. Furthermore
				// we do inform the user, how many entries were stored.
				logger.debug("CSV file is empty");
				return 0;
			}
			Connection con = dataSource.getConnection();
			try {
				con.setAutoCommit(false);
				// construct SQL statement
				StringBuilder fields = new StringBuilder("INSERT INTO " + table
						+ " (");
				StringBuilder placeholders = new StringBuilder("VALUES (");

				// append fields by "field1, field2, ..., fieldN"
				// append placeholders by "?, ?, ..., ?"
				// where field1 is a non-static field
				// count the number of such fields in numberOfFields
				for (int i = 0; i < expectedColumns.length; i++) {
					fields.append(expectedColumns[i]);
					fields.append(",");
					placeholders.append("?,");
				}
				// fields = "INSERT INTO <table> (field1, field2, ..., fieldN,"
				// placeholders = "VALUES (?, ?, ..., ?,"

				// remove last commas
				fields.deleteCharAt(fields.length() - 1);
				placeholders.deleteCharAt(placeholders.length() - 1);

				// close statement parts
				fields.append(")");
				placeholders.append(")");

				// fields = "INSERT INTO <table> (field1, field2, ..., fieldN)"
				// placeholders = "VALUES (?, ?, ..., ?)"

				final DateFormat formatter = new SimpleDateFormat(DATEFORMAT);
				// create prepared statement
				final String query = fields.toString()
						+ placeholders.toString();
				// query = "INSERT INTO <table> (field1, field2, ..., fieldN) VALUES (?, ?, ..., ?)"
				// The field names are read from an input file and, hence, represented
				// potentially tainted values. Yet, the fields must correspond to the
				// field names of <table>. Otherwise the SQL statement is invalid. Hence,
				// if any of the fields were tainted, the query would fail. Furthermore,
				// the values read from the input file are added via setX statements to this
				// prepared statement with place holders. Consequently, the query is save.
				PreparedStatement stmt = con.prepareStatement(query);
				try {
					for (int row = 0; row < csvReader.numberOfRows(); row++) {
						logger.debug("processing line " + (row + 1));
						// collect values
						for (int col = 0; col < expectedColumns.length; col++) {
							String value;
							try {
								value = csvReader.get(expectedColumns[col], row);
							} catch (UnknownColumn e) {
								throw new DataSourceException(
										Messages.get("unexpectedColumn") + " '"
												+ expectedColumns[col] + "' "
												+ Messages.get("inLine") + " "
												+ (row + 2) + ": "
												+ e.getLocalizedMessage());
							}
							if (value.isEmpty()) {
								stmt.setNull(col + 1, columns[col].type);
							} else if (columns[col].type == java.sql.Types.DATE) {
								Date date;
								try {
									date = formatter.parse(value);
									java.sql.Date sqlDate = new java.sql.Date(
											date.getTime());
									stmt.setDate(col + 1, sqlDate);
								} catch (ParseException e) {
									throw new DataSourceException(
											Messages.get("unexpectedDateFormat")
													+ " '"
													+ value
													+ "' "
													+ Messages.get("inLine")
													+ " "
													+ (row + 2)
													+ "; "
													+ Messages.get("expected")
													+ " "
													+ DATEFORMAT
													+ ": "
													+ e.getLocalizedMessage());
								}
							} else {
								stmt.setString(col + 1, value);
							}
						}
						// return value of execute() can be safely ignored (see
						// API documentation)
						try {
							stmt.execute();
						} catch (SQLException e) {
							throw new DataSourceException(
									Messages.get("inLine") + " " + (row + 2)
											+ ": " + e.getLocalizedMessage());
						}
					}
				} finally {
					stmt.close();
				}
				con.commit();
				logger.debug("inserts are committed");
			} catch (RuntimeException e) {
				// This exception handler is subsumed by the following on Exception.
				// It is there to please findbugs, which otherwise complains.
				logger.debug("inserts are rolled back");
				con.rollback();
			    throw e;
			} catch (Exception e) {
				logger.debug("inserts are rolled back");
				con.rollback();
				throw e;
			} finally {
				con.close();
			}
			return csvReader.numberOfRows();
		} catch (CorruptInput e) {
			throw new DataSourceException(e.getLocalizedMessage());
		} catch (SQLException e) {
			throw new DataSourceException(e.getLocalizedMessage());
		} catch (Exception e) {
			throw new DataSourceException(e.getLocalizedMessage());
		}
	}

	/**
	 * Returns a concatenation of the strings in columns separated by
	 * DEFAULT_SEPARATOR.
	 *
	 * @param columns
	 *            the strings to be concatenated
	 * @return concatenation of the strings in columns separated by
	 *         DEFAULT_SEPARATOR
	 */
	private static String toString(String[] columns) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < columns.length; i++) {
			s.append(columns[i]);
			s.append(DEFAULT_SEPARATOR);
		}
		return s.toString();
	}

	/*************************************************************************
	 * Reset, backup, restore.
	 *
	 * IMPORTANT NOTE:
	 *
	 * In the following, a very simple approach is used to backup and restore
	 * our database. It will not work in a true online operation where user
	 * accesses occur while the database is backed up or restored. Let alone the
	 * case where multiple users restore and backup at the same time.
	 *
	 * We must use the backup/restore mechanisms provided by the database
	 * itself. See
	 * http://db.apache.org/derby/docs/10.0/manuals/admin/hubprnt43.html for
	 * more information.
	 *************************************************************************/

	/**
	 * Filename for backup of reader table.
	 */
	private static final String READER_BACKUP = "readerBackup.csv";
	/**
	 * Filename for backup of group table.
	 */
	private static final String GROUP_BACKUP = "groupBackup.csv";
	/**
	 * Filename for backup of book table.
	 */
	private static final String BOOK_BACKUP = "bookBackup.csv";

	/*
	 * (non-Javadoc)
	 *
	 * @see swp.bibjsf.persistence.Persistence#reset()
	 */
	@Override
	public void reset() throws DataSourceException {
		logger.debug("drop all tables in database");
		reset(true);
	}

	/**
	 * Resets database. If createAdmin, the admin role is created.
	 *
	 * @throws DataSourceException
	 */
	private void reset(boolean createAdmin) throws DataSourceException {
		// TODO: this operation should be atomic
		try {
			run.update("DROP TABLE " + bookTableName);
		} catch (SQLException e) {
			throw new DataSourceException("reset failed with: "
					+ e.getLocalizedMessage());
		}
		try {
			run.update("DROP TABLE " + groupTableName);
		} catch (SQLException e) {
			throw new DataSourceException("reset failed with: "
					+ e.getLocalizedMessage());
		}
		try {
			run.update("DROP TABLE " + readerTableName);
		} catch (SQLException e) {
			throw new DataSourceException("reset failed with: "
					+ e.getLocalizedMessage());
		}
		try {
			checkDatabaseStructure(createAdmin);
		} catch (SQLException e) {
			throw new DataSourceException(e.getLocalizedMessage());
		}
	}

	/**
	 * Saves content of <code>table</code> in <code>filename</code> as CSV.
	 *
	 * @param table
	 *            name of the table to be saved
	 * @param filename
	 *            name of table in which to save
	 * @throws DataSourceException
	 *             in case saving does not succeed
	 */
	private void write(String table, String filename)
			throws DataSourceException {
		try {
			OutputStream out = new FileOutputStream(filename);
			try {
				export(out, table);
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					throw new DataSourceException(Messages.get("fileNotFound")
							+ " " + filename + ": " + e.getLocalizedMessage());
				}
			}
		} catch (FileNotFoundException e) {
			throw new DataSourceException(Messages.get("fileNotFound") + " "
					+ filename + ": " + e.getLocalizedMessage());
		}
	}

	/**
	 * Reads content of <code>table</code> from <code>filename</code> as CSV.
	 *
	 * @param table
	 *            name of the table to be restored
	 * @param filename
	 *            name from which the data are to be read
	 * @throws DataSourceException
	 *             in case saving does not succeed
	 */
	private void read(String table, String filename) throws DataSourceException {
		try {
			InputStream in = new FileInputStream(filename);
			try {
				importTable(in, table);
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					throw new DataSourceException(Messages.get("fileNotFound")
							+ " " + filename + ": " + e.getLocalizedMessage());
				}
			}
		} catch (FileNotFoundException e) {
			throw new DataSourceException(Messages.get("fileNotFound") + " "
					+ filename + ": " + e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see swp.bibjsf.persistence.Persistence#backup()
	 */
	@Override
	public void backup() throws DataSourceException {
		logger.debug("backup for " + BOOK_BACKUP);
		write(bookTableName, BOOK_BACKUP);
		logger.debug("backup for " + GROUP_BACKUP);
		write(groupTableName, GROUP_BACKUP);
		logger.debug("backup for " + READER_BACKUP);
		write(readerTableName, READER_BACKUP);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see swp.bibjsf.persistence.Persistence#restore()
	 */
	@Override
	public void restore() throws DataSourceException {
		if (fileExists(BOOK_BACKUP) && fileExists(GROUP_BACKUP)
				&& fileExists(READER_BACKUP)) {
			reset(false); // do not create admin because he/she is contained in
							// the backup files
			logger.debug("restoring from " + BOOK_BACKUP);
			read(bookTableName, BOOK_BACKUP);
			// order is important: first reader then groups because readers are
			// members of groups
			logger.debug("restoring from " + READER_BACKUP);
			read(readerTableName, READER_BACKUP);
			logger.debug("restoring from " + GROUP_BACKUP);
			read(groupTableName, GROUP_BACKUP);
		} else {
			throw new DataSourceException(Messages.get("noPreviousBackup"));
		}
	}

	/**
	 * True if file with <code>filename</code> exists.
	 *
	 * @param filename
	 *            file to be checked for existence
	 * @return true if file with <code>filename</code> exists
	 */
	private boolean fileExists(String filename) {
		File f = new File(filename);
		return f.exists();
	}

}
