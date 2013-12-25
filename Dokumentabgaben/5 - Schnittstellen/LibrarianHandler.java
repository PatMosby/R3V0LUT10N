package eu.it_r3v.bibjsf.businesslogic;

import eu.it_r3v.bibjsf.exception.BusinessElementAlreadyExistsException;
import eu.it_r3v.bibjsf.exception.DataSourceException;

/**
 * @author Sebastian Bredehoeft
 *
 */
public class LibrarianHandler extends BusinessObjectHandler<Librarian> {

	/**
	 * Fügt einen neuen Bibliothekaren in die Datenbank. Bibliothekar
	 * darf noch nicht existieren.
	 * @param librarian
	 * 				hinzuzufügender Bibliothekar
	 * @return ID des Bibliothekar
	 * @throws DataSourceException
	 * @throws BusinessElementAlreadyExistsException
	 */
	public synchronized int addLibrarian(Librarian librarian) throws DataSourceException,
	BusinessElementAlreadyExistsException{
	
		return 0;
	}
	
	/**
	 * Löscht einen Bibliothekaren aus der Datenbank.
	 * @param librarian
	 * 				zu löschender Bibliothekar
	 * 
	 */
	public synchronized void deleteLibrarian(Librarian librarian){
	
	}
	
	/**
	 * Berarbeitet einen Bibliothekaren in der Datenbank.
	 * @param librarian
	 * 				zubearbeitender Bibliothekar
	 * @return ID des Bibliothekar
	 */
	public synchronized int editLibrarian(Librarian librarian){
		return 0;
	}
		
}
