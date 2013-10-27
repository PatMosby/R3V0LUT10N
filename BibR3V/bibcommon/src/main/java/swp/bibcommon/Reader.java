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

/**
 * The reader of a library. Used by clients and server. Acts as Java Bean.
 */
package swp.bibcommon;

import java.io.Serializable;
import java.util.Date;

/**
 * @author koschke
 *
 */
public class Reader extends BusinessObject implements Serializable, Cloneable {

    /*
     * IMPORTANT MAINTENANCE NOTE:
     *
     *   If you add a field, you need to make the following additional changes:
     *
     *    - add a corresponding field in the persistence layer, namely, Data;
     *      the name of the field must be identical to the name given here
     *    - add an input field to bibjsf/src/main/webapp/reader/readerAttributes.xhtml
     *    - add an output field to bibjsf/src/main/webapp/reader/list.xhtml
     *    - add a setter/getter for that field in class swp.bibjsf.presentation.ReaderForm
     *
     */

    /**
     * Unique class ID for serialization.
     */
    private static final long serialVersionUID = -2835684051415448369L;

    /* ************************************
     * Name and birthday (mandatory data)
     * ***********************************/

    /**
     * First name of a reader.
     */
    private String firstName;

    /**
     * Last name of a reader.
     */
    private String lastName;

    /**
     * Birthday of a reader.
     */
    private Date birthday;

    /* ************************************
     * Address
     * ***********************************/

    /**
     * Street and number of building or "Postfach".
     */
    private String street;

    /**
     * Zipcode.
     */
    private String zipcode;

    /**
     * City where the reader lives.
     */
    private String city;


    /* ************************************
     * Login
     * ***********************************/

    /**
     * Password for the reader needed for a login.
     */
    private String password;

    /**
     * User name for the reader needed for a login.
     */
    private String username;

    /* ************************************
     * Contact data
     * ***********************************/

    /**
     * Phone number.
     */
    private String phone;

    /**
     * E-mail address.
     */
    private String email;

    /* ************************************
     * Usage data
     * ***********************************/

    /**
     * Date when the reader was enrolled in the library.
     */
    private Date entryDate;

    /**
     * Date of last activity. This information is needed to decide
     * when to delete the reader. All readers with inactivity for
     * a certain amount of time must be deleted.
     */
    private Date lastUse;

    /* ************************************
     * Notes
     * ***********************************/
    /**
     * Arbitrary note for a reader.
     */
    private String note;

    /* ************************************
     * Constructors
     * ***********************************/

	/**
     * Constructor. ID will be undefined.
     */
    public Reader() {
    }

    /* ************************************
     * Setters and getters
     * ***********************************/

    /**
     * Returns the first name.
     *
     * @return the first name
     */
    public final String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the first name to set
     */
    public final void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name.
     *
     * @return the last name
     */
    public final String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the last name to set
     */
    public final void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the birthday of a reader.
     *
     * @return birthday
     */
    public final Date getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday of a reader.
     *
     * @param birthday new birthday
     */
    public final void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Returns a reader as a string.
     *
     * @see java.lang.Object#toString()
     * @return the reader as a string
     */
    @Override
    public final String toString() {
        return id + " " + firstName + " " + lastName + "(" + username + ")";
        /*
        return "Reader [ id=" + id
                + ", username=" + username
                + ", password=" + hide(password)
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", birthday=" + birthday + "]";
         */
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
        if (other == null || !(other instanceof Reader)) {
            return false;
        }

        Reader otherReader = (Reader) other;
        return ((id == otherReader.id)
                && firstName.equals(otherReader.firstName)
                && lastName.equals(otherReader.lastName)
                && birthday == otherReader.birthday);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        // TODO: The ID should be a suitable hashcode.
        final int multiplier = 23;
        final int initialHashCode = 133;
        int hashCode = initialHashCode;
        hashCode = multiplier * hashCode + firstName.hashCode();
        hashCode = multiplier * hashCode + lastName.hashCode();
        hashCode = multiplier * hashCode + birthday.hashCode();
        hashCode = hashCode + id;
        return hashCode;
    }

    /**
     * Returns the password.
     *
     * @return password of the user
     */
    public final String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password new password to be set
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Returns the user name.
     *
     * @return user name
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Sets user name.
     *
     * @param username new user name to be set
     */
    public final void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the street
     */
    public final String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public final void setStreet(final String street) {
        this.street = street;
    }

    /**
     * @return the zipcode
     */
    public final String getZipcode() {
        return zipcode;
    }

    /**
     * @param zipcode the zipcode to set
     */
    public final void setZipcode(final String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * @return the city
     */
    public final String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public final void setCity(final String city) {
        this.city = city;
    }

    /**
     * @return the phone
     */
    public final String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public final void setPhone(final String phone) {
        this.phone = phone;
    }

    /**
     * @return the email
     */
    public final String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public final void setEmail(final String email) {
        this.email = email;
    }

    public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

    /**
     * @return the entry date
     */
    public final Date getEntryDate() {
        return entryDate;
    }

    /**
     * @param entry the entry date to set
     */
    public final void setEntryDate(final Date entry) {
        this.entryDate = entry;
    }

    /**
     * @return the lastUse
     */
    public final Date getLastUse() {
        return lastUse;
    }

    /**
     * @param lastUse the lastUse to set
     */
    public final void setLastUse(final Date lastUse) {
        this.lastUse = lastUse;
    }
}
