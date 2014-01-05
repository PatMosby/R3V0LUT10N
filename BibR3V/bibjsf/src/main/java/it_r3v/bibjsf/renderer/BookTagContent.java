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

package it_r3v.bibjsf.renderer;

/**
 * Provides the necessary data to print a book tag.
 *
 * @author koschke
 *
 */
public class BookTagContent implements Content {

	/**
	 * The identifier to be printed as barcode.
	 */
	public int ID;
	/**
	 * The first text line to be printed on the book tag.
	 */
	public String firstLine;
	/**
	 * The second text line to be printed on the book tag.
	 */
	public String secondLine;

	/**
	 * Constructor.
	 *
	 * @param ID identifier to be printed as barcode.
	 * @param firstLine first text line to be printed on the book tag
	 * @param secondLine second text line to be printed on the book tag
	 */
	public BookTagContent (int ID, String firstLine, String secondLine) {
		this.ID = ID;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
	}
}
