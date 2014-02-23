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

package swp.bibjsf.presentation;

import java.util.Date;

import swp.bibcommon.Reader;

/**
 * ReaderForm is an abstract superclass of forms that add new or change existing
 * readers.
 *
 * @author koschke
 *
 */
public abstract class ReaderForm extends BusinessObjectForm<Reader> {

    /**
	 * Unique ID for serialization.
	 */
	private static final long serialVersionUID = 380665113958410405L;

    /*********************************************************************************
     * Input fields that are not attributes of the element
     ********************************************************************************/

    /**
     * The password needs to be repeated to make sure the user did not
     * misspelled it (it cannot be seen). This field stores the second edit
     * of the password. reader.getPassword() and passwordCheck must match.
     */
    private String passwordCheck = "";

    /**
     * @return the passwordCheck
     */
    public String getPasswordCheck() {
        return passwordCheck;
    }

    /**
     * @param passwordCheck the passwordCheck to set
     */
    public void setPasswordCheck(String passwordCheck) {
    	// passwords are not trimmed
        this.passwordCheck = passwordCheck;
    }

    /* **************************************
     * wrappers to setters/getters of element
     * *************************************/

    /**
     * Returns the first name.
     *
     * @return returns first name.
     */
    public String getFirstName() {
        return element.getFirstName();
    }

    /**
     * Sets first name.
     *
     * @param firstName
     *            new first name
     */
    public void setFirstName(final String firstName) {
    	element.setFirstName(trim(firstName));
    }

    /**
     * Returns last name.
     *
     * @return last name.
     */
    public String getLastName() {
        return element.getLastName();
    }

    /**
     * Sets last name.
     *
     * @param lastName
     *            new last name
     */
    public void setLastName(final String lastName) {
        element.setLastName(trim(lastName));
    }

    /**
     * Returns the birthday of a reader.
     *
     * @return birthday of reader
     */
    public Date getBirthday() {
        return element.getBirthday();
    }

    /**
     * Sets birthday of a reader.
     *
     * @param birthday new birthday
     */
    public void setBirthday(Date birthday) {
        element.setBirthday(birthday);
    }

    public String getUserName() {
        return element.getUsername();
    }

    public void setUserName(String userName) {
        element.setUsername(trim(userName));
    }

    public String getPassword() {
        return element.getPassword();
    }

    public void setPassword(String password) {
    	// passwords are not trimmed
        element.setPassword(password);
    }

    public String getEmail() {
        return element.getEmail();
    }

    public void setEmail(String email) {
        element.setEmail(trim(email));
    }

    public void setEntryDate(Date date) {
        element.setEntryDate(date);
    }

    public Date getEntryDate() {
        return element.getEntryDate();
    }

    public String getStreet() {
        return element.getStreet();
    }

    public void setStreet(String street) {
        element.setStreet(trim(street));
    }

    public String getZipcode() {
        return element.getZipcode();
    }

    public void setZipcode(String zipcode) {
        element.setZipcode(trim(zipcode));
    }

    public String getCity() {
        return element.getCity();
    }

    public void setCity(String city) {
        element.setCity(trim(city));
    }

    public String getPhone() {
        return element.getPhone();
    }

    public void setPhone(String phone) {
        element.setPhone(trim(phone));
    }

    public String getNote() {
    	return element.getNote();
    }

    public void setNote(String note) {
    	element.setNote(note);
    }
    
    public Date getLastUse() {
        return element.getLastUse();
    }

    public void setLastUse(Date lastUse) {
        element.setLastUse(lastUse);
    }
    
}
