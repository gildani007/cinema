package com.danielly.jsf.cinema.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.danielly.jsf.cinema.dao.interfaces.ShowtimeSeatsDao;
import com.danielly.jsf.cinema.model.ShowtimeSeat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * The class ShowtimeSeatsDaoImpl contains all the methods implementation to query the showtimes_seats table.
 * It contains the basic CRUD methods and additional methods that were needed.
 * This class implements the ShowtimeSeatsDao interface
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */

@ApplicationScoped
public class ShowtimeSeatsDaoImpl implements ShowtimeSeatsDao {

	//Attributes
	/** The Database Connection Controller. */
	@Inject
	private DbConnectionController dbConnectionController;
	
	/** The connection to the database. */
	private Connection connection;  
    
    /** The list of all showtime seats. */
    private List<ShowtimeSeat> allShowtimeSeats;
    
    /** The showtime seats by a specific showtime. */
    private List<ShowtimeSeat> showtimeSeatsByShowtime;
    
    /** The Constant NO_AFFECTED_ROWS is used to check if there any affected rows .*/
    private final static int NO_AFFECTED_ROWS = 0;
    
    /** The Constant ONE_AFFECTED_OR_MATCHED_ROW is used to check if there is 1 affected or matched row.*/
    private final static int ONE_AFFECTED_OR_MATCHED_ROW = 1;


    //Empty Constructor
    /**
     * Instantiates a new ShowtimeSeatsDaoImpl.
     */
    public ShowtimeSeatsDaoImpl() { 
    }
    
   
    //Database Queries Methods
    /**
     * Gets the all of the showtime seats from the showtimes_seats table in the database.
     *
     * @return the all showtime seats
     */
    @Override
    public List<ShowtimeSeat> getAllShowtimeSeats() {

    	Statement stmt = null;
    	ResultSet rs = null;
    	allShowtimeSeats = new ArrayList<>();
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("select * from showtimes_seat");    
    		while(rs.next())
    			{  
    			ShowtimeSeat showtimeSeat = new ShowtimeSeat();  
    			showtimeSeat.setSeatId(rs.getInt("seat_id"));  
    			showtimeSeat.setShowtimeId(rs.getInt("showtime_id"));  
    			showtimeSeat.setSeatTaken(rs.getBoolean("is_seat_taken"));
    			showtimeSeat.setSeatSelected(rs.getBoolean("is_seat_selected"));  
	    		    		
	    		allShowtimeSeats.add(showtimeSeat);

    			}  

    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt, connection);
    		} 
    return allShowtimeSeats;
    }  

    
    
    /**
     * Gets the showtime seat from the showtimes_seats table in the database.
     *
     * @param seatId the seat id
     * @param showtimeId the showtime id
     * @return the showtime seat
     */
    @Override
    public ShowtimeSeat getShowtimeSeat(int seatId, int showtimeId) {
    	connection = dbConnectionController.getConnection();
    	Statement stmt = null;
    	ResultSet rs = null;
        try {
        	 stmt = connection.createStatement();
        	 rs = stmt.executeQuery("SELECT * FROM showtimes_seats WHERE seat_id=" + seatId + "and showtime_id=" + showtimeId );
        	 
        	 if(rs.next())
        	 {
        		ShowtimeSeat showtimeSeat = new ShowtimeSeat();  
     			showtimeSeat.setSeatId(rs.getInt("seat_id"));  
     			showtimeSeat.setShowtimeId(rs.getInt("showtime_id"));  
     			showtimeSeat.setSeatTaken(rs.getBoolean("is_seat_taken"));
     			showtimeSeat.setSeatSelected(rs.getBoolean("is_seat_selected"));
	             
	            return showtimeSeat;
        	 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(rs, stmt, connection);
    	}  
        return null;
    }
    

    /**
     * Gets the showtime seats by showtime from the showtimes_seats table in the database.
     *
     * @param showtimeId the showtime id
     * @return the showtime seats by showtime
     */
    @Override
	public List<ShowtimeSeat> getShowtimeSeatsByShowtime(int showtimeId) {
		Statement stmt = null;
		ResultSet rs = null;
		showtimeSeatsByShowtime = new ArrayList<>();
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("SELECT * FROM showtimes_seats WHERE showtime_id=" + showtimeId +"ORDER BY seat_id asc");    
    		while(rs.next())
    			{  
    			ShowtimeSeat showtimeSeat = new ShowtimeSeat();  
    			showtimeSeat.setSeatId(rs.getInt("seat_id"));  
    			showtimeSeat.setShowtimeId(rs.getInt("showtime_id"));  
    			showtimeSeat.setSeatTaken(rs.getBoolean("is_seat_taken"));
    			showtimeSeat.setSeatSelected(rs.getBoolean("is_seat_selected"));  
    		
	    		showtimeSeatsByShowtime.add(showtimeSeat);
    			}  
    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt, connection);
    		} 
    return showtimeSeatsByShowtime;
    }  
    
    
    
    /**
     * Insert a showtime seat to the showtimes_seats table in the database.
     *
     * @param showtimeSeat the showtime seat
     * @return true, if successful
     */
    @Override
	public boolean insertShowtimeSeat(ShowtimeSeat showtimeSeat) {
    	connection = dbConnectionController.getConnection(); 
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("INSERT INTO showtimes_seats VALUES ( ?, ?, ?, ?)");
             ps.setInt(1, showtimeSeat.getSeatId());
             ps.setInt(2, showtimeSeat.getShowtimeId());
             ps.setBoolean(3, showtimeSeat.getIsSeatTaken());
             ps.setBoolean(4, showtimeSeat.getIsSeatSelected());
           
             int i = ps.executeUpdate();
            
          if(i == ONE_AFFECTED_OR_MATCHED_ROW) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(ps, connection);
    	}  
        return false;
    }
	
    /**
     * Insert a showtime seat to the showtimes_seats table in the database.
     *
     * @param showtimeId the showtime id
     * @return true, if successful
     */
    @Override
   	public boolean insertShowtimeSeats(int showtimeId) {
       	connection = dbConnectionController.getConnection(); 
       	PreparedStatement ps=null;
           try {
           	 ps = connection.prepareStatement("INSERT INTO showtimes_seats (select seat_id , showtime_id from seats, showtimes where showtime_id=? and showtimes.hall_id =seats.hall_id)");
                ps.setInt(1, showtimeId);
 
                int i = ps.executeUpdate();
               
             if(i>NO_AFFECTED_ROWS) {
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
     * Update showtime seat in the showtimes_seats table in the database.
     *
     * @param showtimeSeat the showtime seat
     * @return true, if successful
     */
    @Override
	public boolean updateShowtimeSeat(ShowtimeSeat showtimeSeat) {
    	connection = dbConnectionController.getConnection();
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("UPDATE showtimes_seats SET is_seat_taken=?, is_seat_selected=? WHERE seat_id=? AND showtime_id=?");
             ps.setBoolean(1, showtimeSeat.getIsSeatTaken());
             ps.setBoolean(2, showtimeSeat.getIsSeatSelected());
             ps.setInt(3, showtimeSeat.getSeatId());
             ps.setInt(4, showtimeSeat.getShowtimeId());
             
             int i = ps.executeUpdate();
            
          if(i == ONE_AFFECTED_OR_MATCHED_ROW) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(ps, connection);
    	}  
        return false;
    }
	
    
    /**
     * Update showtime seat to taken.
     *
     * @param showtimeId the showtime id
     * @param seatId the seat id
     * @return true, if successful
     */
    @Override
	public boolean updateShowtimeSeatToTaken(int showtimeId, int seatId) {
    	connection = dbConnectionController.getConnection();
    	PreparedStatement ps = null;
        try {
        		ps = connection.prepareStatement("UPDATE showtimes_seats SET is_seat_taken = true WHERE showtime_id = "+ showtimeId +" and seat_id = " + seatId);
        	
             int i = ps.executeUpdate();
        	      	
          if(i == ONE_AFFECTED_OR_MATCHED_ROW) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(ps, connection);
    	}  
        return false;
    }
    
    /**
     * Update showtime seat to not taken.
     *
     * @param showtimeId the showtime id
     * @param seatId the seat id
     * @return true, if successful
     */
    @Override
	public boolean updateShowtimeSeatToNotTaken(int showtimeId, int seatId) {
    	connection = dbConnectionController.getConnection();
    	PreparedStatement ps = null;
        try {
        		ps = connection.prepareStatement("UPDATE showtimes_seats SET is_seat_taken = false WHERE showtime_id = "+ showtimeId +" and seat_id = " + seatId);
        	
             int i = ps.executeUpdate();
        	      	
          if(i == ONE_AFFECTED_OR_MATCHED_ROW) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(ps, connection);
    	}  
        return false;
    }
	
    /**
     * Delete a showtime seat from the showtimes_seats table in the database.
     *
     * @param seatId the seat id
     * @param showtimeId the showtime id
     * @return true, if successful
     */
    @Override
    public boolean deleteShowtimeSeat(int seatId, int showtimeId) {
	connection = dbConnectionController.getConnection();  
	Statement stmt = null;
    try {
        stmt = connection.createStatement();
        int i = stmt.executeUpdate("DELETE FROM showtimes_seats WHERE seat_id=" + seatId + "and showtime_id=" + showtimeId);
        
      if(i == ONE_AFFECTED_OR_MATCHED_ROW) {
    return true;
      }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }finally {
    	dbConnectionController.closeConnection(stmt, connection);
	}  
    return false;
    }
    
    /**
     * Delete showtime seats for specific showtime.
     *
     * @param showtimeId the showtime id
     * @return true, if successful
     */
    @Override
    public boolean deleteShowtimeSeatsForShowtime(int showtimeId) {
	connection = dbConnectionController.getConnection();  
	Statement stmt = null;
    try {
        stmt = connection.createStatement();
        int i = stmt.executeUpdate("DELETE FROM showtimes_seats WHERE showtime_id=" + showtimeId);
        
      if(i > NO_AFFECTED_ROWS) {
    return true;
      }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }finally {
    	dbConnectionController.closeConnection(stmt, connection);
	}  
    return false;
    }
     

    //Getters And Setters
	/**
	 * Gets the seats by showtime.
	 *
	 * @return the seats by showtime
	 */
	public List<ShowtimeSeat> getSeatsByShowtime() {
		return showtimeSeatsByShowtime;
	}

	/**
	 * Sets the seats by showtime.
	 *
	 * @param seatsByShowtime the new seats by showtime
	 */
	public void setSeatsByShowtime(List<ShowtimeSeat> seatsByShowtime) {
		this.showtimeSeatsByShowtime = seatsByShowtime;
	}
}
