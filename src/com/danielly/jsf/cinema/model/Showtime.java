package com.danielly.jsf.cinema.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The class Showtime holds the information about the Showtime, the time, the movie, the hall and the price.
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */

public class Showtime implements Serializable {
	
	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2L;
   
    /** The showtime id. */
    private int showtimeId;
    
    /** The showtime date hour. */
    private LocalDateTime showtimeDateHour;
    
    /** The total tickets. */
    private int totalTickets;
    
    /** The movie id. */
    private int movieId;
    
    /** The price id. */
    private int priceId;
	
	/** The hall id. */
	private int hallId;
	
	/** The number of available tickets for this showtime. */
	private int availableTickets;
	
	/** The showtime deleted is indicator if a showtime deleted. */
	private boolean showtime_deleted;
	
	/** The showtime hour. */
	private String showtimeHour;
	
	/** The showtime date. */
	private String showtimeDate;
	

	//Empty Constructor
	/**
	 * Instantiates a new showtime.
	 */
	public Showtime() {
	}
		
	
	//Getters and Setters
	/**
	 * Gets the day string.
	 *
	 * @param date the date
	 * @return the day string
	 */
	public static String getDayString(LocalDate date) 
	{
	    DayOfWeek day = date.getDayOfWeek();
	    return day.getDisplayName(TextStyle.FULL, Locale.getDefault());
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
	 * Gets the showtime hour.
	 *
	 * @return the showtime hour
	 */
	public String getShowtimeHour() {
		return showtimeHour;
	}

	/**
	 * Gets the showtime date.
	 *
	 * @return the showtime date
	 */
	public String getShowtimeDate() {
		return showtimeDate;
	}
	
	/**
	 * Sets the showtime hour.
	 *
	 * @param showtimeHour the new showtime hour
	 */
	public void setShowtimeHour(String showtimeHour) {
		this.showtimeHour = showtimeHour;
	}

	/**
	 * Sets the showtime date.
	 *
	 * @param showtimeDate the new showtime date
	 */
	public void setShowtimeDate(String showtimeDate) {
		this.showtimeDate = showtimeDate;
	}
	
	/**
	 * Gets the showtime date hour.
	 *
	 * @return the showtime date hour
	 */
	public LocalDateTime getShowtimeDateHour() {
		return showtimeDateHour;
	}


	/**
	 * Sets the showtime date hour.
	 *
	 * @param showtimeDateHour the new showtime date hour
	 */
	public void setShowtimeDateHour(LocalDateTime showtimeDateHour) {
		this.showtimeDateHour = showtimeDateHour;
	}


	/**
	 * Gets the movie id.
	 *
	 * @return the movie id
	 */
	public int getMovieId() {
		return movieId;
	}


	/**
	 * Sets the movie id.
	 *
	 * @param movieId the new movie id
	 */
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}


	/**
	 * Gets the price id.
	 *
	 * @return the price id
	 */
	public int getPriceId() {
		return priceId;
	}


	/**
	 * Sets the price id.
	 *
	 * @param priceId the new price id
	 */
	public void setPriceId(int priceId) {
		this.priceId = priceId;
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
	 * Gets the total tickets.
	 *
	 * @return the total tickets
	 */
	public int getTotalTickets() {
		return totalTickets;
	}

	/**
	 * Sets the total tickets.
	 *
	 * @param totalTickets the new total tickets
	 */
	public void setTotalTickets(int totalTickets) {
		this.totalTickets = totalTickets;
	}

	/**
	 * Gets the available tickets.
	 *
	 * @return the available tickets
	 */
	public int getAvailableTickets() {
		return availableTickets;
	}

	/**
	 * Sets the available tickets.
	 *
	 * @param availableTickets the new available tickets
	 */
	public void setAvailableTickets(int availableTickets) {
		this.availableTickets = availableTickets;
	}


	/**
	 * Checks if is showtime deleted.
	 *
	 * @return true, if is showtime deleted
	 */
	public boolean isShowtime_deleted() {
		return showtime_deleted;
	}


	/**
	 * Sets the showtime deleted.
	 *
	 * @param showtime_deleted the new showtime deleted
	 */
	public void setShowtime_deleted(boolean showtime_deleted) {
		this.showtime_deleted = showtime_deleted;
	}
	



}