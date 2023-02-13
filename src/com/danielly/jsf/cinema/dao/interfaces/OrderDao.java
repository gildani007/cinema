package com.danielly.jsf.cinema.dao.interfaces;

import java.util.List;

import com.danielly.jsf.cinema.model.Order;

/**
 * OrderDao is an Interface class which has all the methods to query the orders table in the Database.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */

public interface OrderDao {

	/**
	 * Gets the all orders.
	 *
	 * @return the all orders
	 */
	List<Order> getAllOrders();
	
	/**
	 * Gets the order.
	 *
	 * @param id the id
	 * @return the order
	 */
	Order getOrder(int id);
    
    /**
     * Insert order.
     *
     * @param order the order
     * @return true, if successful
     */
    boolean insertOrder(Order order);	
	
	/**
	 * Update order.
	 *
	 * @param order the order
	 * @return true, if successful
	 */
	boolean updateOrder(Order order);				
	
	/**
	 * Delete order.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	boolean deleteOrder(int id);
	
	/**
	 * Insert order and return order id.
	 *
	 * @param order the order
	 * @return the int
	 */
	int insertOrderAndReturnId(Order order);

}
