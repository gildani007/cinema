package com.danielly.jsf.cinema.model;

import java.io.Serializable;

/**
 * The class Seat holds the information about the seat, its hall and its position in the layout of the hall.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 */

public class Seat implements Serializable {
	
	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2L;
   
    /** The seat id. */
    private int seatId;
    
    /** The row number. */
    private int rowNumber;
    
    /** The seat number. */
    private int seatNumber;
	
	/** The hall id. */
	private int hallId;

	
	//Empty Constructor
	/**
	 * Instantiates a new seat.
	 */
	public Seat() {
		
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
     * Gets the row number.
     *
     * @return the row number
     */
    public int getRowNumber() {
        return rowNumber;
    }
    
	/**
	 * Sets the row number.
	 *
	 * @param rowNumber the new row number
	 */
	public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * Gets the seat number.
     *
     * @return the seat number
     */
    public int getSeatNumber() {
        return seatNumber;
    }
    
    /**
     * Sets the seat number.
     *
     * @param seatNumber the new seat number
     */
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
  
    /**
     * Gets the hall id.
     *
     * @return the hall id
     */
    public int getHallId() {
        return hallId;
    }
    
    /**
     * Sets the hall id.
     *
     * @param hallId the new hall id
     */
    public void setHallId(int hallId) {
        this.hallId = hallId;
    }


}
