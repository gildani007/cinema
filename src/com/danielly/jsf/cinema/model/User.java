package com.danielly.jsf.cinema.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.danielly.jsf.cinema.services.admin.ValidEmailAddress;
import java.io.Serializable;

/**
 * The class User holds the information about the user.
 * It'b being used in the Administrator interface.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 */

public class User implements Serializable {
	
	//Attributes
	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The user id. */
    private int userId;
    
    /** The username. */
    @Pattern(regexp = "[A-Za-z0-9]{2,20}", message = "Please enter a username consisting of only letters and digits, between 2 and 20 characters long.")
    private String username;

    /** The password. */
    @Size(min = 8, message = "Your password must consist of at least 8 characters.")
    private String password;

    /** The first name. */
    @Size(min = 1, max = 30, message = "Please enter a first name between 1 and 30 characters long.")
    private String firstName;

    /** The last name. */
    @Size(min = 1, max = 30, message = "Please enter a last name between 1 and 30 characters long.")
    private String lastName;

    /** The email address which is being validated by the email validator. */
    @ValidEmailAddress
    private String emailAddress;

    /** The role of the user. */
    @NotNull
    private String role;

    
    //Empty Constructor
	/**
	 * Instantiates a new user.
	 */
    public User(){
   
    }
    
    
    //Getters and Setters
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address.
     *
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address.
     *
     * @param emailAddress the new email address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
