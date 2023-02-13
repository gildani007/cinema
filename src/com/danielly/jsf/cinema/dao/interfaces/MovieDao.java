package com.danielly.jsf.cinema.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.danielly.jsf.cinema.model.Movie;

/**
 * MovieDao is an Interface class which has all the methods to query the movies table in the Database.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */

public interface MovieDao {

    /**
     * Gets the list of released movies.
     *
     * @return the list of released movies
     */
    List<Movie> getListOfReleasedMovies();
    
    /**
     * Gets the coming soon movies.
     *
     * @return the coming soon movies
     */
    List<Movie> getComingSoonMovies();
    
    /**
     * Gets the released movies map.
     *
     * @return the released movies map
     */
    Map<Integer, Movie> getReleasedMoviesMap();

    /**
     * Gets the movie.
     *
     * @param id the id
     * @return the movie
     */
    Movie getMovie(int id);
	
	/**
	 * Insert movie.
	 *
	 * @param movie the movie
	 * @return true, if successful
	 */
	boolean insertMovie(Movie movie);	
	
	/**
	 * Update movie.
	 *
	 * @param movie the movie
	 * @return true, if successful
	 */
	boolean updateMovie(Movie movie);				
	
	/**
	 * Delete movie.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	boolean deleteMovie(int id);

}
