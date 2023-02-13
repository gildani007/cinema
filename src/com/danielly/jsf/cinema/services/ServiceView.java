package com.danielly.jsf.cinema.services;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;


/**
 * The Class ServiceView contains methods that related to the view and can be used by other classes.
 * It's being used to send message to the user about validation error or other errors.
 * In addition, to reload the web page in order to refresh the view.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


@Named
public class ServiceView implements Serializable {
	
	//Attributes
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
		
	//Class Methods
	/**
	 * Send message to view.
	 * It used a specific id name - globalMessage.
	 * @param message the message
	 */
	public void sendMessageToView(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,message, null));
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("globalMessage");
	}
	
	public void reloadPage() {	
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
}
