package com.danielly.jsf.cinema.model;

import java.io.Serializable;

/**
 * The class TicketSeat holds the information about the seats of the tickets.
 * Using this class we can track the assignment of the seats to the tickets .
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 */

public class TicketSeat implements Serializable {

	//Attributes
	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2L;
   
    /** The seat id. */
    private int seatId;
    
    /** The ticket id. */
    private int ticketId;


	//Empty Constructor
	/**
	 * Instantiates a new ticket seat.
	 */
	public TicketSeat() {
		
	}

	
	//Getters and Setters

	/**
	 * Gets the seat id.
	 *
	 * @return the seat id
	 */
	public int getSeatId() {
		return seatId;
	}

	/**
	 * Sets the seat id.
	 *
	 * @param seatId the new seat id
	 */
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

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
		
}
