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

package eu.it_r3v.bibjsf.isbnsearch;

import eu.it_r3v.bibjsf.utils.Configuration;

/**
 * Manages the Google API key that is necessary to query the Google API.
 * It may be created as described on
 *   https://developers.google.com/books/docs/v1/using#APIKey
 * and may be retrieved by way of:
 *   https://code.google.com/apis/console/?api=books
 * Note that such a key is necessary to access any Google API.
 * Note also that the number of daily accesses is limited.
 * The key is personalized and linked to a personal Google account.
 *
 * @author koschke
 *
 */
public final class GoogleAPIKey {

    /**
     * Hide default constructor for this utility class.
     */
    private GoogleAPIKey() { }

    /** The API Key. */
    private static String API_KEY;

    /**
     * Returns the Google API key.
     *
     * @return the Google API key
     */
    public static String getKey() {
        if (API_KEY == null || API_KEY.isEmpty()) {
        	API_KEY = Configuration.getKey("GOOGLE_API_KEY");
        }
        return API_KEY;
    }
}

