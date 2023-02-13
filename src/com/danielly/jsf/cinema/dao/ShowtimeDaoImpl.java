package com.danielly.jsf.cinema.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.danielly.jsf.cinema.dao.interfaces.ShowtimeDao;
import com.danielly.jsf.cinema.model.Showtime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class ShowtimeDaoImpl contains all the methods implementation to query the showtimes table.
 * It contains the basic CRUD methods and additional methods that were needed.
 * This class implements the ShowtimeDao interface
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */


@ApplicationScoped
public class ShowtimeDaoImpl implements ShowtimeDao {

	//Attributes
	/** The Database Connection Controller. */
	@Inject
	private DbConnectionController dbConnectionController;
	
	/** The connection to the database. */
	private Connection connection;  
	
	/** The list of all showtimes. */
	private List<Showtime> listOfShowtimes;
    
    /** The showtimes map. */
    private Map<Integer,Showtime> showtimesMap;    
    
    /** The Constant NO_AFFECTED_ROWS is used to check if there any affected rows .*/
    private final static int NO_AFFECTED_ROWS = 0;
    
    /** The Constant NO_AFFECTED_ROWS is used to check if there any affected rows .*/
    private final static int HIGHER_IS_POSITIVE_AMOUNT = 0;
    
    /** The Constant ONE_AFFECTED_OR_MATCHED_ROW is used to check if there is 1 affected or matched row.*/
    private final static int ONE_AFFECTED_OR_MATCHED_ROW = 1;
    
    /** The Constant FIRST_COLUMN_TO_RETURN_THE_ID is used to define the column the Id can be found in the database. */
    private final static int FIRST_COLUMN_TO_RETURN_THE_ID = 1;
    
    
    //Empty Constructor
	/**
	 * Instantiates a new ShowtimeDaoImpl.
	 */
	public ShowtimeDaoImpl() { 
    }
    
     
	
	//Database Queries Methods
    /**
     * Gets the list of showtimes from the showtimes table in the database.
     *
     * @return the list of showtimes
     */
    @Override
    public List<Showtime> getListOfShowtimes() {
    	listOfShowtimes = new ArrayList<>();
    	showtimesMap = new HashMap<Integer,Showtime>();
    	Statement stmt = null;
    	ResultSet rs = null;
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt = connection.createStatement();    
    		rs = stmt.executeQuery("SELECT showtime_id, showtime_date_hour, total_tickets, movie_id, price_id, hall_id, available_tickets, showtime_deleted FROM public.showtimes order by showtime_date_hour asc;");    
    		while(rs.next())
    			{  
    			Showtime showtime = new Showtime();  
    			showtime.setShowtimeId(rs.getInt("showtime_id"));  
    			showtime.setShowtimeDateHour(rs.getTimestamp("showtime_date_hour").toLocalDateTime());  
    			showtime.setTotalTickets(rs.getInt("total_tickets"));
    			showtime.setAvailableTickets(rs.getInt("available_tickets"));
    			showtime.setMovieId(rs.getInt("movie_id"));  
    			showtime.setPriceId(rs.getInt("price_id")); 
    			showtime.setHallId(rs.getInt("hall_id")); 
    			showtime.setShowtime_deleted(rs.getBoolean("showtime_deleted"));
    			showtime.setShowtimeHour(rs.getTimestamp("showtime_date_hour").toLocalDateTime().toLocalTime().toString());
    			showtime.setShowtimeDate(convertLocalDateToString(rs.getTimestamp("showtime_date_hour").toLocalDateTime().toLocalDate())); 
	    		
	    		listOfShowtimes.add(showtime);
	    		showtimesMap.put(new Integer(showtime.getShowtimeId()),showtime);
	    		
    			}  
    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt,connection);
    				}
    		
    return listOfShowtimes;
    }  
    
    /**
     * Gets the list of showtimes from the showtimes table in the database.
     *
     * @return the list of showtimes
     */
    @Override
    public List<Showtime> getListOfCurrentShowtimes() {
    	listOfShowtimes = new ArrayList<>();
    	showtimesMap = new HashMap<Integer,Showtime>();
    	Statement stmt = null;
    	ResultSet rs = null;
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt = connection.createStatement();    
    		rs = stmt.executeQuery("SELECT showtime_id, showtime_date_hour, total_tickets, movie_id, price_id, hall_id, available_tickets, showtime_deleted FROM public.showtimes where showtime_deleted=false and showtime_date_hour > now() order by showtime_date_hour asc;");    
    		while(rs.next())
    			{  
    			Showtime showtime = new Showtime();  
    			showtime.setShowtimeId(rs.getInt("showtime_id"));  
    			showtime.setShowtimeDateHour(rs.getTimestamp("showtime_date_hour").toLocalDateTime());  
    			showtime.setTotalTickets(rs.getInt("total_tickets"));
    			showtime.setAvailableTickets(rs.getInt("available_tickets"));
    			showtime.setMovieId(rs.getInt("movie_id"));  
    			showtime.setPriceId(rs.getInt("price_id")); 
    			showtime.setHallId(rs.getInt("hall_id")); 
    			showtime.setShowtime_deleted(rs.getBoolean("showtime_deleted"));
    			showtime.setShowtimeHour(rs.getTimestamp("showtime_date_hour").toLocalDateTime().toLocalTime().toString());
    			showtime.setShowtimeDate(convertLocalDateToString(rs.getTimestamp("showtime_date_hour").toLocalDateTime().toLocalDate())); 
	    		
	    		listOfShowtimes.add(showtime);
	    		showtimesMap.put(new Integer(showtime.getShowtimeId()),showtime);
	    		
    			}  
    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt,connection);
    				}
    		
    return listOfShowtimes;
    }  

    /**
     * Gets the showtime from the showtimes table in the database.
     *
     * @param id the id
     * @return the showtime
     */
    @Override
    public Showtime getShowtime(int id) {
    	connection = dbConnectionController.getConnection();
    	Statement stmt = null;
    	ResultSet rs = null;
        try {
        	 stmt = connection.createStatement();
        	 rs = stmt.executeQuery("SELECT * FROM showtimes WHERE showtime_id=" + id);
        	 
        	 if(rs.next())
        	 {
        		Showtime showtime = new Showtime();  
     			showtime.setShowtimeId(rs.getInt("showtime_id"));  
     			showtime.setShowtimeDateHour(rs.getTimestamp("showtime_date_hour").toLocalDateTime());  
     			showtime.setTotalTickets(rs.getInt("total_tickets"));
     			showtime.setMovieId(rs.getInt("movie_id"));  
     			showtime.setPriceId(rs.getInt("price_id")); 
     			showtime.setHallId(rs.getInt("hall_id"));
     			showtime.setShowtime_deleted(rs.getBoolean("showtime_deleted"));
     			showtime.setAvailableTickets(rs.getInt("available_tickets"));
     			showtime.setShowtimeHour(rs.getTimestamp("showtime_date_hour").toLocalDateTime().toLocalTime().toString());
    			showtime.setShowtimeDate(convertLocalDateToString(rs.getTimestamp("showtime_date_hour").toLocalDateTime().toLocalDate())); 
    			
	            return showtime;
        	 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(rs, stmt,connection);
    	}  
        return null;
    }
    
  
    
    /**
     * Insert a showtime to the showtimes table in the database.
     *
     * @param showtime the showtime
     * @return true, if successful
     */
    @Override
	public boolean insertShowtime(Showtime showtime) {
    	connection = dbConnectionController.getConnection(); 
    	PreparedStatement ps=null;
        try {
        	 ps = connection.prepareStatement("INSERT INTO showtimes (showtime_date_hour, total_tickets, movie_id, price_id, hall_id, available_tickets, showtime_deleted) VALUES(?, ?, ?, ?, ?, ?, ?);");
             ps.setTimestamp(1, Timestamp.valueOf(showtime.getShowtimeDateHour()));
             ps.setInt(2, showtime.getTotalTickets());
             ps.setInt(3, showtime.getMovieId());
             ps.setInt(4, showtime.getPriceId());
             ps.setInt(5, showtime.getHallId());
             ps.setInt(6, showtime.getAvailableTickets());
             ps.setBoolean(7, showtime.isShowtime_deleted());

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
     * Insert a showtime and get id from the showtimes table in the database.
     *
     * @param showtime the showtime
     * @return the int
     */
    @Override
	public int insertShowtimeAndReturnId(Showtime showtime) {
    	connection = dbConnectionController.getConnection(); 
    	String insertSqlShowtime="INSERT INTO showtimes (showtime_date_hour, total_tickets, movie_id, price_id, hall_id, available_tickets, showtime_deleted) VALUES(?, ?, ?, ?, ?, ?, ?);";
    	PreparedStatement ps=null;
    	ResultSet rsKey = null;
    	int resultShowtimeId =0;
        try {
        	 ps = connection.prepareStatement(insertSqlShowtime,Statement.RETURN_GENERATED_KEYS);
             ps.setTimestamp(1, Timestamp.valueOf(showtime.getShowtimeDateHour()));
             ps.setInt(2, showtime.getTotalTickets());
             ps.setInt(3, showtime.getMovieId());
             ps.setInt(4, showtime.getPriceId());
             ps.setInt(5, showtime.getHallId());
             ps.setInt(6, showtime.getAvailableTickets());
             ps.setBoolean(7, showtime.isShowtime_deleted());

             int i = ps.executeUpdate();
            
             if(i!=NO_AFFECTED_ROWS) {
           	  rsKey = ps.getGeneratedKeys();
           	  if(rsKey.next()) 
           	  {
           		  resultShowtimeId = rsKey.getInt(FIRST_COLUMN_TO_RETURN_THE_ID);
           		  
           		    return resultShowtimeId;
           	  }
             }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(rsKey, ps,connection);
    	}  
        return resultShowtimeId;
    }
    

    
    
    /**
     * Update showtime in the showtimes table in the database.
     *
     * @param showtime the showtime
     * @return true, if successful
     */
    @Override
	public boolean updateShowtime(Showtime showtime) {
    	connection = dbConnectionController.getConnection();
    	PreparedStatement ps=null;
        try {
        	 ps = connection.prepareStatement("UPDATE public.showtimes SET showtime_date_hour=?, total_tickets=?, movie_id=?, price_id=?, hall_id=?, available_tickets=?, showtime_deleted=? WHERE showtime_id=?");
        	   ps.setTimestamp(1, Timestamp.valueOf(showtime.getShowtimeDateHour()));
               ps.setInt(2, showtime.getTotalTickets());
               ps.setInt(3, showtime.getMovieId());
               ps.setInt(4, showtime.getPriceId());
               ps.setInt(5, showtime.getHallId());
               ps.setInt(6, showtime.getAvailableTickets());
               ps.setBoolean(7, showtime.isShowtime_deleted());
               ps.setInt(8, showtime.getShowtimeId());
               
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
     * Change the amount of available tickets in a showtime.
     *
     * @param showtimeId the showtime id
     * @param amount the amount
     * @return true, if successful
     */
    @Override
  	public boolean changeAmountShowtimeAvailableTickets(int showtimeId, int amount) {
      	connection = dbConnectionController.getConnection();
      	PreparedStatement ps=null;
          try {
        	  if (amount>HIGHER_IS_POSITIVE_AMOUNT) {
        		  ps = connection.prepareStatement("UPDATE showtimes SET available_tickets = available_tickets + "+amount+" WHERE showtime_id = "+showtimeId);
        	  }
        	  else {
        		  ps = connection.prepareStatement("UPDATE showtimes SET available_tickets = available_tickets "+amount+" WHERE showtime_id = "+showtimeId);
        	  }       	  
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
     * Decrement by 1 the amount of available tickets in a showtime.
     *
     * @param showtimeId the showtime id
     * @return true, if successful
     */
    @Override
  	public boolean decrementShowtimeAvaialableTickets(int showtimeId) {
      	connection = dbConnectionController.getConnection();
      	PreparedStatement ps=null;
          try {
        	  ps = connection.prepareStatement("UPDATE showtimes SET available_tickets = available_tickets - 1 WHERE showtime_id = "+showtimeId);
        	  
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
     * Increment by 1 the amount of available tickets in a showtime.
     *
     * @param showtimeId the showtime id
     * @return true, if successful
     */
    @Override
  	public boolean incrementShowtimeAvaialableTickets(int showtimeId) {
      	connection = dbConnectionController.getConnection();
      	PreparedStatement ps=null;
          try {
        	  	ps = connection.prepareStatement("UPDATE showtimes SET available_tickets = available_tickets + 1 WHERE showtime_id = "+showtimeId);
        	  
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
     * Delete showtime from the showtimes table in the database..
     *
     * @param showtimeId the showtime id
     * @return true, if successful
     */
    @Override
    public boolean deleteShowtime(int showtimeId) {
	connection = dbConnectionController.getConnection();  
	Statement stmt = null;
    try {
        stmt = connection.createStatement();
        int i = stmt.executeUpdate("DELETE FROM showtimes WHERE showtime_id=" + showtimeId);
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
    
    //Getter
    /**
     * Gets the showtimes map which contains the showtime Id and the Showtime object.
     *
     * @return the showtimes map
     */
    public Map<Integer, Showtime> getShowtimesMap() {
		return showtimesMap;
	}
    
    
    //Converter
    /**
     * Convert local date to string.
     *
     * @param localDate the local date
     * @return the string
     */
    public String convertLocalDateToString(LocalDate localDate) {
    	DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String resultString = localDate.format(formatters);
    	
    	return resultString;
    }
    
}
