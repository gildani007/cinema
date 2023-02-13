package com.danielly.jsf.cinema.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.dao.interfaces.ShowtimeSeatsDao;
import com.danielly.jsf.cinema.model.ShowtimeSeat;

import java.util.List;

/**
 * The Class ShowtimeSeatsByShowtime is a service class that being used to get the showtime seats for a specific showtime.
 * When using the class we firstly set the showtime id and than the onload gets the showtime seats of the showtime.
 * We are setting the parameters and loading with viewParam and viewAction in the web page.
 * Once we loaded the the showtime seats we can get the list of showtime seats of that showtime.
 * With the showtime seats we will track the availability and the selections of the seats by the user in the GUI.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */




@Named
@RequestScoped
public class ShowtimeSeatsByShowtime {

	//Attributes
    /** The showtime seats dao. */
    @Inject
    private ShowtimeSeatsDao showtimeSeatsDao;
    
    /** The showtime id. */
    private int showtimeId;

    /** The showtime seat list by showtime. */
    private List<ShowtimeSeat> showtimeSeatListByShowtime;
    

    //Onload
    /**
     * Onload - this method is being loaded with every navigation action to this page
     * In addition, this method is also being called from web pages using the viewAction
     */
    public void onload() {
    	showtimeSeatListByShowtime = showtimeSeatsDao.getShowtimeSeatsByShowtime(showtimeId);
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
	 * Gets the showtime seat list by showtime.
	 *
	 * @return the showtime seat list by showtime
	 */
	public List<ShowtimeSeat> getShowtimeSeatListByShowtime() {
		return showtimeSeatListByShowtime;
	}

	/**
	 * Sets the showtime seat list by showtime.
	 *
	 * @param showtimeSeatListByShowtime the new showtime seat list by showtime
	 */
	public void setShowtimeSeatListByShowtime(List<ShowtimeSeat> showtimeSeatListByShowtime) {
		this.showtimeSeatListByShowtime = showtimeSeatListByShowtime;
	}
 
}
