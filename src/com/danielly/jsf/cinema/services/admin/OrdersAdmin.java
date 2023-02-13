package com.danielly.jsf.cinema.services.admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.dao.interfaces.OrderDao;
import com.danielly.jsf.cinema.dao.interfaces.ShowtimeDao;
import com.danielly.jsf.cinema.model.Order;
import com.danielly.jsf.cinema.model.Showtime;

/**
 * The Class OrdersAdmin provides methods to view the orders in the GUI interface.
 * It query the database to present the orders.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


@Named("order_data")
@RequestScoped
public class OrdersAdmin implements Serializable{	  
	
	//Attributes
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The showtime dao. */
	@Inject
    private ShowtimeDao showtimeDao;
	
	/** The order dao. */
	@Inject
	private OrderDao orderDao;
					
	/** The orders list. */
	private List<Order> ordersList;		
    
	/** The Showtimes list. */
	private List<Showtime> ShowtimesList;
    
	/** The showtime map. */
	private HashMap<Integer, Showtime> showtimeMap;
    		

	//Initialize
    /**
     * Initialize method which is loaded before the class is put to service
     * It loads the lists of orders and showtimes which will be used to present the data in the view.
     */
	@PostConstruct
    public void initialize() {
    	ordersList = orderDao.getAllOrders();
    	ShowtimesList = showtimeDao.getListOfShowtimes();    	
    	showtimeMap = new HashMap<Integer, Showtime>();
    	for(Showtime showtime: ShowtimesList ) {
    		showtimeMap.put(new Integer(showtime.getShowtimeId()), showtime);    		
    	}
    }
    	      
	
	/**
	 * Gets the orders list.
	 *
	 * @return the orders list
	 */
	public List<Order> getOrdersList() {
		return ordersList;
	}
	  
	/**
	 * Gets the showtime map.
	 *
	 * @return the showtime map
	 */
	public HashMap<Integer, Showtime> getShowtimeMap() {
		return showtimeMap;
	}

}
