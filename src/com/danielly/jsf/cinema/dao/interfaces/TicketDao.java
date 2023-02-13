package com.danielly.jsf.cinema.dao.interfaces;

import java.util.List;

import com.danielly.jsf.cinema.model.Ticket;

/**
 * TicketDao is an Interface class which has all the methods to query the tickets table in the Database.
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */


public interface TicketDao {

	/**
	 * Gets the all tickets.
	 *
	 * @return the all tickets
	 */
	List<Ticket> getAllTickets();
	
	/**
	 * Gets the ticket.
	 *
	 * @param id the id
	 * @return the ticket
	 */
	Ticket getTicket(int id);
    
    /**
     * Insert ticket.
     *
     * @param ticket the ticket
     * @return true, if successful
     */
    boolean insertTicket(Ticket ticket);	
	
	/**
	 * Update ticket.
	 *
	 * @param ticket the ticket
	 * @return true, if successful
	 */
	boolean updateTicket(Ticket ticket);				
	
	/**
	 * Delete ticket.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	boolean deleteTicket(int id);
	
	/**
	 * Insert ticket and return the ticket id.
	 *
	 * @param ticket the ticket
	 * @return the int
	 */
	int insertTicketAndReturnId(Ticket ticket);
	
	/**
	 * Gets the tickets by order id.
	 *
	 * @param orderId the order id
	 * @return the tickets by order
	 */
	List<Ticket> getTicketsByOrder(int orderId);

}
