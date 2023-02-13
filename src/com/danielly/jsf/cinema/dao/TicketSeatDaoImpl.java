package com.danielly.jsf.cinema.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.danielly.jsf.cinema.dao.interfaces.TicketSeatDao;
import com.danielly.jsf.cinema.model.TicketSeat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * The class TicketSeatDaoImpl contains all the methods implementation to query the tickets_seats table.
 * It contains the basic CRUD methods and additional methods that were needed.
 * This class implements the TicketSeatDao interface
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */


@ApplicationScoped
public class TicketSeatDaoImpl implements TicketSeatDao {

	//Attributes
	/** The Database Connection Controller. */
	@Inject
	private DbConnectionController dbConnectionController;
	
	/** The connection to the database. */
	private Connection connection; 
    
    /** The list of all tickets seats. */
    private List<TicketSeat> allTicketsSeats;
    
    /** The Constant ONE_AFFECTED_OR_MATCHED_ROW is used to check if there is 1 affected or matched row.*/
    private final static int ONE_AFFECTED_OR_MATCHED_ROW = 1;
    

    //Empty Constructor
    /**
     * Instantiates a new TicketSeatDaoImpl.
     */
    public TicketSeatDaoImpl() { 
    }
    
    
    //Database Queries Methods
    /**
     * Gets the all of the seat tickets from the tickets_seats table in the database.
     *
     * @return the all seat tickets
     */
    @Override
    public List<TicketSeat> getAllSeatTickets() {

    	Statement stmt = null;
    	ResultSet rs = null;
    	allTicketsSeats = new ArrayList<TicketSeat>();
    	
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("select * from tickets_seats");    
    		while(rs.next())
    			{  
	    		TicketSeat ticketSeat = new TicketSeat();  
	    		ticketSeat.setTicketId(rs.getInt("ticket_id"));  
	    		ticketSeat.setSeatId(rs.getInt("seat_id"));  

	    		allTicketsSeats.add(ticketSeat);

    			}  

    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt, connection);
    		} 
    return allTicketsSeats;
    }  

    
    /**
     * Gets the ticket seat by ticket id from the tickets_seats table in the database.
     *
     * @param ticketId the ticket id
     * @return the ticket seat by ticket id
     */
    @Override
    public TicketSeat getTicketSeatByTicketId(int ticketId) {
    	connection = dbConnectionController.getConnection();
    	Statement stmt = null;
    	ResultSet rs = null;
        try {
        	 stmt = connection.createStatement();
        	 rs = stmt.executeQuery("SELECT * FROM tickets_seats WHERE ticket_id=" + ticketId);
        	 
        	 if(rs.next())
        	 {
        		TicketSeat ticketSeat = new TicketSeat();  
    	    	ticketSeat.setTicketId(rs.getInt("ticket_id"));  
    	    	ticketSeat.setSeatId(rs.getInt("seat_id"));  
	             
	         return ticketSeat;
        	 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(rs, stmt, connection);
    	}  
        return null;
    }
    
    /**
     * Insert a ticket seat to the tickets_seats table in the database.
     *
     * @param ticketId the ticket id
     * @param seatId the seat id
     * @return true, if successful
     */
    @Override
	public boolean insertTicketSeat(int ticketId, int seatId) {
    	connection = dbConnectionController.getConnection(); 
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("INSERT INTO tickets_seats (ticket_id, seat_id) VALUES (?, ?)");
             ps.setInt(1, ticketId);
             ps.setInt(2, seatId);
           
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
     * Delete ticket seat from the tickets_seats table in the database.
     *
     * @param ticketId the ticket id
     * @return true, if successful
     */
    @Override
    public boolean deleteTicketSeat(int ticketId) {
	connection = dbConnectionController.getConnection();  
	Statement stmt = null;
    try {
        stmt = connection.createStatement();
        int i = stmt.executeUpdate("DELETE FROM TicketSeat WHERE ticket_id=" + ticketId);
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
    
    
}
