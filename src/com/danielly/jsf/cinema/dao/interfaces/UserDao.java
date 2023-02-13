package com.danielly.jsf.cinema.dao.interfaces;


import java.util.List;

import com.danielly.jsf.cinema.model.User;


/**
 * UserDao is an Interface class which has all the methods to query the users table in the Database.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


public interface UserDao {

	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	List<User> getAllUsers();

	/**
	 * Gets the user.
	 *
	 * @param id the id
	 * @return the user
	 */
	User getUser(int id);

	/**
	 * Insert user.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	boolean insertUser(User user);

	/**
	 * Update user.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	boolean updateUser(User user);

	/**
	 * Delete user.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	boolean deleteUser(int id);

	/**
	 * Authenticate user.
	 *
	 * @param username the username
	 * @param password the password
	 * @return true, if successful
	 */
	boolean authenticateUser(String username, String password);

	/**
	 * Gets the user by username.
	 *
	 * @param username the username
	 * @return the user by name
	 */
	User getUserByName(String username);
}
