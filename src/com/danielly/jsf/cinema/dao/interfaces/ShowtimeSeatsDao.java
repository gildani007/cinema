package com.danielly.jsf.cinema.dao.interfaces;


import java.util.List;

import com.danielly.jsf.cinema.model.ShowtimeSeat;

/**
 * ShowtimeSeatsDao is an Interface class which has all the methods to query the showtimes_seats table in the Database.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


public interface ShowtimeSeatsDao {

	/**
	 * Gets the all showtime seats.
	 *
	 * @return the all showtime seats
	 */
	List<ShowtimeSeat> getAllShowtimeSeats();

	/**
	 * Gets the showtime seat.
	 *
	 * @param seatId the seat id
	 * @param showtimeId the showtime id
	 * @return the showtime seat
	 */
	ShowtimeSeat getShowtimeSeat(int seatId, int showtimeId);

	/**
	 * Insert showtime seat.
	 *
	 * @param showtimeSeat the showtime seat
	 * @return true, if successful
	 */
	boolean insertShowtimeSeat(ShowtimeSeat showtimeSeat);

	/**
	 * Update showtime seat.
	 *
	 * @param showtimeSeat the showtime seat
	 * @return true, if successful
	 */
	boolean updateShowtimeSeat(ShowtimeSeat showtimeSeat);

	/**
	 * Delete showtime seat.
	 *
	 * @param seatId the seat id
	 * @param showtimeId the showtime id
	 * @return true, if successful
	 */
	boolean deleteShowtimeSeat(int seatId, int showtimeId);

	/**
	 * Gets the showtime seats of a specific showtime.
	 *
	 * @param showtimeId the showtime id
	 * @return the showtime seats by showtime
	 */
	List<ShowtimeSeat> getShowtimeSeatsByShowtime(int showtimeId);

	/**
	 * Insert showtime seats.
	 *
	 * @param showtimeId the showtime id
	 * @return true, if successful
	 */
	boolean insertShowtimeSeats(int showtimeId);

	/**
	 * Delete showtime seats for a specific showtime.
	 *
	 * @param showtimeId the showtime id
	 * @return true, if successful
	 */
	boolean deleteShowtimeSeatsForShowtime(int showtimeId);

	/**
	 * Update showtime seat to taken.
	 *
	 * @param showtimeId the showtime id
	 * @param seatId the seat id
	 * @return true, if successful
	 */
	boolean updateShowtimeSeatToTaken(int showtimeId, int seatId);

	/**
	 * Update showtime seat to not taken.
	 *
	 * @param showtimeId the showtime id
	 * @param seatId the seat id
	 * @return true, if successful
	 */
	boolean updateShowtimeSeatToNotTaken(int showtimeId, int seatId);
}
