package com.danielly.jsf.cinema.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.danielly.jsf.cinema.dao.interfaces.TicketDao;
import com.danielly.jsf.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * The class TicketDaoImpl contains all the methods implementation to query the tickets table.
 * It contains the basic CRUD methods and additional methods that were needed.
 * This class implements the TicketDao interface
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */


@ApplicationScoped
public class TicketDaoImpl implements TicketDao {

	//Attributes
	/** The Database Connection Controller. */
	@Inject
	private DbConnectionController dbConnectionController;
	
	/** The connection to the database. */
	private Connection connection;  
    
    /** The list of all tickets. */
    private List<Ticket> allTickets;  
    
    /** The Constant NO_AFFECTED_ROWS is used to check if there any affected rows .*/
    private final static int NO_AFFECTED_ROWS = 0;
    
    /** The Constant ONE_AFFECTED_OR_MATCHED_ROW is used to check if there is 1 affected or matched row.*/
    private final static int ONE_AFFECTED_OR_MATCHED_ROW = 1;
    
    /** The Constant FIRST_COLUMN_TO_RETURN_THE_ID is used to define the column the Id can be found in the database. */
    private final static int FIRST_COLUMN_TO_RETURN_THE_ID = 1;

    
    //Empty Constructor
    /**
     * Instantiates a new TicketDaoImpl.
     */
    public TicketDaoImpl() { 
    }
    
    
    
    //Database Queries Methods
    /**
     * Gets the all of the tickets from the tickets table in the database.
     *
     * @return the all tickets
     */
    @Override
    public List<Ticket> getAllTickets() {

    	Statement stmt = null;
    	ResultSet rs = null;
    	allTickets = new ArrayList<>();
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("select * from tickets");    
    		while(rs.next())
    			{  
	    		Ticket ticket = new Ticket();  
	    		ticket.setTicketId(rs.getInt("ticket_id"));  
	    		ticket.setIsSeatAllocated(rs.getBoolean("is_seat_allocated"));  
	    		ticket.setShowtimeId(rs.getInt("showtime_id"));
	    		ticket.setOrderId(rs.getInt("order_id")); 
	    		
	    		allTickets.add(ticket);

    			}  

    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt, connection);
    		} 
    return allTickets;
    }  

    /**
     * Gets the tickets by order from the tickets table in the database.
     *
     * @param orderId the order id
     * @return the tickets by order
     */
    @Override
    public List<Ticket> getTicketsByOrder(int orderId) {

    	Statement stmt = null;
    	ResultSet rs = null;
    	allTickets = new ArrayList<>();
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("select * from tickets where order_id=" + orderId);    
    		while(rs.next())
    			{  
	    		Ticket ticket = new Ticket();  
	    		ticket.setTicketId(rs.getInt("ticket_id"));  
	    		ticket.setIsSeatAllocated(rs.getBoolean("is_seat_allocated"));  
	    		ticket.setShowtimeId(rs.getInt("showtime_id"));
	    		ticket.setOrderId(rs.getInt("order_id")); 
	    		
	    		allTickets.add(ticket);

    			}  

    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt, connection);
    		} 
    return allTickets;
    }  
    
    /**
     * Gets the ticket from the tickets table in the database.
     *
     * @param id the id
     * @return the ticket
     */
    @Override
    public Ticket getTicket(int id) {
    	connection = dbConnectionController.getConnection();
    	Statement stmt = null;
    	ResultSet rs = null;
        try {
        	 stmt = connection.createStatement();
        	 rs = stmt.executeQuery("SELECT * FROM tickets WHERE ticket_id=" + id);
        	 
        	 if(rs.next())
        	 {
        		Ticket ticket = new Ticket();  
 	    		ticket.setTicketId(rs.getInt("ticket_id"));  
 	    		ticket.setIsSeatAllocated(rs.getBoolean("is_seat_allocated"));  
 	    		ticket.setShowtimeId(rs.getInt("showtime_id"));
 	    		ticket.setOrderId(rs.getInt("order_id")); 
	             
	            return ticket;
        	 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(rs, stmt, connection);
    	}  
        return null;
    }
    

    
    /**
     * Insert a ticket to the tickets table in the database.
     *
     * @param ticket the ticket
     * @return true, if successful
     */
    @Override
	public boolean insertTicket(Ticket ticket) {
    	connection = dbConnectionController.getConnection(); 
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("INSERT INTO tickets (is_seat_allocated, showtime_id, order_id) VALUES (?, ?, ?)");
             ps.setBoolean(1, ticket.getIsSeatAllocated());
             ps.setInt(2, ticket.getShowtimeId());
             ps.setInt(3, ticket.getOrderId());
           
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
     * Insert a ticket return the ticket id from the tickets table in the database.
     *
     * @param ticket the ticket
     * @return the int
     */
    @Override
   	public int insertTicketAndReturnId(Ticket ticket) {
       	connection = dbConnectionController.getConnection();
       	int resultTicketId=0;
       	PreparedStatement ps = null;
       	ResultSet rsKey = null;
       	
       	String insertSql = "INSERT INTO tickets (is_seat_allocated, showtime_id, order_id) VALUES (?, ?, ?)";
           try {
           	 ps = connection.prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS);
                ps.setBoolean(1, ticket.getIsSeatAllocated());
                ps.setInt(2, ticket.getShowtimeId());
                ps.setInt(3, ticket.getOrderId());
              
                int i = ps.executeUpdate();
                
                if(i!=NO_AFFECTED_ROWS) {
              	  rsKey = ps.getGeneratedKeys();
              	  if(rsKey.next()) 
              	  {
              		resultTicketId = rsKey.getInt(FIRST_COLUMN_TO_RETURN_THE_ID);
              		    return resultTicketId;
              	  }
                }
           } catch (SQLException ex) {
               ex.printStackTrace();
           }finally {
        	   dbConnectionController.closeConnection(rsKey, ps, connection);
       	}  
           return NO_AFFECTED_ROWS;
       }
    
    
    /**
     * Update the ticket in the tickets table in the database.
     *
     * @param ticket the ticket
     * @return true, if successful
     */
    @Override
	public boolean updateTicket(Ticket ticket) {
    	connection = dbConnectionController.getConnection();
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("UPDATE tickets SET is_seat_allocated=?, showtime_id=?, order_id=? WHERE id=?");
             ps.setBoolean(1, ticket.getIsSeatAllocated());
             ps.setInt(2, ticket.getShowtimeId());
             ps.setInt(3, ticket.getOrderId());
             ps.setInt(4, ticket.getTicketId());

             
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
     * Delete a ticket from the tickets table in the database.
     *
     * @param id the id
     * @return true, if successful
     */
    @Override
    public boolean deleteTicket(int id) {
	connection = dbConnectionController.getConnection();  
	Statement stmt = null;
    try {
        stmt = connection.createStatement();
        int i = stmt.executeUpdate("DELETE FROM tickets WHERE ticket_id=" + id);
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
