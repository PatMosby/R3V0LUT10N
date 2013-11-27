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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.it_r3v.bibjsf.renderer.BookTagContent;
import eu.it_r3v.bibjsf.renderer.BookTagPrinter;
import eu.it_r3v.bibjsf.renderer.Content;

/**
 * Test of class BookTagPrinter.
 *
 * @author koschke
 *
 */
public class BookTagPrinterTest {

    /**
     * Prints example book tags to file BookTags.pdf.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
	@Test
    public void printBookTags() throws FileNotFoundException, IOException {
        List<Content> tags = new ArrayList<Content>();
        for (int i = 1; i <= 100; i++) {
            tags.add(new BookTagContent(i, "Titel", "Autor"));
        }
        tags.add(new BookTagContent(1000, "Another Titel", "Another Autor"));
        tags.add(new BookTagContent(1001, "Yet another Titel", "Yet another Autor"));
        tags.add(new BookTagContent(100000, "veryveryveryveryveryveryveryverylonglonglongTitel", "veryveryveryveryveryveryveryverylonglonglongAutor"));
        BookTagPrinter printer = new BookTagPrinter();
        OutputStream outStream = new FileOutputStream("BookTags.pdf");
        printer.printCards(tags, outStream);
        outStream.close();
    }
}
