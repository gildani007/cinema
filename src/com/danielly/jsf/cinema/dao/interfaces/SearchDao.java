package com.danielly.jsf.cinema.dao.interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.faces.model.SelectItem;

import com.danielly.jsf.cinema.model.Showtime;

/**
 * SearchDao is an Interface class which has all the methods needed to the search GUI and to query the showtimes table in the Database.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


public interface SearchDao {
     
 	/**
 	 * Find all showtimes hours.
 	 *
 	 * @return the list
 	 */
 	List<String> findAllShowtimesHours();
	 
 	/**
 	 * Find all showtime movies.
 	 *
 	 * @return the list
 	 */
 	List<String> findAllShowtimeMovies();
	 
 	/**
 	 * Search showtimes by filter.
 	 *
 	 * @param date the date
 	 * @param hour the hour
 	 * @param movie the movie
 	 * @return the list
 	 */
 	List<Showtime> searchByfilter(String date, String hour, String movie);
	 
 	/**
 	 * Gets the filtered showtimes.
 	 *
 	 * @return the filtered showtimes
 	 */
 	List<Showtime> getFilteredShowtimes();	 
	 
 	/**
 	 * Gets the selection hours.
 	 *
 	 * @return the selection hours
 	 */
 	List<SelectItem> getSelectionHours();
	 
 	/**
 	 * Gets the selection movies.
 	 *
 	 * @return the selection movies
 	 */
 	List<SelectItem> getSelectionMovies();
	 
 	/**
 	 * Convert string to time.
 	 *
 	 * @param stringTime the string time
 	 * @return the local time
 	 */
 	LocalTime convertStringToTime(String stringTime);
	 
 	/**
 	 * Convert string to date.
 	 *
 	 * @param stringDate the string date
 	 * @return the local date
 	 */
 	LocalDate convertStringToDate(String stringDate);
	 
 	/**
 	 * Convert local date to string.
 	 *
 	 * @param localDate the local date
 	 * @return the string
 	 */
 	String convertLocalDateToString(LocalDate localDate);
}
