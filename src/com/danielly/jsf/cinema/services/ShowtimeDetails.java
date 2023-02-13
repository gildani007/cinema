package com.danielly.jsf.cinema.services;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.dao.interfaces.ShowtimeDao;
import com.danielly.jsf.cinema.model.Showtime;

import java.io.Serializable;


/**
 * The Class ShowtimeDetails is a service class that being used to get the details of the showtime.
 * When using the class we firstly set the showtime id and than the onload gets the showtime object.
 * We are setting the parameters and loading with viewParam and viewAction in the web page.
 * Once we loaded the the showtime we can get the showtime object.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */




@Named
@ViewScoped
public class ShowtimeDetails implements Serializable {
	
	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The showtime dao. */
    @Inject
    private ShowtimeDao showtimeDao;

    /** The showtime id. */
    private int showtimeId;
    
    /** The showtime. */
    private Showtime showtime;

     
    //Onload
    /**
     * Onload - this method is being loaded with every navigation action to this page
     * In addition, this method is also being called from web pages using the viewAction
     */
    public void onload() {
    	showtime = showtimeDao.getShowtime(showtimeId);
    }
    
    
   
     //Getters And Setters
    /**
     * Gets the showtime id.
     *
     * @return the showtime id
     */
    public int getShowtimeId() {
        return showtimeId;
    }

    /**
     * Sets the showtime id.
     *
     * @param showtimeId the new showtime id
     */
    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }
 
    /**
     * Gets the showtime.
     *
     * @return the showtime
     */
    public Showtime getShowtime() {
        return showtime;
    }

}
