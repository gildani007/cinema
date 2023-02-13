package com.danielly.jsf.cinema.services;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.dao.interfaces.HallDao;
import com.danielly.jsf.cinema.model.Hall;

import java.io.Serializable;


/**
 * The Class HallDetails is a service class that being used to get the details of the hall.
 * When using the class we firstly set the hall id and than the onload gets the hall object.
 * We are setting the parameters and loading with viewParam and viewAction in the web page.
 * Once we loaded the the hall we can get the hall object.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


@Named
@ViewScoped
public class HallDetails implements Serializable {

	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The hall dao. */
    @Inject
    private HallDao hallDao;

    /** The hall id. */
    private int hallId;

    /** The hall. */
    private Hall hall;
    
    
    //Onload
    /**
     * Onload - this method is being loaded with every navigation action to this page
     * In addition, this method is also being called from web pages using the viewAction
     */
    public void onload() {
        hall = hallDao.getHall(hallId);
    }
    
    
    
    //Getters And Setters
    /**
     * Gets the hall id.
     *
     * @return the hall id
     */
    public int getHallId() {
        return hallId;
    }

    /**
     * Sets the hall id.
     *
     * @param hallId the new hall id
     */
    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    /**
     * Gets the hall.
     *
     * @return the hall
     */
    public Hall getHall() {
        return hall;
    }
}
