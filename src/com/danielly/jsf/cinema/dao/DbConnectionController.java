package com.danielly.jsf.cinema.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The class DbConnectionController provides Database services for other classes.
 * Using this class we can create and close connections.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 */

@ApplicationScoped
public class DbConnectionController {

	//Empty Constructor       
    /**
     * Instantiates a new db connection controller.
     */
    public DbConnectionController() { 
    }
    
    //Class Methods 
    /**
     * Gets the connection to the PostgreSQL database.
     * Required that in the Glassfish server will be configure a pool for PostgreSQL.
     * In addition, JDBC Resource with the JDNI name jdbc/postgresdb which is using the pool that we configured.
     *   
     * @return the connection
     */
    public Connection getConnection(){
		Connection connection = null;
		try{  
			Context ctx = new InitialContext();

			DataSource ds = (DataSource)ctx.lookup("jdbc/postgresdb");

			connection = ds.getConnection("postgres", "Algotec123");		
		}catch(Exception e){  
			System.out.println(e);  
		}  
		return connection;  
		}  

    
    
    /**
     * Close the connection to the database
     * This method can close the connection of the Statement or PreparedStatement in addition to the Connection itself.
     * 
     * @param stmt the Statement or PreparedStatement
     * @param connection the Connection
     */
    public void closeConnection(Object stmt, Connection connection) {
    	if (stmt != null)
    		if(stmt instanceof PreparedStatement)
			  try { 
				  ((PreparedStatement)stmt).close(); 
			  }catch (SQLException e) {
			  		System.out.println(e);  
			  	}
    		else if(stmt instanceof Statement)
			  try { 
				  ((Statement)stmt).close(); 
			  }catch (SQLException e) {
			  		System.out.println(e);  
			  	}
	    if (connection != null) 
	    	  try { 
	    		  connection.close(); 
	    	  }catch (SQLException e) {
	    		  System.out.println(e);
	    	  }			
    }
    
    /**
     * Close the connection to the database.
     * This method can close the connection of the ResultSet and the Statement in addition to the Connection itself.
     * 
     * @param rs the ResultSet
     * @param stmt the Statement
     * @param connection the Connection
     */
    public void closeConnection(ResultSet rs, Statement stmt, Connection connection) {
   	 if (rs != null) 
	    	  try { 
	    		  rs.close(); 
	    	  }catch (SQLException e) {
	    		  System.out.println(e);
	    	  }	
   	if (stmt != null) 
			  try { 
				  stmt.close(); 
			  }catch (SQLException e) {
			  		System.out.println(e);  
			  	}
	    if (connection != null) 
	    	  try { 
	    		  connection.close(); 
	    	  }catch (SQLException e) {
	    		  System.out.println(e);
	    	  }		    
   }
    
}
