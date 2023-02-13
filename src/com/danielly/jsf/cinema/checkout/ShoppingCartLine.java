package com.danielly.jsf.cinema.checkout;

import com.danielly.jsf.cinema.model.Hall;
import com.danielly.jsf.cinema.model.Movie;
import com.danielly.jsf.cinema.model.Price;
import com.danielly.jsf.cinema.model.Seat;
import com.danielly.jsf.cinema.model.Showtime;

/**
 * The class ShoppingCartLine holds the information about a specific ticket the user wish to purchase.
 * It contains the information about the showtime, the movie, hall and the seat.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 */

public class ShoppingCartLine {

	//Attributes
	/** The showtime. */
	private Showtime showtime;
    
    /** The movie. */
    private Movie movie;
    
    /** The hall. */
    private Hall hall;
    
	/** The seat. */
	private Seat seat;
    
    /** The quantity. */
    private int quantity;
    
    /** The amount. */
    private int amount;
    
    /** The ticket id. */
    private int ticketId;
    

    
    //Constructor for ShoppingCartLine with allocated\assigned seat
    /**
     * Instantiates a new shopping cart line with allocated seat.
     *
     * @param showtime the showtime
     * @param seat the seat
     * @param price the price
     * @param movie the movie
     * @param hall the hall
     */
    public ShoppingCartLine(Showtime showtime, Seat seat, Price price, Movie movie, Hall hall) {
        this.showtime = showtime;
        this.seat = seat;        
        this.amount = price.getPriceNis();
        this.movie = movie;
        this.hall = hall; 
        this.quantity = 1;
    }    

    //Constructor for ShoppingCartLine without allocated\assigned seat
    /**
     * Instantiates a new shopping cart line without allocated seat.
     *
     * @param showtime the showtime
     * @param price the price
     * @param movie the movie
     * @param hall the hall
     */ 
    public ShoppingCartLine(Showtime showtime, Price price, Movie movie, Hall hall) {
        this.showtime = showtime;       
        this.amount = price.getPriceNis();
        this.movie = movie;
        this.hall = hall; 
        this.quantity = 1;
    } 
 
    
    //Class Methods 
    /**
     * Increment quantity of the tickets and amount.
     */
    public void incrementQuantityAndAmount() {
        quantity++;
        amount = amount + amount;
    }

    /**
     * Decrement quantity of the tickets and amount.
     *
     * @return true, if successful
     */
    public boolean decrementQuantityAndAmount() {
        quantity--;
        amount = amount - amount;
        return quantity == 0;
    }

    
    //Getters and Setters  
    /**
     * Gets the showtime.
     *
     * @return the showtime
     */
	public Showtime getShowtime() {
		return showtime;
	 }

	/**
	 * Gets the movie.
	 *
	 * @return the movie
	 */
	public Movie getMovie() {
		return movie;
	}

	/**
	 * Gets the hall.
	 *
	 * @return the hall
	 */
	public Hall getHall() {
		return hall;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Gets the seat.
	 *
	 * @return the seat
	 */
	public Seat getSeat() {
		return seat;
	}

	/**
	 * Sets the seat.
	 *
	 * @param seat the new seat
	 */
	public void setSeat(Seat seat) {
		this.seat = seat;
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
