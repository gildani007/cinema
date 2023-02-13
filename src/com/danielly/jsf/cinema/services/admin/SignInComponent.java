package com.danielly.jsf.cinema.services.admin;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

/**
 * The Class SignInComponent is being used by the SignIn component to store its data and handle the action of the submit button.
 * It will be used when we will sign in to the Administrator interface in the GUI.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


@FacesComponent("com.danielly.jsf.component.SignIn")
public class SignInComponent extends UINamingContainer {

    /** The username. */
    private String username;

    /** The password. */
    private String password;

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
     * Submit.
     * It's being used by the SignIn custom component
     * The submit calls the method that the expression refers to.
     * The elContext is being used to find the method with the method signature specified in the SignIn custom component.
     * @return the object
     */
    public Object submit() {
    	//gets the method signature from the SignIn custom component.
        MethodExpression expression = (MethodExpression) getAttributes().get("action"); 

        ELContext elContext = getFacesContext().getELContext();
        Object[] params = {username, password};

        return expression.invoke(elContext, params); //run the method that was found with the parameters 
    }
}
