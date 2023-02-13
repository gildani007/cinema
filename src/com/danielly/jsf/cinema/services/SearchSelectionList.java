package com.danielly.jsf.cinema.services;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.dao.interfaces.SearchDao;
import com.danielly.jsf.cinema.model.Showtime;

import java.util.List;


/**
 * The Class SearchSelectionList is used in the search component.
 * It presents the selection to the user to search from the existing showtimes.
 * In addition, return the filter showtimes after the search.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */

@Named("search_data")
@RequestScoped

public class SearchSelectionList {

	/** The search dao. */
	@Inject
    private SearchDao searchDao;
  	
	/** The select items one hour. */
	private List<SelectItem> selectItemsOneHour;
    
    /** The select items one movie. */
    private List<SelectItem> selectItemsOneMovie;
  
    /** The showtimes. */
    private List<Showtime> showtimes;
	
	/** The hour. */
	private String hour;
	
	/** The date. */
	private String date;
    
    /** The movie. */
    private String movie;

    
    
    //Initialize
    /**
     * Initialize method which is loaded before the class is put to service
     * It loads the list and map of selections and being access by the search component in the web pages
     */
    @PostConstruct 
    public void initialize() {
    	selectItemsOneHour = searchDao.getSelectionHours();
    	selectItemsOneMovie = searchDao.getSelectionMovies();
    }
    
    
    //Getters And Setters
	/**
	 * Gets the filtered showtimes by the choices the user made in the GUI.
	 * 
	 * @return the showtimes
	 */
	public List<Showtime> getShowtimes() {
		showtimes = searchDao.searchByfilter(date, hour, movie);
        return showtimes;
    }    

  	/**
	   * Gets the select items one hour.
	   *
	   * @return the select items one hour
	   */
	  public List<SelectItem> getSelectItemsOneHour() {
  		return selectItemsOneHour;
  	}

  	/**
	   * Sets the select items one hour.
	   *
	   * @param selectItemsOneHour the new select items one hour
	   */
	  public void setSelectItemsOneHour(List<SelectItem> selectItemsOneHour) {
  		this.selectItemsOneHour = selectItemsOneHour;
  	}

  	/**
	   * Gets the select items one movie.
	   *
	   * @return the select items one movie
	   */
	  public List<SelectItem> getSelectItemsOneMovie() {
  		return selectItemsOneMovie;
  	}

  	/**
	   * Sets the select items one movie.
	   *
	   * @param selectItemsOneMovie the new select items one movie
	   */
	  public void setSelectItemsOneMovie(List<SelectItem> selectItemsOneMovie) {
  		this.selectItemsOneMovie = selectItemsOneMovie;
  	}

    /**
     * Gets the hour.
     *
     * @return the hour
     */
    public String getHour() {
		return hour;
	}

	/**
	 * Sets the hour.
	 *
	 * @param hour the new hour
	 */
	public void setHour(String hour) {
		this.hour = hour;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets the movie.
	 *
	 * @return the movie
	 */
	public String getMovie() {
		return movie;
	}
	

	/**
	 * Sets the movie.
	 *
	 * @param movie the new movie
	 */
	public void setMovie(String movie) {
		this.movie = movie;
	}

    /**
     * Sets the showtimes.
     *
     * @param showtimes the new showtimes
     */
    public void setShowtimes(List<Showtime> showtimes) {
		this.showtimes = showtimes;
	}


   
}
