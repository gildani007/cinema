package com.danielly.jsf.cinema.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.danielly.jsf.cinema.model.Seat;

/**
 * SeatDao is an Interface class which has all the methods to query the seats table in the Database.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


public interface SeatDao {

	/**
	 * Gets the all seats.
	 *
	 * @return the all seats
	 */
	List<Seat> getAllSeats();
    
    /**
     * Gets the seats by hall id.
     *
     * @param hallId the hall id
     * @return the seats by hall id
     */
    List<Seat> getSeatsByHallId(int hallId);
    
    /**
     * Gets the seat.
     *
     * @param seatId the seat id
     * @return the seat
     */
    Seat getSeat(int seatId);
    
    /**
     * Insert seat.
     *
     * @param seat the seat
     * @return true, if successful
     */
    boolean insertSeat(Seat seat);	
	
	/**
	 * Update seat.
	 *
	 * @param seat the seat
	 * @return true, if successful
	 */
	boolean updateSeat(Seat seat);				
	
	/**
	 * Delete seat.
	 *
	 * @param seatId the seat id
	 * @return true, if successful
	 */
	boolean deleteSeat(int seatId);
	
	/**
	 * Gets the seats map.
	 *
	 * @return the seats map
	 */
	Map<Integer, Seat> getSeatsMap();
	
	/**
	 * Delete all seat of a specific hall.
	 *
	 * @param hallId the hall id
	 * @return true, if successful
	 */
	boolean deleteAllSeatOfAHall(int hallId);

}
