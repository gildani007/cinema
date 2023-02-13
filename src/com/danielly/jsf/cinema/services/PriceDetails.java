package com.danielly.jsf.cinema.services;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.dao.interfaces.PriceDao;
import com.danielly.jsf.cinema.model.Price;

import java.io.Serializable;

/**
 * The Class PriceDetails is a service class that being used to get the details of the price.
 * When using the class we firstly set the price id and than the onload gets the price object.
 * We are setting the parameters and loading with viewParam and viewAction in the web page.
 * Once we loaded the the price we can get the price object.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


@Named
@ViewScoped
public class PriceDetails implements Serializable {
	
	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The price dao. */
    @Inject
    private PriceDao priceDao;

    /** The price id. */
    private int priceId;

    /** The price. */
    private Price price;

    
    //Onload
    /**
     * Onload - this method is being loaded with every navigation action to this page
     * In addition, this method is also being called from web pages using the viewAction
     */
    public void onload() {
        price = priceDao.getPrice(priceId);
    }
    
    
    //Getters And Setters
    /**
     * Gets the price id.
     *
     * @return the price id
     */
    public int getPriceId() {
        return priceId;
    }

    /**
     * Sets the price id.
     *
     * @param priceId the new price id
     */
    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public Price getPrice() {
        return price;
    }
}
