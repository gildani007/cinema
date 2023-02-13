
package com.danielly.jsf.cinema.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.danielly.jsf.cinema.model.Showtime;

/**
 * ShowtimeDao is an Interface class which has all the methods to query the showtimes table in the Database.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


public interface ShowtimeDao {
	
    /**
     * Gets the list of showtimes.
     *
     * @return the list of showtimes
     */
    List<Showtime> getListOfShowtimes();
    
    /**
     * Gets the showtimes map.
     *
     * @return the showtimes map
     */
    Map<Integer, Showtime> getShowtimesMap();
    
    /**
     * Gets the showtime.
     *
     * @param showtimeId the showtime id
     * @return the showtime
     */
    Showtime getShowtime(int showtimeId);
	
	/**
	 * Insert showtime.
	 *
	 * @param showtime the showtime
	 * @return true, if successful
	 */
	boolean insertShowtime(Showtime showtime);	
	
	/**
	 * Update showtime.
	 *
	 * @param showtime the showtime
	 * @return true, if successful
	 */
	boolean updateShowtime(Showtime showtime);				
	
	/**
	 * Delete showtime.
	 *
	 * @param showtimeId the showtime id
	 * @return true, if successful
	 */
	boolean deleteShowtime(int showtimeId);
	
	/**
	 * Insert showtime and return showtime id.
	 *
	 * @param showtime the showtime
	 * @return the int
	 */
	int insertShowtimeAndReturnId(Showtime showtime);
	
	/**
	 * Increment the showtime avaialable tickets by 1.
	 *
	 * @param showtimeId the showtime id
	 * @return true, if successful
	 */
	boolean incrementShowtimeAvaialableTickets(int showtimeId);
	
	/**
	 * Decrement the showtime avaialable tickets by 1.
	 *
	 * @param showtimeId the showtime id
	 * @return true, if successful
	 */
	boolean decrementShowtimeAvaialableTickets(int showtimeId);
	
	/**
	 * Change the amount of the available tickets in a showtime .
	 *
	 * @param showtimeId the showtime id
	 * @param amount the amount
	 * @return true, if successful
	 */
	boolean changeAmountShowtimeAvailableTickets(int showtimeId, int amount);

	/**
	 * Gets the list of the current showtimes from the showtimes table in the database.
	 * It will not include deleted showtimes
	 *
	 * @return the list of showtimes
	 */
	List<Showtime> getListOfCurrentShowtimes();
	
}
