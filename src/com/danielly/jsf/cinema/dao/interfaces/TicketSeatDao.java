package com.danielly.jsf.cinema.dao.interfaces;

import java.util.List;

import com.danielly.jsf.cinema.model.TicketSeat;

/**
 * TicketSeatDao is an Interface class which has all the methods to query the tickets_seats table in the Database.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


public interface TicketSeatDao {

	/**
	 * Gets the all seat tickets.
	 *
	 * @return the all seat tickets
	 */
	List<TicketSeat> getAllSeatTickets();

	/**
	 * Insert ticket seat.
	 *
	 * @param ticketId the ticket id
	 * @param seatId the seat id
	 * @return true, if successful
	 */
	boolean insertTicketSeat(int ticketId, int seatId);

	/**
	 * Delete ticket seat.
	 *
	 * @param ticketId the ticket id
	 * @return true, if successful
	 */
	boolean deleteTicketSeat(int ticketId);

	/**
	 * Gets the ticket seat by ticket id.
	 *
	 * @param ticketId the ticket id
	 * @return the ticket seat by ticket id
	 */
	TicketSeat getTicketSeatByTicketId(int ticketId);



}
