package com.danielly.jsf.cinema.model;

import java.io.Serializable;

/**
 * The class Hall holds the information about a hall, the number of seats and the layout.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 */

public class Hall implements Serializable {

	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2L;
    
    /** The hall id. */
    private int hallId;
    
    /** The hall name. */
    private String hallName;
    
    /** The total number of seats. */
    private int totalNumberOfSeats;
	
	/** The number of rows. */
	private int numberOfRows;
	
	/** The seats per row. */
	private int seatsPerRow;
	

	//Empty Constructor
	/**
	 * Instantiates a new hall.
	 */
	public Hall(){
		
	}
	
	//Getters and Setters
    /**
     * Gets the number of rows.
     *
     * @return the number of rows
     */
    public int getNumberOfRows() {
		return numberOfRows;
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


	/**
	 * Gets the hall name.
	 *
	 * @return the hall name
	 */
	public String getHallName() {
		return hallName;
	}


	/**
	 * Sets the hall name.
	 *
	 * @param hallName the new hall name
	 */
	public void setHallName(String hallName) {
		this.hallName = hallName;
	}


	/**
	 * Gets the total number of seats.
	 *
	 * @return the total number of seats
	 */
	public int getTotalNumberOfSeats() {
		return totalNumberOfSeats;
	}


	/**
	 * Sets the total number of seats.
	 *
	 * @param totalNumberOfSeats the new total number of seats
	 */
	public void setTotalNumberOfSeats(int totalNumberOfSeats) {
		this.totalNumberOfSeats = totalNumberOfSeats;
	}


	/**
	 * Checks if is number of rows.
	 *
	 * @return the number of rows
	 */
	public int isNumberOfRows() {
		return numberOfRows;
	}


	/**
	 * Sets the number of rows.
	 *
	 * @param numberOfRows the new number of rows
	 */
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}


	/**
	 * Gets the seats per row.
	 *
	 * @return the seats per row
	 */
	public int getSeatsPerRow() {
		return seatsPerRow;
	}


	/**
	 * Sets the seats per row.
	 *
	 * @param seatsPerRow the new seats per row
	 */
	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}	

}
