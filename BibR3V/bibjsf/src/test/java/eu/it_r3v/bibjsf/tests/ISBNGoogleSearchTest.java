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

package eu.it_r3v.bibjsf.tests;

import java.util.List;

import org.junit.Test;

import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import com.google.api.services.books.model.Volume.VolumeInfo.ImageLinks;

import eu.it_r3v.bibjsf.isbnsearch.ISBNGoogleSearch;

/**
 * Test for ISBNGoogleSearch.
 *
 * @author koschke
 *
 */
public class ISBNGoogleSearchTest {

	@Test
	public void test() {
		printList();
	}

	private static void printList() {
		String isbns[] = {"9783446418417", "9783868940992", "9780321313799", "9780201398151"};
		try {
            final ISBNGoogleSearch searcher = new ISBNGoogleSearch();
            for (String isbn : isbns) {
            	printVolumes(searcher, isbn);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
	}

	private static void printVolumes(final ISBNGoogleSearch searcher, final String isbn) throws Exception {
		java.util.List<Volume> volumes = searcher.search(isbn);

		if (volumes.size() == 0) {
		    System.out.println("Nothing found.");
		} else {
			// Output findings.
			for (Volume volume : volumes) {
				System.out.println("-----------------------------------------");
				Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
				printTitle(volumeInfo);
				printSubtitle(volumeInfo);
				printAuthors(volumeInfo);
				printImages(volumeInfo);
				printDescription(volumeInfo);
				printRatings(volumeInfo);
				printURL("Link to Google eBooks: ", volumeInfo.getInfoLink());
				printCategories(volumeInfo);
				printLanguage(volumeInfo);
				printPageCount(volumeInfo);
				printPrintType(volumeInfo);
				printPublisher(volumeInfo);
				printDateOfPublication(volumeInfo);
				printPreviewLink(volumeInfo);
			}
		}
	}

    private static void printPreviewLink(VolumeInfo volumeInfo) {
    	printURL("preview link: ",  volumeInfo.getPreviewLink());
	}

	private static void printPageCount(VolumeInfo volumeInfo) {
		System.out.println("page count: " + volumeInfo.getPageCount());
	}

	private static void printLanguage(VolumeInfo volumeInfo) {
		System.out.println("language: " + volumeInfo.getLanguage());
	}

	private static void printPublisher(VolumeInfo volumeInfo) {
		System.out.println("publisher: " + volumeInfo.getPublisher());
	}

	private static void printDateOfPublication(VolumeInfo volumeInfo) {
		System.out.println("date of publication: " + volumeInfo.getPublishedDate());
	}

	private static void printPrintType(VolumeInfo volumeInfo) {
		System.out.println("print type: " + volumeInfo.getPrintType());
	}

	private static void printCategories(VolumeInfo volumeInfo) {
		System.out.println("main category: " + volumeInfo.getMainCategory());
		List<String> categories = volumeInfo.getCategories();
		if (categories != null) {
			for(String category : categories) {
				System.out.println("category: " + category);
			}
		}
	}

	private static void printSubtitle(VolumeInfo volumeInfo) {
		System.out.println("subtitle: " + volumeInfo.getSubtitle());
	}

	/**
     * Print ratings (if any).
     *
     * @param volumeInfo the volume whose ratings are to be printed.
     */
    protected static void printRatings(final Volume.VolumeInfo volumeInfo) {
        if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() > 0) {
            int fullRating = (int) Math.round(volumeInfo.getAverageRating().doubleValue());
            System.out.print("Rating: ");
            for (int i = 1; i <= fullRating; ++i) {
                System.out.print("*");
            }
            System.out.println(" (" + volumeInfo.getRatingsCount() + " rating(s))");
        }
    }

    /**
     * Print descriptions (if any).
     *
     * @param volumeInfo the volume whose descriptions are to be printed.
     */
    protected static void printDescription(final Volume.VolumeInfo volumeInfo) {
        if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
            System.out.println("Description: " + volumeInfo.getDescription());
        }
    }

    /**
     * Print book cover images (if any) in all available sizes.
     *
     * @param volumeInfo the volume whose images are to be printed.
     */
    protected static void printImages(final Volume.VolumeInfo volumeInfo) {
        final ImageLinks imageLinks = volumeInfo.getImageLinks();
        if (imageLinks != null) {
            printURL("extra large image URL:     ", imageLinks.getExtraLarge());
            printURL("large image URL:           ", imageLinks.getLarge());
            printURL("medium image URL:          ", imageLinks.getMedium());
            printURL("small image URL:           ", imageLinks.getSmall());
            printURL("thumbnail image URL:       ", imageLinks.getThumbnail());
            printURL("small thumbnail image URL: ", imageLinks.getSmallThumbnail());
        }
    }

    /**
     * Print author(s).
     *
     * @param volumeInfo the volume whose authors are to be printed.
     */
    protected static void printAuthors(final Volume.VolumeInfo volumeInfo) {
        java.util.List<String> authors = volumeInfo.getAuthors();
        if (authors != null && !authors.isEmpty()) {
            System.out.print("Author(s): ");
            for (int i = 0; i < authors.size(); ++i) {
                System.out.print(authors.get(i));
                if (i < authors.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Print book title.
     *
     * @param volumeInfo the volume whose title is to be printed.
     */
    protected static void printTitle(final Volume.VolumeInfo volumeInfo) {
        System.out.println("Title: " + volumeInfo.getTitle());
    }

    /**
     * Prints message and the URL if the latter is defined.
     *
     * @param message message to be emitted
     * @param url the URL to be emitted
     */
    private static void printURL(final String message, final String url) {
        if (url != null && url.length() > 0) {
            System.out.println(message + url);
        }
    }
}
