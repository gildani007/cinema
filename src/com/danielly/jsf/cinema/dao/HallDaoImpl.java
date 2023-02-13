package com.danielly.jsf.cinema.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.danielly.jsf.cinema.dao.interfaces.HallDao;
import com.danielly.jsf.cinema.model.Hall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The class HallDaoImpl contains all the methods implementation to query the Halls table.
 * It contains the basic CRUD methods and additional methods that were needed.
 * This class implements the HallDao interface
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */

@ApplicationScoped
public class HallDaoImpl implements HallDao {
    
	
	//Attributes
    /** The list of all the halls. */
    private List<Hall> allHalls;
    
    /** The connection to the database. */
    private Connection connection;  
    
    /** The Database Connection Controller. */
    @Inject
    private DbConnectionController dbConnectionController;
    
    /** The Constant NO_AFFECTED_ROWS is used to check if there any affected rows .*/
    private final static int NO_AFFECTED_ROWS = 0;
    
    /** The Constant ONE_AFFECTED_OR_MATCHED_ROW is used to check if there is 1 affected or matched row.*/
    private final static int ONE_AFFECTED_OR_MATCHED_ROW = 1;
    
    /** The Constant FIRST_COLUMN_TO_RETURN_THE_ID is used to define the column the Id can be found in the database. */
    private final static int FIRST_COLUMN_TO_RETURN_THE_ID = 1;
      
    //Empty Constructor
    /**
     * Instantiates a new hallDaoImpl.
     */
    public HallDaoImpl() { 
    }
          
    
    //Database Queries Methods
    
    /**
     * Gets all of the halls from the halls table in the database.
     *
     * @return the all halls
     */
    @Override
    public List<Hall> getAllHalls() {
    
    	Statement stmt = null;
    	ResultSet rs = null;
    	allHalls = new ArrayList<>();
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("select * from halls");    
    		while(rs.next())
    			{  
	    		Hall hall = new Hall();  
	    		hall.setHallId(rs.getInt("hall_id"));  
	    		hall.setHallName(rs.getString("hall_name"));  	    		
	    		hall.setNumberOfRows(rs.getInt("number_of_rows"));  
	    		hall.setSeatsPerRow(rs.getInt("seats_per_row"));
	    		hall.setTotalNumberOfSeats(rs.getInt("total_number_of_seats"));
	    		
	    		allHalls.add(hall);
	    		
    			}  
    	
    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt, connection);
    		} 
    return allHalls;
    }  

    /**
     * Gets the hall by Id from the halls table in the database.
     *
     * @param id the id
     * @return the hall
     */
    @Override
    public Hall getHall(int id) {
    	connection = dbConnectionController.getConnection(); 
    	Statement stmt = null;
    	ResultSet rs = null;
        try {
        	 stmt = connection.createStatement();
        	 rs = stmt.executeQuery("SELECT * FROM halls WHERE hall_id=" + id);
        	 
        	 if(rs.next())
        	 {
    			Hall hall = new Hall();  
	    		hall.setHallId(rs.getInt("hall_id"));  
	    		hall.setHallName(rs.getString("hall_name"));  	    		
	    		hall.setNumberOfRows(rs.getInt("number_of_rows"));  
	    		hall.setSeatsPerRow(rs.getInt("seats_per_row"));
	    		hall.setTotalNumberOfSeats(rs.getInt("total_number_of_seats"));
	    		
	            return hall;
        	 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(rs, stmt, connection);
    	}  
        return null;
    }
    

    
    /**
     * Insert a hall to the halls table in the database.
     *
     * @param hall the hall
     * @return true, if successful
     */
    @Override
	public boolean insertHall(Hall hall) {
    	connection = dbConnectionController.getConnection(); 
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("INSERT INTO public.halls (hall_name, number_of_rows, seats_per_row, total_number_of_seats) VALUES(?, ?, ?, ?);");
             ps.setString(1, hall.getHallName());           
             ps.setInt(2, hall.getNumberOfRows());
             ps.setInt(3, hall.getSeatsPerRow());
             ps.setInt(4, hall.getTotalNumberOfSeats());
             
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
     * Insert hall and return the hall id.
     *
     * @param hall the hall
     * @return the returned hall Id
     */
    @Override
	public int insertHallAndReturnId(Hall hall) {
    	connection = dbConnectionController.getConnection(); 
    	String insertSqlShowtime="INSERT INTO public.halls (hall_name, number_of_rows, seats_per_row, total_number_of_seats) VALUES(?, ?, ?, ?);";
    	PreparedStatement ps = null;
    	ResultSet rsKey = null;
    	int resultHallId =0;
        try {
        	 ps = connection.prepareStatement(insertSqlShowtime,Statement.RETURN_GENERATED_KEYS);
             ps.setString(1, hall.getHallName());  
             ps.setInt(2, hall.getNumberOfRows());
             ps.setInt(3, hall.getSeatsPerRow());
             ps.setInt(4, hall.getTotalNumberOfSeats());
           
             int i = ps.executeUpdate();
            
             if(i!=NO_AFFECTED_ROWS) {
           	  rsKey = ps.getGeneratedKeys();
           	  if(rsKey.next()) 
           	  {
           		resultHallId = rsKey.getInt(FIRST_COLUMN_TO_RETURN_THE_ID);
           		  
           		    return resultHallId;
           	  }
             }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(rsKey, ps, connection);
    	}  
        return resultHallId;
    }
    
    
    
    /**
     * Update hall in the halls table in the database.
     *
     * @param hall the hall
     * @return true, if successful
     */
    @Override
	public boolean updateHall(Hall hall) {
    	connection = dbConnectionController.getConnection(); 
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("UPDATE halls SET hall_name=?, total_number_of_seats=?, number_of_rows=?, seats_per_row=? WHERE id=?");
             ps.setString(1, hall.getHallName());
             ps.setInt(2, hall.getTotalNumberOfSeats());
             ps.setInt(3, hall.getNumberOfRows());
             ps.setInt(4, hall.getSeatsPerRow());
             ps.setInt(5, hall.getHallId());
             
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
     * Delete hall from the halls table in the database.
     *
     * @param id the id
     * @return true, if successful
     */
    @Override
    public boolean deleteHall(int id) {
	connection = dbConnectionController.getConnection();  
	Statement stmt = null;
    try {
        stmt = connection.createStatement();
        int i = stmt.executeUpdate("DELETE FROM halls WHERE hall_id=" + id);
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
