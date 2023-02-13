package com.danielly.jsf.cinema.model;

import java.io.Serializable;

/**
 * The class Price holds the information about the price of ticket based on the category.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0 
 */

public class Price implements Serializable {

	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2L;
   
    /** The price id. */
    private int priceId;
    
    /** The price category. */
    private String category;
    
    /** The price in NIS currency. */
    private int priceNis;
    
   
	//Empty Constructor
    /**
     * Instantiates a new price.
     */
    public Price() {
    	
    }
      	

    //Getters and Setters
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
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * Gets the price in NIS currency.
	 *
	 * @return the price nis
	 */
	public int getPriceNis() {
		return priceNis;
	}
	
	/**
	 * Sets the price in NIS currency.
	 *
	 * @param priceNis the new price in NIS currency
	 */
	public void setPriceNis(int priceNis) {
		this.priceNis = priceNis;
	}

}
