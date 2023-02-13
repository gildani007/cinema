package com.danielly.jsf.cinema.dao.interfaces;

import java.util.List;

import com.danielly.jsf.cinema.model.Price;

/**
 * PriceDao is an Interface class which has all the methods to query the prices table in the Database.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */

public interface PriceDao {

	/**
	 * Gets the all prices.
	 *
	 * @return the all prices
	 */
	List<Price> getAllPrices();
    
    /**
     * Gets the price.
     *
     * @param id the id
     * @return the price
     */
    Price getPrice(int id);
    
    /**
     * Insert price.
     *
     * @param price the price
     * @return true, if successful
     */
    boolean insertPrice(Price price);	
	
	/**
	 * Update price.
	 *
	 * @param price the price
	 * @return true, if successful
	 */
	boolean updatePrice(Price price);				
	
	/**
	 * Delete price.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	boolean deletePrice(int id);
}
