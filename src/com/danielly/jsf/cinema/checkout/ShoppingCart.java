package com.danielly.jsf.cinema.checkout;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.danielly.jsf.cinema.dao.interfaces.PriceDao;
import com.danielly.jsf.cinema.model.Hall;
import com.danielly.jsf.cinema.model.Movie;
import com.danielly.jsf.cinema.model.Price;
import com.danielly.jsf.cinema.model.Seat;
import com.danielly.jsf.cinema.model.Showtime;
import com.danielly.jsf.cinema.model.ShowtimeSeat;
import com.danielly.jsf.cinema.services.ServiceView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The class ShoppingCart holds the information about the choices user made.
 * It contains shoppingCartLines list for every ticket the user wish to purchase
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 */

@Named
@SessionScoped
public class ShoppingCart implements Serializable {
	
	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
	/** The price dao. */
	@Inject
	PriceDao priceDao;
	 
	/** The message view which is used to send message to the GUI. */
	@Inject
	ServiceView serviceView;

    /** The ShoppingCartLine list, each line represents the information of one ticket. */
    private final List<ShoppingCartLine> lines = new ArrayList<>();
    
    /** The total quantity of the tickets. */
    private int totalQuantity = 0;
    
    /** The total amount of the price of the tickets. */
    private int totalAmount = 0;
    
    /** The showtime that the user chose. */
    private Showtime showtime;
    
    /** The movie. */
    private Movie movie;
    
    /** The price. */
    private Price price;
    
    /** The hall. */
    private Hall hall;
    
    /** The seats. */
    private List<Seat> seats;
    
    /** The showtime seats list. */
    private List<ShowtimeSeat> showtimeSeats;
     
    /** The Constant HIGHER_VALUE_CAN_BE_DELETED is used as a safe check to decrement the quantity only if the number is higher than 0. */
    private static final int HIGHER_VALUE_CAN_BE_DELETED = 0;
    
    /** The Constant DECREMENT_AMOUNT is used to decrement the showtime available tickets. */
    private static final int DECREMENT_AMOUNT = 1;
    
    /** The Constant INCREMENT_AMOUNT is used to increment the showtime available tickets. */
    private static final int INCREMENT_AMOUNT = 1;
    
    /** The Constant MESSAGE_NO_MORE_AVAILABLE_TICKETS is used to notify the user. */
    private static final String MESSAGE_NO_MORE_AVAILABLE_TICKETS = "unfortunately there are no more tickets available for this showtime";
   
 	
 	//Class Methods 
    /**
     * Adds the allocated\assigned seat ticket.
     *
     * @param showtimeSeat the showtime seat
     */
    public void addAlloctedSeatTicket(ShowtimeSeat showtimeSeat) 
    {
    		showtimeSeat.changeSelection();
	    	
	    	//Ignore if the seat is already in the list - safety check
	    	for (ShoppingCartLine line : lines) {
	            if (line.getSeat().getSeatId() == showtimeSeat.getSeatId()) 
	                 return;              
	          }
	    	
	    	for (Seat seatFromHall : seats) {
	            if ((seatFromHall.getSeatId() == showtimeSeat.getSeatId()) && (!showtimeSeat.getIsSeatTaken())) 
	            {
			        // No line for this product yet, add a new line
			        lines.add(new ShoppingCartLine(showtime , seatFromHall, price, movie, hall));
			        incrementTotalQuantityAndAmount(price.getPriceNis());
	            }
	    	} 
	    	decrementShowtimeAvailableTickets();
    }

    
    /**
     * Increment total quantity and amount.
     *
     * @param showtimePrice the showtime price
     */
    private void incrementTotalQuantityAndAmount(int showtimePrice) {
        totalQuantity++;
        totalAmount = totalAmount + showtimePrice;
    }
    
    /**
     * Decrement showtime available tickets.
     */
    private void decrementShowtimeAvailableTickets() {
    	if (this.showtime.getAvailableTickets()>HIGHER_VALUE_CAN_BE_DELETED) {
    		this.showtime.setAvailableTickets(this.showtime.getAvailableTickets()-DECREMENT_AMOUNT);
    	}
    }
       
    
    /**
     * Removes the allocated seat ticket.
     *
     * @param showtimeSeat the showtime seat
     */
    public void removeAlloctedSeatTicket(ShowtimeSeat showtimeSeat) 
    {
    		showtimeSeat.changeSelection();
	        Iterator<ShoppingCartLine> it = lines.iterator();
	        while (it.hasNext()) 
	        {
	        	ShoppingCartLine line = it.next();
	        	if (line.getSeat().getSeatId() == showtimeSeat.getSeatId())
	        	{
		            it.remove();           
		            decrementTotalQuantityAndAmount(price.getPriceNis());
	        	}
	        }   
	        incrementShowtimeAvailableTickets();
	        
    }
    
    /**
     * Decrement total quantity and amount.
     *
     * @param showtimePrice the showtime price
     */
    private void decrementTotalQuantityAndAmount(int showtimePrice) {
    	if (totalQuantity>HIGHER_VALUE_CAN_BE_DELETED) {
	        totalQuantity--;
	        totalAmount = totalAmount - showtimePrice;
    	}
    }   
    
    /**
     * Increment showtime available tickets.
     */
    private void incrementShowtimeAvailableTickets() {
        this.showtime.setAvailableTickets(this.showtime.getAvailableTickets()+INCREMENT_AMOUNT);
    }    
        
    /**
     * Adds the free seat ticket.
     */
    //free seating - not allocated/assigned seats
    public void addFreeSeatTicket() {
    	lines.add(new ShoppingCartLine(showtime, price, movie, hall));
    	if (showtime.getAvailableTickets()>HIGHER_VALUE_CAN_BE_DELETED)
    	{
			incrementTotalQuantityAndAmount(price.getPriceNis());
			decrementShowtimeAvailableTickets();
    	}
    	else
    	{
    		serviceView.sendMessageToView(MESSAGE_NO_MORE_AVAILABLE_TICKETS);
    	}
    }

    
    /**
     * Removes the free seat ticket.
     */
    public void removeFreeSeatTicket() {
    	 Iterator<ShoppingCartLine> it = lines.iterator();
	        while (it.hasNext()) 
	        {
	        	ShoppingCartLine line = it.next();
	        	if (line.getSeat() == null)
	        	{
		            it.remove();           
		            decrementTotalQuantityAndAmount(price.getPriceNis());
		            break;
	        	}
	        }
	      incrementShowtimeAvailableTickets();
    }
       
    
    /**
     * Empty shopping cart.
     */
    public void emptyShoppingCart() {
    	 Iterator<ShoppingCartLine> it = lines.iterator();
         while (it.hasNext()) 
         {
         	ShoppingCartLine line = it.next();
         	if (line.getSeat()!=null) {
         		for (ShowtimeSeat showSeat : showtimeSeats) {
         				if ((showSeat.getSeatId()==(line.getSeat()).getSeatId())){
         				showSeat.changeSelection();
         				}
         		}
         	}
         }	
        lines.clear();
        totalQuantity = 0;
        totalAmount = 0;
    }

    
  //Getters and Setters 
  /**
   * Sets the seats.
   *
   * @param seats the new seats
   */
    public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	/**
	 * Gets the lines.
	 *
	 * @return the lines
	 */
	public List<ShoppingCartLine> getLines() {
        return lines;
    }

    /**
     * Gets the total quantity.
     *
     * @return the total quantity
     */
    public int getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * Gets the total amount.
     *
     * @return the total amount
     */
    public int getTotalAmount() {
        return totalAmount;
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
     * Gets the seats.
     *
     * @return the seats
     */
    public List<Seat> getSeats() {
 		return seats;
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
	 * Sets the movie.
	 *
	 * @param movie the new movie
	 */
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	/**
	 * Sets the showtime.
	 *
	 * @param showtime the new showtime
	 */
	public void setShowtime(Showtime showtime) {
		this.showtime = showtime;
	}
    
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Price getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(Price price) {
		this.price = price;
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
	 * Sets the hall.
	 *
	 * @param hall the new hall
	 */
	public void setHall(Hall hall) {
		this.hall = hall;
	}
    
	/**
	 * Gets the showtime seats.
	 *
	 * @return the showtime seats
	 */
	public List<ShowtimeSeat> getShowtimeSeats() {
		return showtimeSeats;
	}

	/**
	 * Sets the showtime seats.
	 *
	 * @param showtimeSeats the new showtime seats
	 */
	public void setShowtimeSeats(List<ShowtimeSeat> showtimeSeats) {
		this.showtimeSeats = showtimeSeats;
	}

}