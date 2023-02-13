package com.danielly.jsf.cinema.services;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;

import com.danielly.jsf.cinema.dao.interfaces.HallDao;
import com.danielly.jsf.cinema.dao.interfaces.MovieDao;
import com.danielly.jsf.cinema.dao.interfaces.OrderDao;
import com.danielly.jsf.cinema.dao.interfaces.SeatDao;
import com.danielly.jsf.cinema.dao.interfaces.ShowtimeDao;
import com.danielly.jsf.cinema.dao.interfaces.ShowtimeSeatsDao;
import com.danielly.jsf.cinema.dao.interfaces.TicketDao;
import com.danielly.jsf.cinema.dao.interfaces.TicketSeatDao;
import com.danielly.jsf.cinema.model.Hall;
import com.danielly.jsf.cinema.model.Movie;
import com.danielly.jsf.cinema.model.Order;
import com.danielly.jsf.cinema.model.Seat;
import com.danielly.jsf.cinema.model.Showtime;
import com.danielly.jsf.cinema.model.Ticket;
import com.danielly.jsf.cinema.model.TicketSeat;
import com.danielly.jsf.cinema.model.enums.OrderStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class OrderDetails is a service class that being used to search and present the details of the order and to cancel it.
 * When using the class we can set the order id and than the onload gets the order object.
 * Once we loaded the the order we can get the order object.
 * In addition, we can use the search method which is being used in the "View / Cancel Order" page - view-order.xhtml.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


@Named("search_order")
@ViewScoped
public class OrderDetails implements Serializable {
	
	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The order dao. */
    @Inject
    private OrderDao orderDao;
    
    /** The showtime dao. */
    @Inject
    private ShowtimeDao showtimeDao;
    
    /** The movie dao. */
    @Inject
    private MovieDao movieDao;
    
    /** The ticket dao. */
    @Inject
    private TicketDao ticketDao;
    
    /** The ticket seat dao. */
    @Inject
    private TicketSeatDao ticketSeatDao;
    
    /** The seat dao. */
    @Inject
    private SeatDao seatDao;
    
    /** The ticket seat. */
    @Inject
    private TicketSeat ticketSeat;
    
    /** The hall dao. */
    @Inject
    private HallDao hallDao;
    
    /** The showtime seats dao. */
    @Inject
    private ShowtimeSeatsDao showtimeSeatsDao;
    
    /** The service view which is used to run methods that related to the GUI. */
    @Inject
    private ServiceView serviceView;
    
    /** The order id string which will contain the input. */
    @Pattern(regexp = "[0-9]{2,5}", message = "Please enter a valid order number.")   
    private String orderIdString;
    
    /** The order id. */
    private int orderId;
    
    /** The order. */
    private Order order;    
    
    /** The showtime that is in the order. */
    private Showtime showtime;
    
    /** The movie that is in the order. */
    private Movie movie;
    
    /** The hall that is in the order. */
    private Hall hall;
    
    /** The tickets list that is in the order.. */
    private List<Ticket> ticketsList;
    
    /** The seats list that is in the order. */
    private List<Seat> seatsList;
	
	/** The ticket seat list. */
	private List<TicketSeat> ticketSeatList;
	
	/** The ticket counter. */
	private int ticketCounter;
	
    /** The indicator if the order is valid. */
    private boolean orderValidated;
    
	/**  The indicator if the order contains allocated\assigned seats. */
	private boolean isAlloctedSeat = false;
	
	/** The Constant FIRST_TICKET_TO_SET_SEATING_TYPE_IN_ORDER is used to determine if the seating is allocated or free seating. */
	private final static int FIRST_TICKET_TO_SET_SEATING_TYPE_IN_ORDER = 0;
	
	/** The Constant MESSAGE_NO_TICKETS_FOUND. */
	private final static String MESSAGE_NO_TICKETS_FOUND = "We were unable to find tickets under this order";
	
	/** The Constant MESSAGE_ORDER_NOT_FOUND. */
	private final static String MESSAGE_ORDER_NOT_FOUND = "Order not found";
	
	/** The Constant MESSAGE_ORDER_ALREADY_CANCELLED. */
	private final static String MESSAGE_ORDER_ALREADY_CANCELLED = "The order has already been cancelled";
	
	/** The Constant MESSAGE_ORDER_CANNOT_BE_CANCELLED_SHOWTIME_LESS_THAN_DAY. */
	private final static String MESSAGE_ORDER_CANNOT_BE_CANCELLED_SHOWTIME_LESS_THAN_DAY = "Orders can be cancelled till 24 hours before the showtime";	
	

	//Onload
    /**
	 * Onload - this method is being loaded with every navigation action to this page
	 * In addition, this method is also being called from searchOrder method.
	 */
    public void onload() {
        order = orderDao.getOrder(orderId);
    }
    
    
    //Class Methods
    /**
     * Search an order and get all the details.
     *
     * @param orderIdString the order id as a string
     */
    public void searchOrder(String orderIdString) {
    	setOrderIdString(orderIdString);	
    	onload();
    	   	if (order==null) {
    	   		orderValidated= false;
    	   		serviceView.sendMessageToView(MESSAGE_ORDER_NOT_FOUND);
    	   	}
    	   	else {
    	   		orderValidated=true;
	    	   	showtime = showtimeDao.getShowtime(this.getOrder().getShowtimeId());
	    	   	hall = hallDao.getHall(showtime.getHallId());
	    	   	movie = movieDao.getMovie(showtime.getMovieId());
	    	   	ticketsList = ticketDao.getTicketsByOrder(orderId);
	    	   	
	    	   	if (ticketsList.isEmpty()) {
	    	   		serviceView.sendMessageToView(MESSAGE_NO_TICKETS_FOUND);
	    	   	} 
	    	   	else{ 		
	    	   		ticketCounter = ticketsList.size();
	    	   		ticketSeatList = new ArrayList<TicketSeat>();
	    	   		seatsList =  new ArrayList<Seat>();
	    	   		isAlloctedSeat= ticketsList.get(FIRST_TICKET_TO_SET_SEATING_TYPE_IN_ORDER).getIsSeatAllocated();
	    	   		if (isAlloctedSeat) {
		    	   		for (Ticket ticket: ticketsList) {
		    	   			ticketSeat = ticketSeatDao.getTicketSeatByTicketId(ticket.getTicketId());
		    	   			ticketSeatList.add(ticketSeat); 
		    	   			seatsList.add(seatDao.getSeat(ticketSeat.getSeatId()));	    	   			
		    	   		}
	    	   		}
	    	   	}
    	   	}   	
    }
    
    /**
     * Cancel the order and update all of the relevant tables.
     * The method will do a few check before canceling the order
     */
    public void cancelOrder() {
    	if (((order.getStatus()).equals(OrderStatus.CANCELED.getLabel()))) { 
    		serviceView.sendMessageToView(MESSAGE_ORDER_ALREADY_CANCELLED);
    	}
    	else {   		
    		LocalDateTime now = LocalDateTime.now();
    		LocalDateTime dayBeforeShowtime = showtime.getShowtimeDateHour().minusDays(1);
    		   		
    		if (!now.isBefore(dayBeforeShowtime)) {		//check if the cancel action is less than 24 hour before the showtime
    			serviceView.sendMessageToView(MESSAGE_ORDER_CANNOT_BE_CANCELLED_SHOWTIME_LESS_THAN_DAY);		    	
    		}
    		else {    			   			
    		 	order.setStatus(OrderStatus.CANCELED.getLabel());
		    	orderDao.updateOrder(order);
		    	showtimeDao.changeAmountShowtimeAvailableTickets(showtime.getShowtimeId(), ticketCounter);
		    	if (isAlloctedSeat) {
		    		for (Seat seat :seatsList) {
		    			showtimeSeatsDao.updateShowtimeSeatToNotTaken(showtime.getShowtimeId(), seat.getSeatId());
		    		}
		    	}
    		}	      
    	}
    }
    
      
    //Getters And Setters
    /**
     * Gets the order.
     *
     * @return the order
     */
    public Order getOrder() {
        return order;
    }
    
    /**
	 * Gets the order id string.
	 *
	 * @return the order id string
	 */
	public String getOrderIdString() {
		return orderIdString;
	}

	/**
	 * Sets the order id string.
	 *
	 * @param orderIdString the new order id string
	 */
	public void setOrderIdString(String orderIdString) {
		
		this.orderIdString = orderIdString;
		setOrderId(Integer.parseInt(orderIdString));
	}
	
    /**
     * Gets the order id.
     *
     * @return the order id
     */
    public int getOrderId() {
        return orderId;
    }
      
    /**
     * Sets the order id.
     *
     * @param orderId the new order id
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }	

	/**
	 * Gets the showtime.
	 *
	 * @return the showtime
	 */
	public Showtime getShowtime() {
		return showtime;
	}

	/**
	 * Gets the movie.
	 *
	 * @return the movie
	 */
	public Movie getMovie() {
		return movie;
	}

    /**
     * Gets the checks if is allocted seat.
     *
     * @return the checks if is allocted seat
     */
    public Boolean getIsAlloctedSeat() {
		return isAlloctedSeat;
	}

    /**
     * Gets the seats list.
     *
     * @return the seats list
     */
    public List<Seat> getSeatsList() {
		return seatsList;
	}

	/**
	 * Gets the hall.
	 *
	 * @return the hall
	 */
	public Hall getHall() {
		return hall;
	}

	/**
	 * Gets the ticket counter.
	 *
	 * @return the ticket counter
	 */
	public int getTicketCounter() {
			return ticketCounter;
	}
	
	/**
	 * Checks if the order is valid.
	 *
	 * @return true, if is order validated
	 */
	public boolean isOrderValidated() {
		return orderValidated;
	}
    
}
