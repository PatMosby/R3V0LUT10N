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

package swp.bibjsf.isbnsearch;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import swp.bibcommon.Book;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import com.google.api.services.books.model.Volume.VolumeInfo.ImageLinks;
import com.google.api.services.books.model.Volumes;

/**
 * Allows one to search for book data by way of the Google Books API.
 *
 * @author koschke
 *
 */
public class ISBNGoogleSearch {

    /**
     * Uses the Java Google Books Client Library forNetHttpTransport
     * to query Google Books.
     *
     * How to prepare the initial setup:
     * http://code.google.com/p/google-api-java-client/wiki/Setup
     *
     * Developers guide:
     * http://code.google.com/p/google-api-java-client/wiki/DeveloperGuide
     *
     * API documentation:
     * http://code.google.com/p/google-api-java-client/wiki/APIs#Books_API
     *
     * JavaDoc API description:
     * https://developers.google.com/resources/api-libraries/documentation/books/v1/java/latest/
     *
     * Sample source code is available at:
     * http://samples.google-api-java-client.googlecode.com/hg/books-cmdline-sample/instructions.html
     */

    /**
     * A unique identifier for our application (including the version).
     */
    private static final String APPLICATION_NAME = "SWP-GoogleISBNSearch/0.9";

    /**
     * The JSon factory for queries.
     */
    private final JsonFactory jsonFactory;


    /**
     * The books client for queries.
     */
    private final Books books;

    /**
     * Constructor.
     *
     * @throws GeneralSecurityException thrown if API cannot be accessed.
     * @throws IOException thrown in case of search failures.
     */
    public ISBNGoogleSearch() throws GeneralSecurityException, IOException {
        jsonFactory = new JacksonFactory();
        books = setupBookClient(jsonFactory);
    }

    /**
     * Retrieves and returns book data matching the given <code>isbn</code>.
     * Note: There may be multiple data sets matching the same <code>isbn</code>.
     * The Google books database seems to be redundant.
     *
     * The volume data are as follows (not necessarily all aspects are available for
     * every volume):
     *
     * 	- volume subtitle
	 *  - volume title
     *  - list of authors (each author is separate)
	 *  - date of publication
	 *  - best language for this volume (based on content)
	 *  - publisher of this volume
	 *  - industry standard identifiers for this volume
	 *  - type of publication of this volume
     *  - an identifier for the version of the volume content (text & images).
     *  - average rating by Google users
	 *  - number of review ratings for this volume
     *  - list of categories (each is separate), e.g., "Fiction", "Suspense", etc.
	 *  - main category to which this volume belongs
	 *  - synopsis of the volume
	 *  - physical dimensions of this volume
	 *  - total number of pages
	 *  - a list of image links for all the sizes that are available
	 *  - URL to view information about this volume on the Google Books site
	 *  - canonical URL
	 *  - URL to preview this volume on the Google Books site
     *
     * @param isbn the ISBN to be searched for
     * @throws Exception thrown if the query fails
     */
    public final java.util.List<Volume> search(final String isbn) throws Exception {
        final String key   = "isbn:";
        final String query = key + isbn;

        //System.out.println("=========================================");
        //System.out.println("Query: [" + query + "]");
        List volumesList = createQuery(query, books);

        // Execute the query.
        Volumes volumes = volumesList.execute();
//        System.out.println("found "
//                + volumes.getTotalItems()
//                + " matches at http://books.google.com/ebooks?q="
//                + URLEncoder.encode(query, "UTF-8"));
        return volumes.getItems();
    }

    /**
     * Returns all books with given isbn/issn.
     *
     * @param identifier ISBN/ISSN code of books to be looked up
     * @return all books with given isbn/issn
     * @throws Exception in case of errors
     */
    public final java.util.List<Book> searchByIndustrialIdentifier(final String identifier) throws Exception {
    	java.util.List<Book> result = new java.util.ArrayList<Book>();

    	for (Volume volume : search(identifier)) {
    		Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();

    		final StringBuilder authorlist = getAuthorList(volumeInfo);
    		final Book book = new Book(authorlist.toString(), volumeInfo.getTitle(), identifier.toString());
    		setImage(volumeInfo, book);
    		setCategories(volumeInfo, book);
    		setDateOfPublication(volumeInfo, book);
    		book.setDateOfAddition(new Date());
    		book.setDescription(volumeInfo.getDescription());
    		book.setLanguage(volumeInfo.getLanguage());
    		book.setPageCount(volumeInfo.getPageCount());
    		book.setPreviewLink(volumeInfo.getPreviewLink());
    		book.setPrintType(volumeInfo.getPrintType());
    		book.setPublisher(volumeInfo.getPublisher());
			result.add(book);
    	}
    	return result;
    }

	/**
	 * Sets the date of publication of book. If possible, the date is re-formatted
	 * from ISO-style to German style.
	 *
	 * @param volumeInfo retrieved book info from Google
	 * @param book book to be updated
	 */
	private void setDateOfPublication(Volume.VolumeInfo volumeInfo,	final Book book) {
		// we get very different formats for the publication, such as 2009, 2009-07, or
		// 2009-07-01.
		final String date = volumeInfo.getPublishedDate();
		if (date != null && !date.isEmpty()) {
			String [] formats = {"yyyy-MM-dd", "yyyy-MM", "yyyy", "yy-MM-dd", "yy-MM", "yy"};

			Date googleDate = null;
			for (String format : formats) {
				googleDate = getDate(date, format);
				if (googleDate != null) {
					break;
				}
			}
			if (googleDate == null) {
				book.setDateOfPublication(null);
			} else {
				book.setDateOfPublication(googleDate);
			}
		}
	}

	/**
	 * Tries to parse date using given format. In case of success, the converted
	 * date is returned; otherwise null is returned.
	 *
	 * @param date date to be parsed
	 * @param format date format used for the parsing
	 * @return converted date or null
	 */
	private Date getDate(final String date, final String format) {
		SimpleDateFormat googleFormat = new SimpleDateFormat(format);
		try {
			return googleFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Sets categories of book according to those given in the volumeInfo.
	 * Categories are separated by ;.
	 * @param volumeInfo the volume having the categories for the book
	 * @param book book whose categories at be set
	 */
	private void setCategories(VolumeInfo volumeInfo, Book book) {
        java.util.List<String> categories = volumeInfo.getCategories();
        final StringBuilder list = new StringBuilder();
        if (categories != null && !categories.isEmpty()) {
            for (int i = 0; i < categories.size(); ++i) {
                list.append(categories.get(i));
                if (i < categories.size() - 1) {
                    list.append("; ");
                }
            }
        }
        book.setCategories(list.toString());
    }

    /**
	 * Sets the URL to an image to the book's cover.
	 *
	 * @param volumeInfo volume information
	 * @param book book whose image is to be set
	 */
	private void setImage(Volume.VolumeInfo volumeInfo, Book book) {
		// obtain image URL
        final ImageLinks imageLinks = volumeInfo.getImageLinks();
        if (imageLinks != null) {
        	String url = imageLinks.getThumbnail();
        	if (url != null && !url.isEmpty()) {
        		book.setImageURL(url);
        	} else {
        		url = imageLinks.getSmall();
        		if (url != null && !url.isEmpty()) {
            		book.setImageURL(url);
            	} else {
            		url = imageLinks.getSmallThumbnail();
            		if (url != null && !url.isEmpty()) {
                		book.setImageURL(url);
                	} else {
                		url = imageLinks.getMedium();
                		if (url != null && !url.isEmpty()) {
                    		book.setImageURL(url);
                    	} else {
                    		url = imageLinks.getLarge();
                    		if (url != null && !url.isEmpty()) {
                        		book.setImageURL(url);
                        	} else {
                        		url = imageLinks.getExtraLarge();
                        		if (url != null && !url.isEmpty()) {
                            		book.setImageURL(url);
                            	} else {
                            		book.setImageURL("");
                            	}
                        	}
                    	}
                	}
            	}
        	}
        }
	}

	/**
	 * Returns a concatenated list of the book's authors.
	 *
	 * @param volumeInfo volume information
	 * @return concatenated list of the book's authors
	 */
	private StringBuilder getAuthorList(Volume.VolumeInfo volumeInfo) {
		// collect author list
		java.util.List<String> authors = volumeInfo.getAuthors();
		final StringBuilder authorlist = new StringBuilder();
		if (authors != null && !authors.isEmpty()) {
		    for (int i = 0; i < authors.size(); ++i) {
		    	authorlist.append(authors.get(i));
		        if (i < authors.size() - 1) {
		            authorlist.append("; ");
		        }
		    }
		}
		return authorlist;
	}

    /**
     * Creates the query.
     *
     * @param query the query string
     * @param books the book search client
     * @return the retrieved list of volumes matching the query
     * @throws IOException thrown in case of query failures
     */
    protected static List createQuery(final String query, final Books books) throws IOException
    {
        // Set query string and filter only Google eBooks.
        List volumesList = books.volumes().list(query);
        return volumesList;
    }

    /**
     * Sets up the book client retrieving the API key and setting up the connect.
     *
     * @param jsonFactory the JSON factory required to transfer objects
     * @return the book search client connection
     * @throws GeneralSecurityException in case the API cannot be accessed
     * @throws IOException in case of connection failure
     */
    protected static Books setupBookClient(final JsonFactory jsonFactory)
            throws GeneralSecurityException, IOException {
        final String apiKey = GoogleAPIKey.getKey();

        // Set up Books client.
        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
        .setApplicationName(APPLICATION_NAME)
        .setGoogleClientRequestInitializer(new BooksRequestInitializer(apiKey)).build();
        return books;
    }

}


