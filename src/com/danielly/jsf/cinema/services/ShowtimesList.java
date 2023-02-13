package com.danielly.jsf.cinema.services;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.dao.interfaces.ShowtimeDao;
import com.danielly.jsf.cinema.model.Showtime;


/**
 * The Class ShowtimesList is a service class that being used to get the list of showtimes.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */




@Named
@RequestScoped
public class ShowtimesList {
	
	//Attributes	
    /** The showtime dao. */
	@Inject
    private ShowtimeDao showtimeDao;

    /** The showtimes. */
	private List<Showtime> showtimes;

	
	
	//Initialize
    /**
     * Initialize method which is loaded before the class is put to service
     * It loads the list of showtimes and being access by the web pages
     */
	@PostConstruct
    public void initialize() {
    	showtimes = showtimeDao.getListOfCurrentShowtimes();	    	
    }

	//Getters
    /**
	 * Gets the showtimes.
	 *
	 * @return the showtimes
	 */
	public List<Showtime> getShowtimes(){	
        return showtimes;
    }
}
