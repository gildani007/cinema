package com.danielly.jsf.cinema.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.danielly.jsf.cinema.dao.interfaces.PriceDao;
import com.danielly.jsf.cinema.model.Price;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The class PriceDaoImpl contains all the methods implementation to query the Prices table.
 * It contains the basic CRUD methods and additional methods that were needed.
 * This class implements the PriceDao interface
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */

@ApplicationScoped
public class PriceDaoImpl implements PriceDao {

	//Attributes
	/** The Database Connection Controller. */
	@Inject
	private DbConnectionController dbConnectionController;
	
	/** The connection to the database. */
    private Connection connection;  
	
	/** The list of all of the prices. */
	private List<Price> allPrices;
    
    /** The Constant ONE_AFFECTED_OR_MATCHED_ROW is used to check if there is 1 affected or matched row.*/
    private final static int ONE_AFFECTED_OR_MATCHED_ROW = 1;

    
    //Empty Constructor
    /**
     * Instantiates a new PriceDaoImpl.
     */
    public PriceDaoImpl() { 
    }
    
   
    //Database Queries Methods
    /**
     * Gets the list of all of the prices from the prices table in the database.
     *
     * @return the all prices
     */
    @Override
    public List<Price> getAllPrices() {
    	Statement stmt = null;
    	ResultSet rs = null;
    	allPrices = new ArrayList<>();
    	try{   
    		connection =  dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("select * from prices");    
    		while(rs.next())
    			{  
	    		Price price = new Price();  
	    		price.setPriceId(rs.getInt("price_id"));  
	    		price.setCategory(rs.getString("category"));  
	    		price.setPriceNis(rs.getInt("price_nis"));

	    		allPrices.add(price);	    		
    			}  
    		
    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			 dbConnectionController.closeConnection(rs, stmt, connection);
    		} 
    return allPrices;
    }  

    
    
    /**
     * Gets the price from the prices table in the database.
     *
     * @param id the id
     * @return the price
     */
    @Override
    public Price getPrice(int id) {
    	connection =  dbConnectionController.getConnection();
    	Statement stmt = null;
    	ResultSet rs = null;
        try {
        	 stmt = connection.createStatement();
        	 rs = stmt.executeQuery("SELECT * FROM prices WHERE price_id=" + id);
        	 
        	 if(rs.next())
        	 {
    			Price price = new Price();  
	    		price.setPriceId(rs.getInt("price_id"));  
	    		price.setCategory(rs.getString("category"));  
	    		price.setPriceNis(rs.getInt("price_nis"));
	             
	            return price;
        	 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	 dbConnectionController.closeConnection(rs, stmt, connection);
    	}  
        return null;
    }
    

    
    /**
     * Insert a price to the prices table in the database.
     *
     * @param price the price
     * @return true, if successful
     */
    @Override
	public boolean insertPrice(Price price) {
    	connection =  dbConnectionController.getConnection(); 
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("INSERT INTO public.prices (category, price_nis) VALUES(?, ?);");
             ps.setString(1, price.getCategory());
             ps.setInt(2, price.getPriceNis());
           
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
     * Update price in the prices table in the database.
     *
     * @param price the price
     * @return true, if successful
     */
    @Override
	public boolean updatePrice(Price price) {
    	connection =  dbConnectionController.getConnection();
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("UPDATE prices SET category=?, price_nis=? WHERE id=?");
             ps.setString(1, price.getCategory());
             ps.setInt(2, price.getPriceNis());
             ps.setInt(3, price.getPriceId());
             
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
     * Delete price from the prices table in the database.
     *
     * @param id the id
     * @return true, if successful
     */
    @Override
    public boolean deletePrice(int id) {
	connection =  dbConnectionController.getConnection();  
	Statement stmt = null;
    try {
        stmt = connection.createStatement();
        int i = stmt.executeUpdate("DELETE FROM prices WHERE price_id=" + id);
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
