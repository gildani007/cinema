package com.danielly.jsf.cinema.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.danielly.jsf.cinema.dao.interfaces.SeatDao;
import com.danielly.jsf.cinema.model.Seat;

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
 * The class SeatDaoImpl contains all the methods implementation to query the seats table.
 * It contains the basic CRUD methods and additional methods that were needed.
 * This class implements the SeatDao interface
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */


@ApplicationScoped
public class SeatDaoImpl implements SeatDao {

	//Attributes
	/** The Database Connection Controller. */
	@Inject
	private DbConnectionController dbConnectionController;
	
	/** The connection to the database. */
	private Connection connection;  
	
	/** The list of all seats. */
	private List<Seat> allSeats;
	
	/** The seats of a specific hall. */
	private List<Seat> seatsByHall;
    
    /** The seats map. */
    private Map<Integer, Seat> seatsMap;
    
    /** The Constant NO_AFFECTED_ROWS is used to check if there any affected rows .*/
    private final static int NO_AFFECTED_ROWS = 0;
    
    /** The Constant ONE_AFFECTED_OR_MATCHED_ROW is used to check if there is 1 affected or matched row.*/
    private final static int ONE_AFFECTED_OR_MATCHED_ROW = 1;
    
    
    //Empty Constructor
    /**
     * Instantiates a new SeatDaoImpl.
     */
    public SeatDaoImpl() { 
    }
    
    
    //Database Queries Methods
    /**
     * Gets the all seats from the seats table in the database.
     *
     * @return the all of the seats
     */
    @Override
    public List<Seat> getAllSeats() {

    	Statement stmt = null;
    	ResultSet rs= null;
    	allSeats = new ArrayList<>();
    	seatsMap = new HashMap<Integer,Seat>();
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("select * from seats");    
    		while(rs.next())
    			{  
	    		Seat seat = new Seat();  
	    		seat.setSeatId(rs.getInt("seat_id"));  
	    		seat.setRowNumber(rs.getInt("seat_row_number"));  
	    		seat.setSeatNumber(rs.getInt("seat_number"));
	    		seat.setHallId(rs.getInt("hall_id"));
	    		
	    		allSeats.add(seat);
	    		seatsMap.put(new Integer(seat.getSeatId()),seat);
    			}  
    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt, connection);
    		} 
    return allSeats;
    }  

       
	/**
	 * Gets the seats by hall id from the seats table in the database.
	 *
	 * @param hallId the hall id
	 * @return the seats by hall id
	 */
	@Override
	public List<Seat> getSeatsByHallId(int hallId) {
		Statement stmt = null;
		ResultSet rs= null;
		seatsByHall = new ArrayList<>();
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("SELECT * FROM seats WHERE hall_id=" + hallId);    
    		while(rs.next())
    			{  
	    		Seat seat = new Seat();  
	    		seat.setSeatId(rs.getInt("seat_id"));  
	    		seat.setRowNumber(rs.getInt("seat_row_number"));  
	    		seat.setSeatNumber(rs.getInt("seat_number")); 
	    		seat.setHallId(rs.getInt("hall_id"));
	    		
	    		seatsByHall.add(seat);
    			}  
    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt, connection);
    		} 
    return seatsByHall;
    }  
    	
	
    /**
     * Gets the seat from the seats table in the database.
     *
     * @param id the id
     * @return the seat
     */
    @Override
    public Seat getSeat(int id) {
    	connection = dbConnectionController.getConnection();
    	Statement stmt = null;
    	ResultSet rs = null;
        try {
        	 stmt = connection.createStatement();
        	 rs = stmt.executeQuery("SELECT * FROM seats WHERE seat_id=" + id);
        	 
        	 if(rs.next())
        	 {
        		Seat seat = new Seat();  
 	    		seat.setSeatId(rs.getInt("seat_id"));  
 	    		seat.setRowNumber(rs.getInt("seat_row_number"));  
 	    		seat.setSeatNumber(rs.getInt("seat_number"));
 	    		seat.setHallId(rs.getInt("hall_id"));
	             
	            return seat;
        	 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(rs, stmt, connection);
    	}  
        return null;
    }
    
   
    /**
     * Insert a seat to the seats table in the database.
     *
     * @param seat the seat
     * @return true, if successful
     */
    @Override
	public boolean insertSeat(Seat seat) {
    	connection = dbConnectionController.getConnection(); 
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("INSERT INTO public.seats (seat_row_number, seat_number, hall_id) VALUES(?, ?, ?)");
             ps.setInt(1, seat.getRowNumber());
             ps.setInt(2, seat.getSeatNumber());
             ps.setInt(3, seat.getHallId());
           
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
     * Update a seat in the seats table in the database.
     *
     * @param seat the seat
     * @return true, if successful
     */
    @Override
	public boolean updateSeat(Seat seat) {
    	connection = dbConnectionController.getConnection();
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("UPDATE seats SET seat_row_number=?, seat_number=?, hall_id=? WHERE id=?");
             ps.setInt(1, seat.getRowNumber());
             ps.setInt(2, seat.getSeatNumber());
             ps.setInt(3, seat.getHallId());
             ps.setInt(4, seat.getSeatId());
             
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
     * Delete seat from the seats table in the database.
     *
     * @param id the id
     * @return true, if successful
     */
    @Override
    public boolean deleteSeat(int id) {
	connection = dbConnectionController.getConnection();  
	Statement stmt = null;
    try {
        stmt = connection.createStatement();
        int i = stmt.executeUpdate("DELETE FROM seats WHERE seat_id=" + id);
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
     * Delete all of the seats of a specific hall.
     *
     * @param hallId the hall id
     * @return true, if successful
     */
    @Override
    public boolean deleteAllSeatOfAHall(int hallId) {
	connection = dbConnectionController.getConnection();  
	Statement stmt = null;
    try {
        stmt = connection.createStatement();
        int i = stmt.executeUpdate("DELETE FROM seats WHERE hall_id=" + hallId);
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
    
    
	/**
	 * Gets the seats map which contains the seat Id and the Seat object.
	 * 
	 * @return the seats map
	 */
	@Override
	public Map<Integer, Seat> getSeatsMap() {
		return seatsMap;
	}

}
