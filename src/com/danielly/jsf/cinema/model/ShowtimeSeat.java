package com.danielly.jsf.cinema.model;

import java.io.Serializable;

/**
 * The class ShowtimeSeat holds the information about the seats of a showtime.
 * Using this class we can track the status of the seats of a specific showtime.
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */

public class ShowtimeSeat implements Serializable {

	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2L;
   
    /** The seat id. */
    private int seatId;
    
    /** The showtime id. */
    private int showtimeId;
	
	/** The isSeatTaken is being used to indicate if the seat is already been taken.
	 * 	In addition, it's being used in the GUI on page Order-Details.xhtml the availability of the seats.
	 *  Red seat will mean that the seat is taken
	 *  */
	private boolean isSeatTaken;
    
    /** The isSeatSelected is being used to show in the GUI on page Order-Details.xhtml the selections of the user. 
     * 	Green seat will mean that the seat was selected
     * */
    private boolean isSeatSelected;


	//Empty Constructor
	/**
	 * Instantiates a new showtime seat.
	 */
	public ShowtimeSeat() {
		
	}
		

	//Class Methods
	/**
	 * Change selection of a seat in a specific showtime.
	 * This method is being used in the order-details.xhtml when clicking on an available seat.
	 */
	public void changeSelection() {
 		this.isSeatSelected = !(this.isSeatSelected==true);
 	}
 	
 	/**
	  * Do nothing.
	  * This method is being used in the view as the click action of seats that are not available.
	  * It's being used in the Order-Deatils.xhtml
	  */
	 public void doNothing() {	
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
	 * Gets the checks if is seat taken.
	 *
	 * @return the checks if is seat taken
	 */
	public boolean getIsSeatTaken() {
		return isSeatTaken;
	}

	/**
	 * Sets the seat taken.
	 *
	 * @param isSeatTaken the new seat taken
	 */
	public void setSeatTaken(boolean isSeatTaken) {
		this.isSeatTaken = isSeatTaken;
	}

	/**
	 * Gets the checks if is seat selected.
	 *
	 * @return the checks if is seat selected
	 */
	public boolean getIsSeatSelected() {
		return isSeatSelected;
	}

	/**
	 * Sets the seat selected.
	 *
	 * @param isSeatSelected the new seat selected
	 */
	public void setSeatSelected(boolean isSeatSelected) {
		this.isSeatSelected = isSeatSelected;
	}


}
