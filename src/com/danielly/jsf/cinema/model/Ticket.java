package com.danielly.jsf.cinema.model;

import java.io.Serializable;
import javax.inject.Named;

/**
 * The class Ticket holds the information about the ticket.
 * Ticket can have assigned\allocated seat or without assigned seat - free seating
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 */


@Named
public class Ticket implements Serializable {

	//Attributes
	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2L;
    
    /** The ticket id. */
    private int ticketId;
    
    /** The isSeatAllocated is being used to indicate if this ticket has allocated\assigned seat. */
    private Boolean isSeatAllocated;
	
	/** The showtime id. */
	private int showtimeId;
	
	/** The order id. */
	private int orderId;

	
	//Empty Constructor
	/**
	 * Instantiates a new ticket.
	 */
	public Ticket(){
		
	}	
	
	
	//Getters and Setters
	/**
	 * Gets the ticket id.
	 *
	 * @return the ticket id
	 */
	public int getTicketId() {
		return ticketId;
	}

	/**
	 * Sets the ticket id.
	 *
	 * @param ticketId the new ticket id
	 */
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * Gets the checks if is seat allocated.
	 *
	 * @return the checks if is seat allocated
	 */
	public Boolean getIsSeatAllocated() {
		return isSeatAllocated;
	}

	/**
	 * Sets the checks if is seat allocated.
	 *
	 * @param isSeatAllocated the new checks if is seat allocated
	 */
	public void setIsSeatAllocated(Boolean isSeatAllocated) {
		this.isSeatAllocated = isSeatAllocated;
	}

	/**
	 * Gets the showtime id.
	 *
	 * @return the showtime id
	 */
	public int getShowtimeId() {
		return showtimeId;
	}

	/**
	 * Sets the showtime id.
	 *
	 * @param showtimeId the new showtime id
	 */
	public void setShowtimeId(int showtimeId) {
		this.showtimeId = showtimeId;
	}

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Sets the order id.
	 *
	 * @param orderId the new order id
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
}
