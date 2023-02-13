package com.danielly.jsf.cinema.services;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.model.User;

import java.io.Serializable;


/**
 * The Class UserDetails is in charge on changing the details of an existing user.
 * It provides signed in users the ability to change their details.
 * It's being used in the administrator interface in the page user-details.xhtml.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


@Named
@ViewScoped
public class UserDetails implements Serializable {

	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The user manager. */
    @Inject
    private UserManager userManager;

    /** The user. */
    private User user;
    

	//Onload
    /**
	 * Onload - this method is being loaded with every navigation action to this page
	 * Check if the user signed in.
	 */
    public void onload() {
        user = userManager.isSignedIn() ? userManager.getCurrentUser() : new User();
    }

    
    //Class Methods
    /**
     * Submit will save the details of the user in the users table in the database. 
     *
     * @return the string
     */
    public String submit() {
        return userManager.save(user);
    }
    
    
    //Getters
    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }
}
