package com.danielly.jsf.cinema.services;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.dao.interfaces.UserDao;
import com.danielly.jsf.cinema.model.User;
import com.danielly.jsf.cinema.model.enums.UserRoles;

import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * The Class UserManager in charge of all of the user actions.
 * This class is being used in the Administrator interface.
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */


@Named
@SessionScoped
public class UserManager implements Serializable {

	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The user dao. */
    @Inject
    private UserDao userDao;
    
    /** The current user. */
    private User currentUser;
    
    //Class Methods
    /**
     * Checks if is signed in.
     *
     * @return true, if is signed in
     */
    public boolean isSignedIn() {
        return currentUser != null;
    }
    
    /**
     * Checks if the user has the administrator role.
     *
     * @return true, if the user has the administrator role
     */
    public boolean isAdmin() {
        return currentUser.getRole().equals(UserRoles.ADMIN.name());
    }
    
    /**
     * Checks if the user has the sales role.
     *
     * @return true, the user has the sales role
     */
    public boolean isSales() {
        return currentUser.getRole().equals(UserRoles.SALES.name());
    }
    
    /**
     * Checks if the user has the reporting role.
     *
     * @return true, if the user has the reporting role
     */
    public boolean isReporting() {
        return currentUser.getRole().equals(UserRoles.REPORTING.name());
    }

    /**
     * Sign in.
     *
     * @param username the username
     * @param password the password
     * @return the string
     */
    public String signIn(String username, String password) {  	
    	
    	  User user = userDao.getUserByName(username);
        if (!userDao.authenticateUser(username, password)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Please enter a valid username and password."));
            return "failure";
        }

        currentUser = user;
        return "success";
    }
    
    /**
     * Sign out.
     */
    public void signOut() {
        // End the session, removing any session state, including the current user and content of the shopping cart
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
      
    /**
     * Save.
     *
     * @param user the user
     * @return the string
     */
    public String save(User user) {
        userDao.updateUser(user);
        currentUser = user;
        return "admin-home";
    }
    
    //Getters
    /**
     * Gets the current user.
     *
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
}
