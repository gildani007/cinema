package com.danielly.jsf.cinema.services;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.dao.interfaces.MovieDao;
import com.danielly.jsf.cinema.model.Movie;

import java.util.List;
import java.util.Map;

/**
 * The Class MoviesList is a service class that being used to get the lists of released and coming soon movies.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */

@Named
@RequestScoped
public class MoviesList {
	
	//Attributes
    /** The movie dao. */
    @Inject
    private MovieDao movieDao;

    /** The coming soon movies. */
    private List<Movie> comingSoonMovies;
    
    
    /** The movies map. */
    private static Map<Integer, Movie> moviesMap;

    
    //Initialize
    /**
     * Initialize method which is loaded before the class is put to service
     * It loads the list and map of movies and being access by the web pages
     */
    @PostConstruct
    public void initialize() {
        movieDao.getListOfReleasedMovies(); //this method will create the Movies Map
        moviesMap = movieDao.getReleasedMoviesMap();
        comingSoonMovies = movieDao.getComingSoonMovies();        
    }

    
    //Getters   
    /**
     * Gets the coming soon movies.
     *
     * @return the coming soon movies
     */
    public List<Movie> getComingSoonMovies() {
        return comingSoonMovies;
    }
    
    /**
     * Gets the movie by id.
     *
     * @param id the id
     * @return the movie by id
     */
    public Movie getMovieById(int id) {
        return moviesMap.get(new Integer(id));
    }
    
}
