package com.danielly.jsf.cinema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import com.danielly.jsf.cinema.dao.interfaces.SearchDao;
import com.danielly.jsf.cinema.model.Showtime;

/**
 * The class SearchDaoImpl contains all the methods implementation to query the database for the search component.
 * In addition, stores lists that will be used for selections by the user.
 * This class implements the SearchDao interface
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */

@ApplicationScoped
public class SearchDaoImpl implements SearchDao {
	
	//Attributes
	/** The Database Connection Controller. */
	@Inject
	private DbConnectionController dbConnectionController;
	
	/** The connection to the database. */
	private Connection connection;  
	
	/** The showtimes hours. */
	private List<String> showtimesHours;
	
	/** The showtimes movies. */
	private List<String> showtimesMovies;
	
	/** The filtered showtimes after the search. */
	private List<Showtime> filteredShowtimes;
	
	 /** The Constant HIGHER_IS_INDICATOR_FOR_ANY_DATE_INPUT is used to check if there any date input received from the GUI.*/
    private final static int HIGHER_IS_INDICATOR_FOR_ANY_DATE_INPUT = 2;
	
	 //Empty Constructor
    /**
     * Instantiates a new SearchDaoImpl.
     */
	public SearchDaoImpl() { 
	}
			
	
	//Database Queries Methods
	/**
	 * Find all showtimes hours by hour order.
	 *
	 * @return the String list of hours
	 */
	@Override
	public List<String> findAllShowtimesHours() {
		List<String> rawDuplicateHours = new ArrayList<String>();
    	Statement stmt = null;
    	ResultSet rs = null;
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("SELECT showtime_date_hour FROM showtimes where showtime_deleted=false and showtime_date_hour > now() order by showtime_date_hour asc;");    
    		while(rs.next())
    			{  
    			String time;
    			time=rs.getTimestamp("showtime_date_hour").toLocalDateTime().toLocalTime().toString();
    			rawDuplicateHours.add(time);
    			}  
		}catch(SQLException e){  
			System.out.println(e);  
		}finally {
			dbConnectionController.closeConnection(rs, stmt,connection);
				}
    	showtimesHours = rawDuplicateHours.stream().distinct().collect(Collectors.toList());   //remove duplicates
    	 return showtimesHours;
	}

	
	/**
	 * Find all the movies that have a showtime.
	 *
	 * @return the String list of movies that have a showtime
	 */
	@Override
	public List<String> findAllShowtimeMovies() {
		List<String> rawDuplicateMovies = new ArrayList<String>();
    	Statement stmt = null;
    	ResultSet rs = null;
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("SELECT movie_name FROM showtimes, movies where showtimes.movie_id = movies.movie_id and showtime_deleted=false and showtime_date_hour > now();");    
    		while(rs.next())
    			{  
    			String movieName;
    			movieName=rs.getString("movie_name");
    			rawDuplicateMovies.add(movieName);
    			}  
		}catch(SQLException e){  
			System.out.println(e);  
		}finally {
			dbConnectionController.closeConnection(rs, stmt,connection);
				}
    	showtimesMovies = rawDuplicateMovies.stream().distinct().collect(Collectors.toList());    //remove duplicates
    	 return showtimesMovies;
	}

	
	/**
	 * Search by filter.
	 * This method contains all of the combination of date, hour, and movie.
	 * @param date the date
	 * @param hour the hour
	 * @param movie the movie
	 * @return the filtered list
	 */
	@Override
	public List<Showtime> searchByfilter(String date, String hour, String movie) {
		
		filteredShowtimes = new ArrayList<Showtime>();
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{   
    		connection = dbConnectionController.getConnection();  
    		
    		//date+hour+movie
    		if ((date.length()>HIGHER_IS_INDICATOR_FOR_ANY_DATE_INPUT) && (hour!=null) && (movie!=null)) {
    			LocalDateTime localDateTime = convertStringDateAndTimeToLocalDateTime(date, hour);
	    		ps = connection.prepareStatement("SELECT showtime_id, showtime_date_hour, total_tickets, a.movie_id, price_id, hall_id FROM showtimes as a ,movies as b where showtime_date_hour=? AND a.movie_id =b.movie_id and b.movie_name =? and a.showtime_deleted=false and a.showtime_date_hour > now() and b.movie_deleted=false");
	    		ps.setTimestamp(1, Timestamp.valueOf(localDateTime));
	    		ps.setString(2, movie); 
    		}   		
    		
    		else if (date.length()>HIGHER_IS_INDICATOR_FOR_ANY_DATE_INPUT) {
    			LocalDate localDate = convertStringToDate(date);
    			
    			//date+hour  - creating LocalDateTime from LocalTime and LocalDate 
    			if ((hour!=null) && (movie==null)) {
        			LocalTime localTime = convertStringToTime(hour);
        			LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
	        		ps = connection.prepareStatement("SELECT showtime_id, showtime_date_hour, total_tickets, movie_id, price_id, hall_id FROM showtimes where showtime_date_hour=? and showtime_deleted=false and showtime_date_hour > now()");
	        		ps.setTimestamp(1, Timestamp.valueOf(localDateTime));
    			}			
    			//date+movie
	        	else if ((hour==null) && (movie!=null)) {
	        		ps = connection.prepareStatement("SELECT showtime_id, showtime_date_hour, total_tickets, a.movie_id, price_id, hall_id FROM showtimes as a ,movies as b  where a.showtime_date_hour::date = ? AND a.movie_id =b.movie_id and b.movie_name =? and a.showtime_deleted=false and a.showtime_date_hour > now() and b.movie_deleted=false");
	        		ps.setDate(1, java.sql.Date.valueOf(localDate));
	        		ps.setString(2, movie); 
	        	}
    			//only date
	        	else {
	        		ps = connection.prepareStatement("SELECT showtime_id, showtime_date_hour, total_tickets, movie_id, price_id, hall_id FROM showtimes where showtime_date_hour::date = ? and showtime_deleted=false and showtime_date_hour > now()");     		
	        		ps.setDate(1, java.sql.Date.valueOf(localDate));
	        	}
    		}
    		
    		//hour+movie
			else if (hour!=null) {
				LocalTime localTime = convertStringToTime(hour);
    			if ((date.length()<HIGHER_IS_INDICATOR_FOR_ANY_DATE_INPUT) && (movie!=null)) {
    				ps = connection.prepareStatement("SELECT showtime_id, showtime_date_hour, total_tickets, a.movie_id, price_id, hall_id FROM showtimes as a ,movies as b  where a.showtime_date_hour::time = ? AND a.movie_id =b.movie_id and b.movie_name =? and a.showtime_deleted=false and a.showtime_date_hour > now() and b.movie_deleted=false");
	        		ps.setTime(1, Time.valueOf(localTime));
	        		ps.setString(2, movie); 
    			}
    			 //hour
	        	else {
	        		ps = connection.prepareStatement("SELECT showtime_id, showtime_date_hour, total_tickets, movie_id, price_id, hall_id FROM showtimes where showtime_date_hour::time = ? and showtime_deleted=false and showtime_date_hour > now()");
	        		ps.setTime(1, Time.valueOf(localTime));
	        	}
	        }
    		
        	else if(movie!=null) {//movie
        		ps = connection.prepareStatement("SELECT showtime_id, showtime_date_hour, total_tickets, a.movie_id, price_id, hall_id FROM showtimes as a ,movies as b where a.movie_id =b.movie_id and b.movie_name =? and a.showtime_deleted=false and a.showtime_date_hour > now() and b.movie_deleted=false");
    			ps.setString(1, movie); 
        	}    		
        	else
        	{
        		ps = connection.prepareStatement("SELECT showtime_id, showtime_date_hour, total_tickets, movie_id, price_id, hall_id, available_tickets, showtime_deleted FROM public.showtimes where showtime_deleted=false and showtime_date_hour > now() order by showtime_date_hour asc;");   ;
        	}
    		
    		rs=ps.executeQuery();    
    		while(rs.next())
    			{     			
    			filteredShowtimes.add(createAndSetShowtime(rs));
    			}  
		}catch(SQLException e){  
			System.out.println(e);  
		}finally {
			dbConnectionController.closeConnection(rs, ps ,connection);
				}
    	 return filteredShowtimes;
	}
	
	
	//Class Methods
	
	/**
	 * Create the showtime object and set the attributes.
	 *
	 * @param rs the rs
	 * @return the showtime
	 */
	private Showtime createAndSetShowtime(ResultSet rs) {
		Showtime showtime = new Showtime();  
		try {
			showtime.setShowtimeId(rs.getInt("showtime_id"));	
			showtime.setShowtimeDateHour(rs.getTimestamp("showtime_date_hour").toLocalDateTime());  
			showtime.setTotalTickets(rs.getInt("total_tickets"));
			showtime.setMovieId(rs.getInt("movie_id"));  
			showtime.setPriceId(rs.getInt("price_id")); 
			showtime.setHallId(rs.getInt("hall_id")); 
			showtime.setShowtimeHour(rs.getTimestamp("showtime_date_hour").toLocalDateTime().toLocalTime().toString());
			showtime.setShowtimeDate(convertLocalDateToString(rs.getTimestamp("showtime_date_hour").toLocalDateTime().toLocalDate())); 
		} catch (SQLException e) {
			
			e.printStackTrace();
		}  
		return showtime;
	}
	
	
	
	//Getters	
	/**
	 * Gets the selection hours for the GUI.
	 * It's being used in the panel-top-default.xhtml
	 * @return the selection hours
	 */
	@Override
	   public List<SelectItem> getSelectionHours(){
		   List<SelectItem> selectItemsOneDateHour = new ArrayList<SelectItem>();
		   List<String> listHoursStr =  this.findAllShowtimesHours();
			  
		  for (String hourStr:listHoursStr) {
			  SelectItem selectItem = new SelectItem(hourStr);
			  selectItemsOneDateHour.add(selectItem);
		  }
		   return selectItemsOneDateHour;
	   }	
	
	/**
	 * Gets the selection movies for the GUI.
	 * It's being used in the panel-top-default.xhtml
	 * @return the selection movies
	 */
	@Override
	   public List<SelectItem> getSelectionMovies(){
		   List<SelectItem> selectItemsOneDateMovie = new ArrayList<SelectItem>();
		   List<String> listMoviesStr =  this.findAllShowtimeMovies();			
			  
			  for (String movieStr:listMoviesStr) {
				  SelectItem selectItem = new SelectItem(movieStr);
				  selectItemsOneDateMovie.add(selectItem);
			  }
		   return selectItemsOneDateMovie;		   
	   }
	
	/**
	 * Gets the filtered showtimes.
	 * It's being used in the panel-top-default.xhtml
	 * @return the filtered showtimes
	 */
	@Override
	public List<Showtime> getFilteredShowtimes() {
		return filteredShowtimes;
	}
	
	
	//Converters
	/**
	 * Convert local date to string.
	 *
	 * @param localDate the local date
	 * @return the string
	 */
	@Override
    public String convertLocalDateToString(LocalDate localDate) {
    	DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String resultString = localDate.format(formatters);
    	
    	return resultString;
    }
    
	

	/**
	 * Convert string date and time to local date time.
	 *
	 * @param date the date
	 * @param hour the hour
	 * @return the local date time
	 */
	public LocalDateTime convertStringDateAndTimeToLocalDateTime(String date, String hour) {
		LocalDate localDate = convertStringToDate(date);
		LocalTime localTime = convertStringToTime(hour);
		LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
		return localDateTime;
	}
	
	
	/**
	 * Convert string to date.
	 *
	 * @param stringDate the date as a String
	 * @return the local date
	 */
	@Override
    public LocalDate convertStringToDate(String stringDate) {
    	DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
    	formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	LocalDate localDate = LocalDate.parse(stringDate, formatter);
    	
    	return localDate;
    }
    
	/**
	 * Convert string to time.
	 *
	 * @param stringTime the time as a String
	 * @return the local time
	 */
	@Override
    public LocalTime convertStringToTime(String stringTime) {
    	 LocalTime localTime = LocalTime.parse(stringTime,DateTimeFormatter.ofPattern("HH:mm"));
    	
    	return localTime;
    }
    
}
