package com.danielly.jsf.cinema.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.danielly.jsf.cinema.dao.interfaces.UserDao;
import com.danielly.jsf.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * The class UserServiceImpl contains all the methods implementation to query the users table.
 * It contains the basic CRUD methods and additional methods that were needed.
 * This class implements the UserDao interface
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */


@ApplicationScoped
public class UserServiceImpl implements UserDao {

	//Attributes
	/** The Database Connection Controller. */
	@Inject
	private DbConnectionController dbConnectionController;
	
	/** The connection to the database. */
	private Connection connection; 
	
    /** The list of all users. */
    private List<User> allUsers;
    
    /** The Constant ONE_AFFECTED_OR_MATCHED_ROW is used to check if there is 1 affected or matched row.*/
    private final static int ONE_AFFECTED_OR_MATCHED_ROW = 1;

    
    
    //Empty Constructor
    /**
     * Instantiates a new UserServiceImpl.
     */
    public UserServiceImpl() { 
    }
    
    
    
   //Database Queries Methods
    /**
     * Gets all of the users from the users table in the database.
     *
     * @return the all users
     */
    @Override
    public List<User> getAllUsers() {
    
    	Statement stmt = null;
    	ResultSet rs = null;
    	allUsers = new ArrayList<>();
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    		rs=stmt.executeQuery("select * from users");    
    		while(rs.next())
    			{  
	    		User user = new User(); 
	    		user.setUserId(rs.getInt("user_id"));
	    		user.setFirstName(rs.getString("first_name"));  
	    		user.setLastName(rs.getString("last_name"));  	    		
	    		user.setUsername(rs.getString("user_name"));
	    		user.setPassword(rs.getString("user_password"));
	    		user.setEmailAddress(rs.getString("email"));
	    		user.setRole(rs.getString("user_role"));
	    		
	    		allUsers.add(user);
	    		
    			}  
    	
    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			dbConnectionController.closeConnection(rs, stmt, connection);
    		} 
    return allUsers;
    }  

    /**
     * Gets the user from the users table in the database.
     *
     * @param id the id
     * @return the user
     */
    @Override
    public User getUser(int id) {
    	connection = dbConnectionController.getConnection();
    	Statement stmt = null;
    	ResultSet rs = null;
        try {
        	 stmt = connection.createStatement();
        	 rs = stmt.executeQuery("SELECT * FROM users WHERE user_id=" + id);
        	 
        	 if(rs.next())
        	 {
        			User user = new User(); 
        			user.setUserId(rs.getInt("user_id"));
    	    		user.setFirstName(rs.getString("first_name"));  
    	    		user.setLastName(rs.getString("last_name"));  	    		
    	    		user.setUsername(rs.getString("user_name"));
    	    		user.setPassword(rs.getString("user_password"));
    	    		user.setEmailAddress(rs.getString("email"));
    	    		user.setRole(rs.getString("user_role"));
	    		
	            return user;
        	 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(rs, stmt, connection);
    	}  
        return null;
    }
       
    /**
     * Gets the user by name from the users table in the database.
     *
     * @param username the username
     * @return the user by name
     */
    @Override
    public User getUserByName(String username) {
    	connection = dbConnectionController.getConnection();
    	Statement stmt = null;
    	ResultSet rs = null;
        try {
        	 stmt = connection.createStatement();
        	 rs = stmt.executeQuery("SELECT * FROM users WHERE user_name='" + username+"'");
        	 
        	 if(rs.next())
        	 {
        			User user = new User(); 
    	    		user.setUserId(rs.getInt("user_id"));
    	    		user.setFirstName(rs.getString("first_name"));  
    	    		user.setLastName(rs.getString("last_name"));  	    		
    	    		user.setUsername(rs.getString("user_name"));
    	    		user.setPassword(rs.getString("user_password"));
    	    		user.setEmailAddress(rs.getString("email"));
    	    		user.setRole(rs.getString("user_role"));
	    		
	            return user;
        	 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(rs, stmt, connection);
    	}  
        return null;
    }
    
    
    
    
    /**
     * Authenticate user with the user in the database.
     * 
     * @param username the username
     * @param password the password
     * @return true, if successful
     */
    @Override
    public boolean authenticateUser(String username, String password) {
    	connection = dbConnectionController.getConnection();
    	Statement stmt = null;
    	ResultSet rs = null;
        try {
        	 stmt = connection.createStatement();
        	 rs = stmt.executeQuery("SELECT user_id  FROM users WHERE user_name ='" + username + "' AND user_password = crypt('"+ password +"' ,user_password);");        	 
        	 if(rs.next())	{
        		 return true; 
        	 }
        	 else {
        		 return false;
        	 }
        	
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(rs, stmt, connection);
    	}  
        return false;
    }

    
    /**
     * Insert a user to the users table in the database.
     * The password will be stored encrypted in the database
     * @param user the user
     * @return true, if successful
     */
    @Override
	public boolean insertUser(User user) {
    	connection = dbConnectionController.getConnection(); 
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("INSERT INTO users (first_name, last_name, user_name, user_password, email, user_role) VALUES(?, ?, ?, crypt(?, gen_salt('bf')), ?, ?);");
             ps.setString(1, user.getFirstName());           
             ps.setString(2, user.getLastName()); 
             ps.setString(3, user.getUsername()); 
             ps.setString(4, user.getPassword()); 
             ps.setString(5, user.getEmailAddress()); 
             ps.setString(6, user.getRole()); 
             
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
     * Update user in the users table in the database..
     *
     * @param user the user
     * @return true, if successful
     */
    @Override
	public boolean updateUser(User user) {
    	connection = dbConnectionController.getConnection();
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("UPDATE users SET first_name=?, last_name=?, user_name=?, user_password=crypt(?, gen_salt('bf')), email=?, user_role=? WHERE user_id=?;");
        	 ps.setString(1, user.getFirstName());           
             ps.setString(2, user.getLastName()); 
             ps.setString(3, user.getUsername()); 
             ps.setString(4, user.getPassword()); 
             ps.setString(5, user.getEmailAddress()); 
             ps.setString(6, user.getRole()); 
             ps.setInt(7, user.getUserId());
             
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
     * Delete user from the users table in the database..
     *
     * @param userId the user id
     * @return true, if successful
     */
    @Override
    public boolean deleteUser(int userId) {
	connection = dbConnectionController.getConnection();  
	Statement stmt = null;
    try {
        stmt = connection.createStatement();
        int i = stmt.executeUpdate("DELETE FROM users WHERE user_id=" + userId);
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

