package com.danielly.jsf.cinema.dao.interfaces;

import java.util.List;

import com.danielly.jsf.cinema.model.Hall;

/**
 * HallDao is an Interface class which has all the methods to query the halls table in the Database.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


public interface HallDao {

	/**
	 * Gets the all halls.
	 *
	 * @return the all halls
	 */
	List<Hall> getAllHalls();
    
    /**
     * Gets the hall.
     *
     * @param id the id
     * @return the hall
     */
    Hall getHall(int id);
    
    /**
     * Insert hall.
     *
     * @param hall the hall
     * @return true, if successful
     */
    boolean insertHall(Hall hall);	
	
	/**
	 * Update hall.
	 *
	 * @param hall the hall
	 * @return true, if successful
	 */
	boolean updateHall(Hall hall);				
	
	/**
	 * Delete hall.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	boolean deleteHall(int id);
	
	/**
	 * Insert hall and return hall id.
	 *
	 * @param hall the hall
	 * @return the int
	 */
	int insertHallAndReturnId(Hall hall);

}
