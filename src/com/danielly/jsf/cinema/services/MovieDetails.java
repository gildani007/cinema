package com.danielly.jsf.cinema.services;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.dao.interfaces.MovieDao;
import com.danielly.jsf.cinema.model.Movie;

import java.io.Serializable;

/**
 * The Class MovieDetails is a service class that being used to get the details of the movie.
 * When using the class we firstly set the movie id and than the onload gets the movie object.
 * We are setting the parameters and loading with viewParam and viewAction in the web page.
 * Once we loaded the the movie we can get the movie object.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


@Named
@ViewScoped
public class MovieDetails implements Serializable {

	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The movie dao. */
    @Inject
    private MovieDao movieDao;

    /** The movie id. */
    private int movieId;
    
    /** The movie. */
    private Movie movie;

    
    //Onload
    /**
     * Onload - this method is being loaded with every navigation action to this page
     * In addition, this method is also being called from web pages using the viewAction
     */
    public void onload() {
    	movie = movieDao.getMovie(movieId);
    }
    
    
    //Getters And Setters
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
     * Gets the movie.
     *
     * @return the movie
     */
    public Movie getMovie() {
        return movie;
    }
    
}
