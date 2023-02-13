package com.danielly.jsf.cinema.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.danielly.jsf.cinema.dao.interfaces.MovieDao;
import com.danielly.jsf.cinema.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class MovieDaoImpl contains all the methods implementation to query the Movies table.
 * It contains the basic CRUD methods and additional methods that were needed.
 * This class implements the MovieDao interface
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */

@ApplicationScoped
public class MovieDaoImpl implements MovieDao {

	
	//Attributes
	/** The Database Connection Controller. */
	@Inject
	private DbConnectionController dbConnectionController;
	 
    /** The connection to the database. */
    private Connection connection;  
    
	/** The list of movies that already released. */
	private List<Movie> listOfReleasedMovies;
    
    /** The list of movies that are coming soon. */
    private List<Movie> listOfComingSoonMovies;
    
    /** The movies map which contains the movie Id and the Movie object. 
     *  This Map will be used to find movie by Id.
     *  The map will be created from getListOfReleasedMovies while storing each of the movie object.
     * */
    private Map<Integer,Movie> releasedMoviesMap;
    
    /** The Constant ONE_AFFECTED_OR_MATCHED_ROW is used to check if there is 1 affected or matched row.*/
    private final static int ONE_AFFECTED_OR_MATCHED_ROW = 1;
    

    //Empty Constructor
	/**
	 * Instantiates a new movie dao impl.
	 */
	public MovieDaoImpl() { 
    }
    
  
	//Database Queries Methods
    /**
     * Gets the list of movies that already released from the movies table in the database.
     *
     * @return the list of movies that already released
     */
    @Override
    public List<Movie> getListOfReleasedMovies() {
    	listOfReleasedMovies = new ArrayList<>();
    	releasedMoviesMap = new HashMap<Integer,Movie>();
    	Statement stmt = null;
    	ResultSet rs = null;
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("SELECT movie_id, movie_name, description, movie_year, movie_length, director, main_actor, genre, mpaa_rating, is_coming_soon, image_name, movie_deleted FROM public.movies WHERE movie_deleted=false and is_coming_soon=false;");    
    		while(rs.next())
    			{  
    			Movie movie = new Movie();  
	    		movie.setMovieId(rs.getInt("movie_id"));  
	    		movie.setName(rs.getString("movie_name"));  
	    		movie.setDescription(rs.getString("description"));
	    		movie.setYear(rs.getInt("movie_year"));  
	    		movie.setLength(rs.getInt("movie_length")); 
	    		movie.setDirector(rs.getString("director")); 
	    		movie.setMainActor(rs.getString("main_actor")); 
	    		movie.setGenre(rs.getString("genre")); 
	    		movie.setMpaaRating(rs.getString("mpaa_rating"));
	    		movie.setImageName(rs.getString("image_name"));
	    		movie.setMovie_deleted(rs.getBoolean("movie_deleted"));
	    		
	    		listOfReleasedMovies.add(movie);
	    		releasedMoviesMap.put(new Integer(movie.getMovieId()),movie);
    			}  
    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt,connection);
    				}
    		
    return listOfReleasedMovies;
    }  
    
    /**
     * Gets the list of movies that are coming soon from the movies table in the database.
     *
     * @return the list of movies that are coming soon
     */
    @Override
    public List<Movie> getComingSoonMovies() {
    	listOfComingSoonMovies = new ArrayList<>();
    	Statement stmt = null;
    	ResultSet rs = null;
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("SELECT movie_id, movie_name, description, movie_year, movie_length, director, main_actor, genre, mpaa_rating, image_name, is_coming_soon, movie_deleted FROM movies where movie_deleted=false and is_coming_soon=true");    
    		while(rs.next())
    			{  
    			Movie movie = new Movie();  
	    		movie.setMovieId(rs.getInt("movie_id"));  
	    		movie.setName(rs.getString("movie_name"));  
	    		movie.setDescription(rs.getString("description"));
	    		movie.setYear(rs.getInt("movie_year"));  
	    		movie.setLength(rs.getInt("movie_length")); 
	    		movie.setDirector(rs.getString("director")); 
	    		movie.setMainActor(rs.getString("main_actor")); 
	    		movie.setGenre(rs.getString("genre")); 
	    		movie.setMpaaRating(rs.getString("mpaa_rating")); 
	    		movie.setImageName(rs.getString("image_name"));
	    		movie.setComingSoon(rs.getBoolean("is_coming_soon"));
	    		movie.setMovie_deleted(rs.getBoolean("movie_deleted"));
	    		
	    		listOfComingSoonMovies.add(movie);
    			}  
    		
    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt,connection);
    		} 
    return listOfComingSoonMovies;
    }  

    /**
     * Gets the movie from the movies table in the database..
     *
     * @param id the id
     * @return the movie
     */
    @Override
    public Movie getMovie(int id) {
    	connection = dbConnectionController.getConnection();
    	Statement stmt = null;
    	ResultSet rs = null;
        try {
        	 stmt = connection.createStatement();
        	 rs = stmt.executeQuery("SELECT * FROM movies WHERE movie_id=" + id);
        	 
        	 if(rs.next())
        	 {
        		Movie movie = new Movie();  
 	    		movie.setMovieId(rs.getInt("movie_id"));  
 	    		movie.setName(rs.getString("movie_name"));  
 	    		movie.setDescription(rs.getString("description"));
 	    		movie.setYear(rs.getInt("movie_year"));  
 	    		movie.setLength(rs.getInt("movie_length")); 
 	    		movie.setDirector(rs.getString("director")); 
 	    		movie.setMainActor(rs.getString("main_actor")); 
 	    		movie.setGenre(rs.getString("genre")); 
 	    		movie.setMpaaRating(rs.getString("mpaa_rating"));
 	    		movie.setImageName(rs.getString("image_name"));
 	    		movie.setMovie_deleted(rs.getBoolean("movie_deleted"));
	             
	            return movie;
        	 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(rs, stmt,connection);
    	}  
        return null;
    }
    

    
    /**
     * Insert a movie from the movies table in the database..
     *
     * @param movie the movie
     * @return true, if successful
     */
    @Override
	public boolean insertMovie(Movie movie) {
    	connection = dbConnectionController.getConnection(); 
    	PreparedStatement ps=null;
        try {
        	 ps = connection.prepareStatement("INSERT INTO public.movies (movie_name, description, movie_year, movie_length, director, main_actor, genre, mpaa_rating, is_coming_soon, image_name, movie_deleted) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
             ps.setString(1, movie.getName());
             ps.setString(2, movie.getDescription());
             ps.setInt(3, movie.getYear());
             ps.setInt(4, movie.getLength());
             ps.setString(5, movie.getDirector());
             ps.setString(6, movie.getMainActor());
             ps.setString(7, movie.getGenre());
             ps.setString(8, movie.getMpaaRating());
             ps.setBoolean(9, movie.isComingSoon());
             ps.setString(10, movie.getImageName());
             ps.setBoolean(11, movie.isMovie_deleted());

             int i = ps.executeUpdate();
            
          if(i == ONE_AFFECTED_OR_MATCHED_ROW) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(ps,connection);
    	}  
        return false;
    }
	
    
    /**
     * Update movie from the movies table in the database..
     *
     * @param movie the movie
     * @return true, if successful
     */
    @Override
	public boolean updateMovie(Movie movie) {
    	connection = dbConnectionController.getConnection();
    	PreparedStatement ps=null;
        try {
        	 ps = connection.prepareStatement("UPDATE movies SET movie_name=?, description=?, movie_year=?, movie_length=?, director=?, main_actor=?, genre=?, mpaa_rating=?, is_coming_soon=?, image_name=?, movie_deleted=? WHERE movie_id=?");
             ps.setString(1, movie.getName());
             ps.setString(2, movie.getDescription());
             ps.setInt(3, movie.getYear());
             ps.setInt(4, movie.getLength());
             ps.setString(5, movie.getDirector());
             ps.setString(6, movie.getMainActor());
             ps.setString(7, movie.getGenre());
             ps.setString(8, movie.getMpaaRating());
             ps.setBoolean(9, movie.isComingSoon());
             ps.setString(10, movie.getImageName());
             ps.setBoolean(11, movie.isMovie_deleted());
             ps.setInt(12, movie.getMovieId());

             int i = ps.executeUpdate();
            
          if(i == ONE_AFFECTED_OR_MATCHED_ROW) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(ps,connection);
    	}  
        return false;
    }
	
	
    /**
     * Delete movie from the movies table in the database..
     *
     * @param id the id
     * @return true, if successful
     */
    @Override
    public boolean deleteMovie(int id) {
	connection = dbConnectionController.getConnection();  
	Statement stmt = null;
    try {
        stmt = connection.createStatement();
        int i = stmt.executeUpdate("DELETE FROM movies WHERE movie_id=" + id);
      if(i == ONE_AFFECTED_OR_MATCHED_ROW) {
    return true;
      }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }finally {
    	dbConnectionController.closeConnection(stmt,connection);
	}  
    return false;
    }
    
    
    /**
     * Gets the movies map which contains the movie Id and the Movie object.
     *
     * @return the movies map
     */
    public Map<Integer, Movie> getReleasedMoviesMap() {
		return releasedMoviesMap;
	}
    
    
}
