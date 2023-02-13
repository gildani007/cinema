package com.danielly.jsf.cinema.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.dao.interfaces.SeatDao;
import com.danielly.jsf.cinema.model.Seat;

import java.util.List;


/**
 * The Class SeatsByHall is a service class that being used to get the seats of the hall.
 * When using the class we firstly set the hall id and than the onload gets the seats of the hall.
 * We are setting the parameters and loading with viewParam and viewAction in the web page.
 * Once we loaded the the seats we can get the list of seat which belong to that hall.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


@Named
@RequestScoped
public class SeatsByHall {
	
	//Attributes
    /** The seat dao. */
    @Inject
    private SeatDao seatDao;
    
    /** The hall id. */
    private int hallId;

    /** The seats by hall. */
    private List<Seat> seatsByHall;
    

    //Onload
    /**
     * Onload - this method is being loaded with every navigation action to this page
     * In addition, this method is also being called from web pages using the viewAction
     */
    public void onload() {
    	seatsByHall = seatDao.getSeatsByHallId(hallId);
    }

    
    //Getters And Setters
	/**
	 * Gets the seats by hall.
	 *
	 * @return the seats by hall
	 */
	public List<Seat> getSeatsByHall() {
		return seatsByHall;
	}
    
	/**
	 * Gets the hall id.
	 *
	 * @return the hall id
	 */
	public int getHallId() {
		return hallId;
	}
	
    /**
     * Sets the seats by hall.
     *
     * @param seatsByHall the new seats by hall
     */
    public void setSeatsByHall(List<Seat> seatsByHall) {
		this.seatsByHall = seatsByHall;
	}

	/**
	 * Sets the hall id.
	 *
	 * @param hallId the new hall id
	 */
	public void setHallId(int hallId) {
		this.hallId = hallId;
	}
 
}
