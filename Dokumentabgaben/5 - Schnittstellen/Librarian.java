package eu.it_r3v.bibcommon;

import java.io.Serializable;
import java.util.Date;

/** Der Bibliothekar der Bücherrei. 
*
*/

public class Librarian extends BusinessObject implements Serializable, Cloneable {

	/**
     * Unique class ID for serialization.
     */
    
	private static final long serialVersionUID = -2835684051415448369L;

    /* ************************************
     * Name and birthday (mandatory data)
     * ***********************************/

    /**
     * Vorname des Bibliothekars.
     */
    private String firstName;

    /**
     * Nachname des Bibliothekars.
     */
    private String lastName;


    /* ************************************
     * Login
     * ***********************************/

    /**
     * Passwort des Bibliothekars.
     */
    private String password;

    /**
     * Username des Bibliothekars.
     */
    private String username;

    /* ************************************
     * Contact data
     * ***********************************/

    /**
     * Nummer der Bibliothek.
     */
    private String phone;

    /**
     * E-mail des Bibliothekars.
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
     * Konstructor. ID bleibt undefiniert.
     */
    public Librarian() {
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
     * Gibt den Bibliothekaren als String wieder.
     *
     * @see java.lang.Object#toString()
     * @return Der Bibliothekar als String
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

        Librarian otherLibrarian = (Librarian) other;
        return ((id == otherLibrarian.id)
                && firstName.equals(otherLibrarian.firstName)
                && lastName.equals(otherLibrarian.lastName));
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
}