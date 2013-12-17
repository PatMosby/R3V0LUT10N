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

package eu.it_r3v.bibjsf.renderer;

/**
 *
 * The content information to be printed on an ID card specific to one
 * particular reader.
 *
 * @author koschke
 *
 */
public class IDContent implements Content {

    public IDContent(String firstname, String lastname, String barCodeText) {
        this.firstname   = firstname;
        this.lastname    = lastname;
        this.barCodeText = barCodeText;
    }

    public IDContent() {}

    public String firstname;
    public String lastname;
    public String barCodeText;
}