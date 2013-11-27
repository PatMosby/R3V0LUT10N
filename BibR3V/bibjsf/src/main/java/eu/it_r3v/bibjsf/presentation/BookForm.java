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

package eu.it_r3v.bibjsf.presentation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import eu.it_r3v.bibcommon.Book;
import eu.it_r3v.bibcommon.BusinessObject;
import eu.it_r3v.bibcommon.IllegalRating;
import eu.it_r3v.bibjsf.businesslogic.BookHandler;
import eu.it_r3v.bibjsf.exception.DataSourceException;
import eu.it_r3v.bibjsf.isbnsearch.ISBNGoogleSearch;
import eu.it_r3v.bibjsf.utils.Messages;

/**
 * BookForm is an abstract superclass of forms that add new or change existing
 * books.
 *
 * @author koschke (first version by Dirk Lüdemann)
 *
 */
public abstract class BookForm extends BusinessObjectForm<Book>{

	public BookForm() {
		super();
		 try {
			searcher = new ISBNGoogleSearch();
		} catch (Exception e) {
			searcher = null;
			googleNotAvailable(e);
		}
	}

    /**
     * Unique serial number.
     */
    private static final long serialVersionUID = 8953750862130771398L;

    /* **************************************
     * selection of added/edited book
     * *************************************/

    /**
     * True if this form supports selection of form fields to keep
     * old values even when the user presses reset. May be overridden
     * by subclass.
     *
     * @return always true (default)
     */
    public boolean getSelectable() {
        return true;
    }

    /**
     * True if user selected the corresponding form field for the book attribute.
     */
    private boolean isbnSelected              = false;
    private boolean authorsSelected           = false;
	private boolean titleSelected             = false;
    private boolean subtitleSelected          = false;
    private boolean categoriesSelected        = false;
    private boolean dateOfPublicationSelected = false;
    private boolean dateOfAdditionSelected    = false;
    private boolean descriptionSelected       = false;
    private boolean languageSelected          = false;
    private boolean locationSelected          = false;
    private boolean pageCountSelected         = false;
    private boolean previewLinkSelected       = false;
    private boolean priceSelected             = false;
    private boolean printTypeSelected         = false;
    private boolean publisherSelected         = false;
    private boolean imageURLSelected          = false;
    private boolean votesSelected             = false;
    private boolean avgRatingSelected         = false;
    private boolean noteSelected              = false;

	/**
     * @return the isbnSelected
     */
    public boolean isIsbnSelected() {
        return isbnSelected;
    }
    /**
     * @param isbnSelected the isbnSelected to set
     */
    public void setIsbnSelected(boolean isbnSelected) {
        this.isbnSelected = isbnSelected;
    }

    public boolean isAuthorsSelected() {
		return authorsSelected;
	}

	public void setAuthorsSelected(boolean authorsSelected) {
		this.authorsSelected = authorsSelected;
	}

	public boolean isTitleSelected() {
		return titleSelected;
	}

	public void setTitleSelected(boolean titleSelected) {
		this.titleSelected = titleSelected;
	}

	public boolean isSubtitleSelected() {
		return subtitleSelected;
	}

	public void setSubtitleSelected(boolean subtitleSelected) {
		this.subtitleSelected = subtitleSelected;
	}

	public boolean isCategoriesSelected() {
		return categoriesSelected;
	}

	public void setCategoriesSelected(boolean categoriesSelected) {
		this.categoriesSelected = categoriesSelected;
	}

	public boolean isDateOfPublicationSelected() {
		return dateOfPublicationSelected;
	}

	public void setDateOfPublicationSelected(boolean dateOfPublicationSelected) {
		this.dateOfPublicationSelected = dateOfPublicationSelected;
	}

	public boolean isDateOfAdditionSelected() {
		return dateOfAdditionSelected;
	}

	public void setDateOfAdditionSelected(boolean dateOfAdditionSelected) {
		this.dateOfAdditionSelected = dateOfAdditionSelected;
	}

	public boolean isDescriptionSelected() {
		return descriptionSelected;
	}

	public void setDescriptionSelected(boolean descriptionSelected) {
		this.descriptionSelected = descriptionSelected;
	}

	public boolean isLanguageSelected() {
		return languageSelected;
	}

	public void setLanguageSelected(boolean languageSelected) {
		this.languageSelected = languageSelected;
	}

	public boolean isLocationSelected() {
		return locationSelected;
	}

	public void setLocationSelected(boolean locationSelected) {
		this.locationSelected = locationSelected;
	}

	public boolean isPageCountSelected() {
		return pageCountSelected;
	}

	public void setPageCountSelected(boolean pageCountSelected) {
		this.pageCountSelected = pageCountSelected;
	}

	public boolean isPreviewLinkSelected() {
		return previewLinkSelected;
	}

	public void setPreviewLinkSelected(boolean previewLinkSelected) {
		this.previewLinkSelected = previewLinkSelected;
	}

	public boolean isPriceSelected() {
		return priceSelected;
	}

	public void setPriceSelected(boolean priceSelected) {
		this.priceSelected = priceSelected;
	}

	public boolean isPrintTypeSelected() {
		return printTypeSelected;
	}

	public void setPrintTypeSelected(boolean printTypeSelected) {
		this.printTypeSelected = printTypeSelected;
	}

	public boolean isPublisherSelected() {
		return publisherSelected;
	}

	public void setPublisherSelected(boolean publisherSelected) {
		this.publisherSelected = publisherSelected;
	}

	public boolean isImageURLSelected() {
		return imageURLSelected;
	}

	public void setImageURLSelected(boolean imageURLSelected) {
		this.imageURLSelected = imageURLSelected;
	}

	public boolean isVotesSelected() {
		return votesSelected;
	}

	public void setVotesSelected(boolean votesSelected) {
		this.votesSelected = votesSelected;
	}

	public boolean isAvgRatingSelected() {
		return avgRatingSelected;
	}

	public void setAvgRatingSelected(boolean avgRatingSelected) {
		this.avgRatingSelected = avgRatingSelected;
	}

    public boolean isNoteSelected() {
		return noteSelected;
	}

	public void setNoteSelected(boolean noteSelected) {
		this.noteSelected = noteSelected;
	}

    /* **************************************
     * selection in data table of found books
     * *************************************/

    /**
     * The book selected in the data table of looked up books.
     */
    private Book selectedBook;

    /**
     * @return the selectedBook
     */
    public Book getSelectedBook() {
        return selectedBook;
    }

    /**
     * @param selectedBook the selectedBook to set
     */
    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    /**
     * Copy the attributes of the selected book imported from Google
     * to the book currently being added. If none is selected, but
     * there is only one found book, that book is used for the copy.
     * This method is an action called from the facelet.
     *
     * @return navigation result, currently null (remain on the same page);
     * it makes no sense to copy the attributes and then to navigate to
     * a different form
     */
    public String copySelected() {
    	if (selectedBook != null || (foundBooks != null && foundBooks.getRowCount() == 1)) {
    		if (selectedBook != null) {
    			copyElementFromDataTable(selectedBook);
    		} else {
    			// no selection but only one element in the data table
    			List<Book> wrappedData = (List<Book>)foundBooks.getWrappedData();
    			copyElementFromDataTable(wrappedData.get(0));
    		}
    	}
    	return null;
    }

    /**
     * Creates a new book by copying the attributes of given 'books'
     * and then merging the selected attributes by the user (selected
     * for maintaining their values). This new book is then assigned
     * to 'element'. The ID of 'element' is re-set to its old value
     * or to UndefinedID if there was no element set yet. That is
     * needed because 'book' stems from the list of books retrieved
     * from Google and they have arbitrary IDs.
     *
     * @param book from where to copy the attributes
     */
    private void copyElementFromDataTable(Book book) {
		int oldID = BusinessObject.UndefinedID;
		if (element != null) {
			oldID = element.getId();
		}
		// assign all attributes of book
    	Book newElement = new Book(book);
    	// set default date of addition (today)
    	newElement.setDateOfAddition(new Date());
		// override all attributes imported from Google and selected by the
		// user with the old attributes of element
    	mergeSelectedAttributes(newElement, element);
    	element = newElement;
		// reset ID to old one: those for the Google books are artificial
		element.setId(oldID);
    }

    /* **************************************
     * data model for found books
     * *************************************/

    /**
     * For Google searches for books. May be null if we could not connect
     * to the service when constructor was called.
     */
    ISBNGoogleSearch searcher;

    /**
     * Looks up the books matching the entered ISBN/ISSN at Google.
     * Fills the data model 'foundBooks' with the retrieved books.
     * If only one book is found, that books is entered in the
     * edit form immediately.
     */
    private void lookup() {
    	logger.debug("BookForm.lookup()");
    	foundBooks = new BookListDataModel(find(element.getIndustrialIdentifier()));
    	if (foundBooks.getRowCount() == 1) {
    		// we want to override all attributes of the current
    		// form except for the ID
    		element.getId();
			List<Book> wrappedData = (List<Book>)foundBooks.getWrappedData();
			copyElementFromDataTable(wrappedData.get(0));
    	}
    	// inform user if a book with given ISBN/ISSN exists already
    	try {
			BookHandler bh = BookHandler.getInstance();
			final int numberOfBooks = bh.getByIndustrialIdentifier(element.getIndustrialIdentifier()).size();
			if (numberOfBooks > 0) {
    			FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                         Messages.get("bookexists"),
                                         numberOfBooks + " " + Messages.get("exemplars")));
			}
		} catch (DataSourceException e) {
			// nothing to, it's just an informative message
		}
    }

	/**
	 * Merges all selected attributes from fromBook into toBook.
	 *
	 * @param toBook book into which to merge the attributes
	 * @param fromBook book from which to merge the attributes
	 */
	protected void mergeSelectedAttributes(Book toBook, Book fromBook) {
		if (isIsbnSelected()) {
            toBook.setIndustrialIdentifier(fromBook.getIndustrialIdentifier());
        }
        if (isTitleSelected()) {
            toBook.setTitle(fromBook.getTitle());
        }
        if (isAuthorsSelected()) {
            toBook.setAuthors(fromBook.getAuthors());
        }
        if (isSubtitleSelected()) {
            toBook.setSubtitle(fromBook.getSubtitle());
        }
        if (isCategoriesSelected()) {
            toBook.setCategories(fromBook.getCategories());
        }
        if (isDateOfPublicationSelected()) {
            toBook.setDateOfPublication(fromBook.getDateOfPublication());
        }
        if (isDateOfAdditionSelected()) {
            toBook.setDateOfAddition(fromBook.getDateOfAddition());
        }
        if (isDescriptionSelected()) {
            toBook.setDescription(fromBook.getDescription());
        }
        if (isLanguageSelected()) {
            toBook.setLanguage(fromBook.getLanguage());
        }
        if (isLocationSelected()) {
            toBook.setLocation(fromBook.getLocation());
        }
        if (isPageCountSelected()) {
            toBook.setPageCount(fromBook.getPageCount());
        }
        if (isPreviewLinkSelected()) {
            toBook.setPreviewLink(fromBook.getPreviewLink());
        }
        if (isPriceSelected()) {
            toBook.setPrice(fromBook.getPrice());
        }
        if (isPrintTypeSelected()) {
            toBook.setPrintType(fromBook.getPrintType());
        }
        if (isPublisherSelected()) {
            toBook.setPublisher(fromBook.getPublisher());
        }
        if (isImageURLSelected()) {
            toBook.setImageURL(fromBook.getImageURL());
        }
        if (isVotesSelected()) {
            toBook.setVotes(fromBook.getVotes());
        }
        if (isNoteSelected()) {
            toBook.setNote(fromBook.getNote());
        }
        if (isAvgRatingSelected()) {
            try {
				toBook.setAvgRating(fromBook.getAvgRating());
			} catch (IllegalRating e) {
				// cannot happen
			}
        }
	}

    /**
     * Returns the list of books matching given identifier asking Google.
     * Normally, there should be only one book, but sometimes Google
     * gives us several.
     *
     * @param identifier the ISBN/ISSN of the book/magazine to be retrieved.
     * @return the list of books matching given identifier
     */
    private synchronized List<Book> find(String identifier) {
        if (identifier == null || identifier.isEmpty()) {
        	return new ArrayList<Book>();
        } else {
        	// TODO: validate isbn/issn
        	if (searcher == null) {
        		// we did not get a connection initially; let's try once more now
        		try {
        			searcher = new ISBNGoogleSearch();
        		} catch (Exception e) {
        			googleNotAvailable(e);
        			return new ArrayList<Book>();
        		}
        	}
        	List<Book> result = new ArrayList<Book>();
        	try {
        		result = searcher.searchByIndustrialIdentifier(identifier);
        		int i = 1;
        		for (Book b : result) {
        			b.setId(i);
        			i++;
        		}
        		return result;
        	} catch (Exception e) {
        		logger.error("general error " + e.getMessage()
        				 + " (" + e.getClass().getCanonicalName() + ")");
        		FacesContext.getCurrentInstance().addMessage(null,
        				new FacesMessage(FacesMessage.SEVERITY_WARN,
        						Messages.get("googleNotAvailable"),
        						e.getLocalizedMessage()));
        		return result;
        	}
        }
    }

    /**
     * Report problem with Google service.
     *
     * @param e the exception that was thrown and should be reported
     *
     */
    private void googleNotAvailable(Exception e) {
        logger.error("cannot connect to Google service " + e.getMessage()
        		+ " (" + e.getClass().getCanonicalName() + ")");
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                 Messages.get("googleNotAvailable"),
                                 e.getLocalizedMessage()));
    }

    /**
     * The data model for all books found by the Google search.
     */
    private BookListDataModel foundBooks = new BookListDataModel();

    /**
     * @return the foundBooks
     */
    public BookListDataModel getFoundBooks() {
        return foundBooks;
    }

    /**
     * @param foundBooks the foundBooks to set
     */
    public void setFoundBooks(BookListDataModel foundBooks) {
        this.foundBooks = foundBooks;
    }

    /* **************************************
     * wrappers to setters/getters of element
     * *************************************/
    /**
     * Getter for subtitle.
     *
     * @return book subtitle
     */
    public String getTitle() {
        return element.getTitle();
    }

    /**
     * Setter for subtitle.
     *
     * @param subtitle new subtitle.
     */
    public void setTitle(final String subtitle) {
        element.setTitle(subtitle);
    }

    /**
     * Getter for subtitle.
     *
     * @return book subtitle
     */
    public String getSubtitle() {
        return element.getSubtitle();
    }

    /**
     * Setter for subtitle.
     *
     * @param subtitle new subtitle.
     */
    public void setSubtitle(final String subtitle) {
        element.setSubtitle(subtitle);
    }

    /**
     * Getter for authors.
     *
     * @return list of authors
     */
    public String getAuthors() {
        return element.getAuthors();
    }

    /**
     * Setter for authors.
     *
     * @param authors new list of authors
     */
    public void setAuthors(final String authors) {
        element.setAuthors(authors);
    }

    /**
     * Getter for ISBN/ISSN.
     *
     * @return ISBN/ISSN of current book
     */
    public String getIndustrialIdentifier() {
        return element.getIndustrialIdentifier();
    }

    /**
     * Setter for ISBN/ISSN.
     *
     * @param identifier new ISBN/ISSN
     */
    public void setIndustrialIdentifier(final String identifier) {
        element.setIndustrialIdentifier(identifier);
        lookup();
    }

    public int getVotes() {
    	return element.getVotes();
    }

    public void setVotes(int votes) {
    	element.setVotes(votes);
    }

    public double getAvgRating() {
    	return element.getAvgRating();
    }

    public String getImageURL() {
    	return element.getImageURL();
    }

    public void setImageURL(String url) {
    	element.setImageURL(url);
    }

    public void setAvgRating(double rating) {
    	try {
			element.setAvgRating(rating);
		} catch (IllegalRating e) {
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     Messages.get("invalidRating"),
                                     e.getLocalizedMessage()));
		}
    }

    public String getCategories() {
        return element.getCategories();
    }

    public void setCategories(String categories) {
        element.setCategories(categories);
    }

    public Date getDateOfAddition() {
        return element.getDateOfAddition();
    }

    public void setDateOfAddition(Date dateOfAddition) {
        element.setDateOfAddition(dateOfAddition);
    }

    public Date getDateOfPublication() {
        return element.getDateOfPublication();
    }

    public void setDateOfPublication(Date dateOfPublication) {
        element.setDateOfPublication(dateOfPublication);
    }

    public String getDescription() {
        return element.getDescription();
    }

    public void setDescription(String description) {
        element.setDescription(description);
    }

    public String getLanguage() {
        return element.getLanguage();
    }

    public void setLanguage(String language) {
        element.setLanguage(language);
    }

    /**
     *  Language of a book. A two-letter ISO 639-1 code such as 'fr', 'en', etc.
     */
    private String[] languages = {"de", "en", "fr", "es", "it"};

    public String[] getLanguages() {
    	return languages.clone();
    }

    public void setLanguages(String[] languages) {
    	this.languages = languages.clone();
    }

    public String getLocation() {
        return element.getLocation();
    }

    public void setLocation(String location) {
        element.setLocation(location);
    }

    public int getPageCount() {
        return element.getPageCount();
    }

    public void setPageCount(int pageCount) {
        element.setPageCount(pageCount);
    }

    public BigDecimal getPrice() {
        return element.getPrice();
    }

    public void setPrice(BigDecimal price) {
        element.setPrice(price);
    }

    public String getPreviewLink() {
        return element.getPreviewLink();
    }

    public void setPreviewLink(String previewLink) {
        element.setPreviewLink(previewLink);
    }

    public String getPrintType() {
    	return element.getPrintType();
    }

    public void setPrintType(String printType) {
        element.setPrintType(printType);
    }

    public String getPublisher() {
        return element.getPublisher();
    }

    public void setPublisher(String publisher) {
        element.setPublisher(publisher);
    }

    public String getNote() {
        return element.getNote();
    }

    public void setNote(String note) {
        element.setNote(note);
    }
}
