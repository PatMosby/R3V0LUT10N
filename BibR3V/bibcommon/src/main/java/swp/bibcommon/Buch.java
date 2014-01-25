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

package swp.bibcommon;

import java.io.Serializable;


/**
 *
 * This class represents a Book in our bib-system and is used by the server and
 * by the android client. This Book class is designed as a bean: all attributes
 * have setter and getter, the class is serializable and it has a public
 * constructor without parameters.
 *
 * IMPORTANT MAINTENANCE NOTE:
 *
 * If you add a field, you need to make the following additional changes:
 *
 * - add an assignment in copy constructor Book(Book) here below - add a
 * corresponding field in the persistence layer, namely, Data; the name of the
 * field must be identical to the name given here - add an input field to
 * bibjsf/src/main/webapp/book/bookAttributes.xhtml - add an output field to
 * bibjsf/src/main/webapp/book/list.xhtml - add a setter/getter for that field
 * in class swp.bibjsf.presentation.BookForm - add a selection marker (boolean)
 * for that field in class swp.bibjsf.presentation.BookForm - handle overriding
 * of selected field in method
 * swp.bibjsf.presentation.BookForm.mergeSelectedAttributes()
 *
 * IMPLEMENTATION ADVICE:
 *
 * Another note on data types for fields: Instances of Book are stored in the
 * data base and they are transmitted via GSON accessible from a REST web
 * service. GSON poses some restrictions on the data types that can be used
 * for fields. For instance, java.util.Date is currently not supported. Likewise,
 * the data base access (read and write) is implemented through reflection,
 * which handles all primitive data types but not necessarily types that are classes.
 * For this reason, you should prefer primitive data types for the fields of Book.
 *
 * @author D. Lüdemann, R. Koschke
 *
 */
public class Buch extends Medium implements Serializable {


	/**
	 * Unique ID for serialization.
	 */
	private static final long serialVersionUID = -2835684051415448365L;

	/*
	 * Some of the attributes below are those described in the Google Books API
	 * at https://developers.google.com/books/docs/v1/reference/volumes
	 */

	/**
	 * Title of the book.
	 */
	private String title;
	/**
	 * List of authors of this book. Authors are separated by ;.
	 */
	private String authors;
	/**
	 * Industrial identifier. May be ISBN-10 or ISBN-13 in case of a book. May
	 * be an ISSN in case of magazine.
	 */
	private String industrialIdentifier;


	/**
	 * Seitenzahl des Buches
	 */
	private int pageCount;;

	/**
	 * Any kind of note on the book, usually created by the librarian, for
	 * instance, regarding the damage status of the book.
	 */
	private String note;
	/**
	 * Ausgabe des Buches
	 */

	private String printType;
	/**
	 * Subtitle of the book.
	 */
	private String subtitle;
	/**
	 * Publisher of this book.
	 */
	private String publisher;

	/**
	 * URL to preview this volume on the Google Books site.
	 */
	private String previewLink;

	/**
	 * Price of the book. Any currency, e.g., EUR or dollar.
	 */
	private java.math.BigDecimal price;
	/**
	 * The location of the book in the library.
	 */
	private String location;

	/**
	 * Constructor required for DBUtils.
	 */
	public Buch() {
	}



	/**
	 * Copy constructor.
	 *
	 * IMPORTANT NOTE: Do not forget to add an assignment for every new field.
	 *
	 * @param book
	 *            the book whose values are to be copied
	 */
	public Buch(Buch buch) {
		// Keep these assignments in alphabetic order. It will ease
		// to check whether every field is actually set. If any field
		// is added (either to this class or any of its superclasses,
		// you need to add a corresponding assignment here.

		super(buch);
		this.authors              = copyString(buch.authors);
		this.industrialIdentifier = copyString(buch.industrialIdentifier);
		this.pageCount            = buch.pageCount;
		this.previewLink          = copyString(buch.previewLink);
		this.publisher            = copyString(buch.publisher);
	}

	/**
	 * Constructor with author, title, and industrial identifier
	 *
	 * @param authors
	 *            the book's author
	 * @param title
	 *            the book's title
	 * @param identifier
	 *            the book's industrial identifier ISBN/ISSN
	 */
	public Buch(final String authors, final String title, final String identifier) {
		this.title                = copyString(title);
		this.authors              = copyString(authors);
		this.industrialIdentifier = copyString(identifier);
	}



	/* ****************************************************************
	 * Setters and getters
	 * ***************************************************************/

	/**
	 * Returns a note about the book as added by setNote().
	 *
	 * @return note about the book
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note about this book.
	 *
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}


	/**
	 * @return the pageCount
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount
	 *            the pageCount to set
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @return the printType
	 */
	public String getPrintType() {
		return printType;
	}

	/**
	 * @param printType
	 *            the printType to set
	 */
	public void setPrintType(String printType) {
		this.printType = printType;
	}

	/**
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * @param subtitle
	 *            the subtitle to set
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher
	 *            the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the previewLink
	 */
	public String getPreviewLink() {
		return previewLink;
	}

	/**
	 * @param previewLink
	 *            the previewLink to set
	 */
	public void setPreviewLink(String previewLink) {
		this.previewLink = previewLink;
	}

	/**
	 * @return the price
	 */
	public java.math.BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Returns list of authors of this book. Authors are separated by ;.
	 *
	 * @return list of authors of this book; authors are separated by ;
	 */
	public final String getAuthors() {
		return authors;
	}

	/**
	 * Sets list of authors. Authors are separated by ;.
	 *
	 * @param authors
	 *            new list of authors
	 */
	public final void setAuthors(final String authors) {
		this.authors = authors;
	}

	/**
	 * Yields industrial identifier (ISBN or ISSN).
	 *
	 * @return industrial identifier (ISBN or ISSN)
	 */
	public final String getIndustrialIdentifier() {
		return industrialIdentifier;
	}

	/**
	 * Sets industrial identifier (ISBN or ISSN).
	 *
	 * @param industrialIdentifier
	 *            new industrial identifier
	 *
	 */
	public final void setIndustrialIdentifier(final String industrialIdentifier) {
		this.industrialIdentifier = industrialIdentifier;
	}


	/**
	 * Return the book as String.
	 *
	 * @see java.lang.Object#toString()
	 * @return book as a string
	 */
	@Override
	public final String toString() {
		return "Book [ id=" + id + ", authors=" + authors + ", title=" + title
				+ ", ISBN/ISSN=" + industrialIdentifier + "]";
	}



	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || !(other instanceof Book)) {
			return false;
		}

		Buch otherBook = (Buch) other;
		return ((id == otherBook.id)
				&& title.equals(otherBook.title)
				&& authors.equals(otherBook.authors)
				&& industrialIdentifier.equals(otherBook.industrialIdentifier));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int multiplier = 23;
		final int initialHashCode = 133;
		int hashCode = initialHashCode;
		hashCode = multiplier * hashCode + authors.hashCode();
		hashCode = multiplier * hashCode + title.hashCode();
		hashCode = multiplier * hashCode + industrialIdentifier.hashCode();
		hashCode = hashCode + id;
		return hashCode;
	}

}
