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

package eu.it_r3v.bibcommon;

import java.io.Serializable;

/**
 * Thrown if ratings are not in the valid range of 0.0 .. 5.0.
 *
 * @author koschke
 *
 */
public class IllegalRating extends Exception implements Serializable {

    /**
     * Unique ID for serialization.
     */
	private static final long serialVersionUID = 4802396695412664994L;

	public IllegalRating(String message) {
		super(message);
	}
}
