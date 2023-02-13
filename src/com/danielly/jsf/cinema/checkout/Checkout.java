package com.danielly.jsf.cinema.checkout;

import javax.annotation.PostConstruct;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.danielly.jsf.cinema.dao.interfaces.OrderDao;
import com.danielly.jsf.cinema.dao.interfaces.ShowtimeDao;
import com.danielly.jsf.cinema.dao.interfaces.ShowtimeSeatsDao;
import com.danielly.jsf.cinema.dao.interfaces.TicketDao;
import com.danielly.jsf.cinema.dao.interfaces.TicketSeatDao;
import com.danielly.jsf.cinema.model.Order;
import com.danielly.jsf.cinema.model.Showtime;
import com.danielly.jsf.cinema.model.ShowtimeSeat;
import com.danielly.jsf.cinema.model.Ticket;
import com.danielly.jsf.cinema.model.enums.OrderStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The Class Checkout is in charge of the checkout process.
 * Starting with the purchase request, the validation and the necessary checks
 * and finishing with completing the order, issuing the tickets and updating the database.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 */
 
@Named
@FlowScoped("checkout")
public class Checkout implements Serializable {

	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The shopping cart. */
    @Inject
    private ShoppingCart shoppingCart;
    
    /** The showtime seats dao. */
    @Inject
    private ShowtimeSeatsDao showtimeSeatsDao;

    /** The showtime dao. */
    @Inject
    private ShowtimeDao showtimeDao;
    
    /** The showtime seat. */
    @Inject
    private ShowtimeSeat showtimeSeat;
    
    /** The showtime. */
    @Inject
    private Showtime showtime;
    
    /** The order dao. */
    @Inject
    private OrderDao orderDao;
    
    /** The ticket dao. */
    @Inject
    private TicketDao ticketDao;
    
    /** The ticket seat dao. */
    @Inject
    private TicketSeatDao ticketSeatDao;
   
    /** The new order. */
    @Inject
    private Order newOrder;
    
    /** The new ticket. */
    @Inject
    private Ticket newTicket;
    
    
    /** The Constant START_YEARS_CREDIT_CARD_EXP is the current year. */
    private static final int START_YEARS_CREDIT_CARD_EXP = 0;
    
    /** The Constant END_YEARS_CREDIT_CARD_EXP is the number of years the user can choose for the Expiration Date. */
    private static final int END_YEARS_CREDIT_CARD_EXP = 5;
    
    /** The Constant NO_MORE_TICKETS_AVAILABLE is being used for checking tickets availability . */
    private static final int NO_MORE_TICKETS_AVAILABLE=0;
 
    /** The Constant HIGHER_ORDER_ID_IS_VALID is being used for validating Order Id. */
    private static final int HIGHER_ORDER_ID_IS_VALID =0;    
    
    /** The Constant LOWER_ORDER_ID_IS_INVALID is being used for validating Order Id. */
    private static final int LOWER_ORDER_ID_IS_INVALID =1;
    
    /** The Constant HIGHER_TICKET_ID_IS_VALID is being used for validating Ticket Id. */
    private static final int HIGHER_TICKET_ID_IS_VALID =0;
   
    /** The Constant QUANTITY_IS_ZERO is being used to check if the user added any ticket which is without allocated\assigned seat. */
    private static final int QUANTITY_IS_ZERO =0;
    
    /** The Constant NO_SLECTED_SEATS is being used to check if the user selected any seat. */
    private static final int NO_SLECTED_SEATS =0;
    
    /** The payment name. */
    @Size(min = 1, max = 30, message = "Please enter a name.")
    private String paymentName;
    
    /** The credit card type. */
    @NotNull(message = "Please select a credit card type.")
    private CreditCardType creditCardType;

    /** The credit card number. */
    @Pattern(regexp = "[0-9]{16}", message = "Please enter a valid credit card number.")
    private String creditCardNumber;   
    
	/** The credit card cvv. */
	@Pattern(regexp = "[0-9]{3}", message = "Please enter a valid cvv number.")
    private String creditCardCvv;
        
    /** The card expiration month. */
    private String cardExpirationMonth;
    
    /** The card expiration year. */
    private String cardExpirationYear;

    /** The seat selected count. */
    private int seatSelectedCount=0;
	
	/** The order id returned from the insert to the database. */
	private int orderIdReturnedFromInsert=0;
    
    /** The ticket id list which returned from insert queries to the database. */
    private List<Integer> ticketIdListReturnedFromInserts;
    
    /** The months exp list. */
    private LinkedHashMap<String,String> monthsExpList;
    
    /** The years exp list. */
    private List<String> yearsExpList;
       
    /** The payment success is being used as flag for successful payment. */
    private boolean paymentSuccess;
    
    /** The order created is being used as flag for successful order creation. */
    private boolean orderCreated = false;   
    
    /** The ticket created is being used as flag for successful ticket creation. */
    private boolean ticketCreated = false;
    
    /** The general order issue failure is being used as flag for general order issue related to unexpected result from the database */
    private boolean generalOrderIssueFailure = false;
    
    /** The ticket seat check success is being used as flag for ticket and seat availability. */
    private boolean ticketSeatCheckSuccess;
	
	/** The seat allocated is being used as a type of seating - assigned\allocated or free seating. */
	private boolean seatAllocated=true;
	
	
    /**
     * Initialize the years and the months expiration lists which will be used in the payment stage.
     */
    @PostConstruct
    public void initialize() {
        yearsExpList= this.getCcExpYears(); 
        monthsExpList= this.getCcExpMonths();     
    } 
     
    
    //Class Methods
    /**
     * Gets the Credit Card Expiration years list.
     *
     * @return the Credit Card Expiration years list of Strings
     */
    private ArrayList<String> getCcExpYears(){
    	ArrayList<String> newYearsExpList = new ArrayList<String>();
    	LocalDate currentdate = LocalDate.now();
    	int startExpYear = currentdate.getYear();
    	
    	for (int i=START_YEARS_CREDIT_CARD_EXP; i<END_YEARS_CREDIT_CARD_EXP;i++){
    		newYearsExpList.add(String.valueOf(startExpYear));
    		startExpYear++;
    	}	
    	return newYearsExpList;
    }
    
    /**
      * Gets the Credit Card Expiration months list.
     *
     * @return the Credit Card Expiration months list of Strings
     */
    private LinkedHashMap<String,String> getCcExpMonths(){
    	LinkedHashMap<String,String> newMonthsList = new LinkedHashMap<String,String>();
    	newMonthsList.put("January","01");
    	newMonthsList.put("February","02");
    	newMonthsList.put("March","03");
    	newMonthsList.put("April","04");
    	newMonthsList.put("May","05");
    	newMonthsList.put("June","06");
    	newMonthsList.put("July","07");
    	newMonthsList.put("August","08");
    	newMonthsList.put("September","09");
    	newMonthsList.put("October","10");
    	newMonthsList.put("November","11");
    	newMonthsList.put("December","12");	
    	return newMonthsList;
    }
   
    
    /**
     * FulfillOrder is being used to validate and to check the requirements needed and finish with creating the order.
     * The method check the tickets and seats availability if allocated, the payment details and updating the database.
     * In case of a failure with one o the database processes the database will perform rollback and the user will be notified.
     */
    public void fulfillOrder() {
    	ticketIdListReturnedFromInserts = new ArrayList<Integer>();  //will store the tickets ID's
    	int yearExp = Integer.parseInt(cardExpirationYear);
    	int monthExp = Integer.parseInt(cardExpirationMonth);
    	
    	ticketSeatCheckSuccess = checkSeatAndTicket();  
    	if (ticketSeatCheckSuccess) {   		
	        paymentSuccess = (!creditCardNumber.endsWith("00") && (!((LocalDate.now().getYear()==yearExp) && (LocalDate.now().getMonthValue()>monthExp))) );
	       
	        if (paymentSuccess) {	        	
	        	orderCreated = createOrderObjectAndUpdateDB(); 
	        	if (orderCreated) {	        		
	        		//create the ticket and updates showtime available counter 
	        		//if the seat is allocated then set it as taken and add to ticket seat table
	        		ticketCreated=updateTicketAndShowtimeTablesInDb(); 

	        		if (!ticketCreated) {
	        			rollbackTicketAndShowtimeTablesInDb();
	        			orderDao.deleteOrder(orderIdReturnedFromInsert); //rollback order
	        		}
	        	}
	        }
    	}
    	generalOrderIssueFailure = !orderCreated || !ticketCreated;
   }
      
    
    /**
     * Check that there are available tickets for the showtime and the seats availability if the seats are allocated and .
     *
     * @return true, if successful
     */
    public boolean checkSeatAndTicket() { 
    	boolean seatCheckSuccess;
        boolean ticketAvailable;
    	showtime = showtimeDao.getShowtime(shoppingCart.getShowtime().getShowtimeId());
    	ticketAvailable = showtime.getAvailableTickets()>NO_MORE_TICKETS_AVAILABLE;
    	
    	if (!ticketAvailable)
    		return false; 
    	
    	if (seatAllocated){
    		for(ShoppingCartLine line : shoppingCart.getLines()) {
    			showtimeSeat=  showtimeSeatsDao.getShowtimeSeat(line.getSeat().getSeatId(), shoppingCart.getShowtime().getShowtimeId());
    			seatCheckSuccess = !showtimeSeat.getIsSeatTaken();
    			if (!seatCheckSuccess)
    				return false;
    		}
    	}
    	return true;
    }
    
       
    /**
     * Creates the order object and insert the database.
     *
     * @return true, if successful
     */
    private boolean createOrderObjectAndUpdateDB() {
        newOrder.setOrderDateHour(LocalDateTime.now());  
        newOrder.setTotalPriceNis(shoppingCart.getTotalAmount());
        newOrder.setShowtimeId(shoppingCart.getShowtime().getShowtimeId());  
        newOrder.setCardNumber(creditCardNumber);
        newOrder.setCardType(creditCardType.toString());
        newOrder.setCardExpirationYear(cardExpirationYear);
        newOrder.setCardExpirationMonth(cardExpirationMonth);
        newOrder.setCardCvv(creditCardCvv);
        newOrder.setStatus(OrderStatus.SUCCESS.getLabel());       
        newOrder.setPayerName(paymentName);
        
        orderIdReturnedFromInsert = orderDao.insertOrderAndReturnId(newOrder);
        
        if (orderIdReturnedFromInsert>HIGHER_ORDER_ID_IS_VALID)
        	return true;
        
        return false;
    }
     
      
    /**
     * Update the tickets and showtimes tables in the database.
     * These are the tables that are going to be updated:
     * 1. tickets
     * 2. showtimes
     * 3. showtimes_seats - in case the seats are allocated\assigned
     * 4. tickets_seat - in case the seats are allocated\assigned
     * 
     * @return true, if successful
     */
    private boolean updateTicketAndShowtimeTablesInDb() {
    	int newTicketIdFromInsert = 0;
    	boolean isDecrementAvailableTicketsUpdatedInDb=false;
    	boolean isDbUpdatedIfSeatIsAllocted=true;
    	
    	updateNewTicketsAttributes();
    	
    	for(ShoppingCartLine line : shoppingCart.getLines()) {
	    	newTicketIdFromInsert = ticketDao.insertTicketAndReturnId(newTicket);	    	
	    	isDecrementAvailableTicketsUpdatedInDb= decrementAvailableTicket(shoppingCart.getShowtime().getShowtimeId());	    	
	    	
	    	if(this.seatAllocated) {	    		
	    		isDbUpdatedIfSeatIsAllocted= updateDbIfSeatIsAllocted(line.getSeat().getSeatId(), newTicketIdFromInsert);	
	    	}
	    	
	    	if ((newTicketIdFromInsert>HIGHER_TICKET_ID_IS_VALID) && (isDecrementAvailableTicketsUpdatedInDb) &&(isDbUpdatedIfSeatIsAllocted)){  
	    		ticketIdListReturnedFromInserts.add(newTicketIdFromInsert); 
	    		line.setTicketId(newTicketIdFromInsert);
	    	}
	    	
	    	else {  			    			    			    		
	           	return false; 
	    	}
    	}
	    return true;
    }

    /**
     * Update the new tickets attributes in the class level.
     * Can be used for one or more tickets.
     */
    private void updateNewTicketsAttributes() {
    	newTicket.setIsSeatAllocated(seatAllocated);  
    	newTicket.setShowtimeId(shoppingCart.getShowtime().getShowtimeId());
    	newTicket.setOrderId(orderIdReturnedFromInsert); 
    }
    
    
    /**
     * Decrement the number of available tickets.
     *
     * @param showtimeId the showtime id
     * @return true, if successful
     */
    public boolean decrementAvailableTicket(int showtimeId) {
    	return showtimeDao.decrementShowtimeAvaialableTickets(showtimeId);
    }
    
    
    /**
     * Update the database if the seat is allocated\assigned.
     *
     * @param seatId the seat id
     * @param newTempTicketId the new temporary ticket id
     * @return true, if successful
     */
    private boolean updateDbIfSeatIsAllocted(int seatId, int newTempTicketId) {
    	boolean isDbUpdated;
    	boolean isShowtimeSeatUpdateToTaken;
    	boolean isTicketSeatInserted;
    	
    	if (newTempTicketId<LOWER_ORDER_ID_IS_INVALID)
    		return false;
    	
    	isShowtimeSeatUpdateToTaken= showtimeSeatsDao.updateShowtimeSeatToTaken(shoppingCart.getShowtime().getShowtimeId(), seatId);
    	isTicketSeatInserted=ticketSeatDao.insertTicketSeat(newTempTicketId, seatId);  
    	
    	isDbUpdated = (isShowtimeSeatUpdateToTaken && isTicketSeatInserted);
    	
    	return isDbUpdated;
    }
    
    
    /**
     * Rollback tickets and showtimes tables in the database.
     * Will be used in case of a failure to complete the order.
     * These are the tables that are going to be rollback:
     * 1. tickets
     * 2. showtimes
     * 3. showtimes_seats - in case the seats are allocated\assigned
     * 4. tickets_seat - in case the seats are allocated\assigned
     * 
     * @return true, if successful
     */
    private boolean rollbackTicketAndShowtimeTablesInDb() {
    	boolean isIncrementAvailableTicketsUpdatedInDb=false;
    	boolean isRollbackDbIfSeatIsAllocted=true;
    	boolean isRollbackDbShowtimeSeats=true;
    	
    	if(this.seatAllocated) {
    		isRollbackDbShowtimeSeats=rollbackShowtimeSeats();
    	}
    	if(!isRollbackDbShowtimeSeats) {
    		return false;
    	}
    	else {
	    	for(Integer ticketId : ticketIdListReturnedFromInserts) {	    			
				boolean isTicketDeletedFromDb;
				
				if(this.seatAllocated) {	    				
					isRollbackDbIfSeatIsAllocted= rollbackDbTicketsSeats(ticketId);
		    	}	    				    		
				isTicketDeletedFromDb=ticketDao.deleteTicket(ticketId.intValue());
				
				if (isTicketDeletedFromDb) {
					isIncrementAvailableTicketsUpdatedInDb= incrementAvailableTicket(shoppingCart.getShowtime().getShowtimeId());
				}
				
				if (!isIncrementAvailableTicketsUpdatedInDb || !isRollbackDbIfSeatIsAllocted || !isTicketDeletedFromDb)
					return false;
	    	}
    	}
		return true;		
   
    }
    
    /**
     * Rollback the database table showtimes_seats if the seats are allocated\assigned.
     * Will be used in case of a failure to complete the order.
     * 
     * @return true, if successful
     */
    private boolean rollbackShowtimeSeats() {
    	boolean isShowtimeSeatUpdateToNotTaken;
    	for(ShoppingCartLine line : shoppingCart.getLines()) {
    		isShowtimeSeatUpdateToNotTaken= showtimeSeatsDao.updateShowtimeSeatToNotTaken(shoppingCart.getShowtime().getShowtimeId(), line.getSeat().getSeatId());
    		if (!isShowtimeSeatUpdateToNotTaken)
    			return false;
    	}
    	return true;
    }
              
    /**
     * Rollback the database table tickets_seats if the seats are allocated\assigned.
     * Will be used in case of a failure to complete the order.
     *
     * @param newTicketToRollback the new ticket to rollback
     * @return true, if successful
     */
    private boolean rollbackDbTicketsSeats(int newTicketToRollback) {
    	boolean isTicketSeatDelete;
    	       	isTicketSeatDelete=ticketSeatDao.deleteTicketSeat(newTicketToRollback);    	
    	
    	return isTicketSeatDelete;
    }
  
    
    /**
     * Increment the number of available tickets.
     *
     * @param showtimeId the showtime id
     * @return true, if successful
     */
    public boolean incrementAvailableTicket(int showtimeId) {
    	return showtimeDao.incrementShowtimeAvaialableTickets(showtimeId);
    }
    

    /**
     * Check seat and ticket availability for the view.
     *
     * @return the string
     */
    public String checkSeatAndTicketForView() {
    	ticketSeatCheckSuccess=checkSeatAndTicket();
    	if (!ticketSeatCheckSuccess)
    		return "order";
    	else
    		return "payment";
    }   
        
    /**
     * Finish order and empty all selections.
     * Will be used in the end of the checkout process.
     * 
     * @return the string
     */
    public String finishOrder() {
           this.emptySelections();
        return "finished";
    }
    
    /**
     * Cancel order and empty all selections.
     *
     * @return the string
     */
    public String cancelOrder() {
    	 	this.emptySelections();
        return "finished";
    }
    
    /**
     * Empty the selections of the user.
     */
    public void emptySelections() {
        shoppingCart.emptyShoppingCart();
	    seatSelectedCount=0;	   
	}
    
      
    /**
     * Checks if the user chose at least one free seating or allocated\assigned seat.
     * This method is being used in the view to render the continue botton in the order-details.xhtml
     * 
     * @return true, if is free or allocated seat chosen
     */
    public boolean isFreeOrAllocatedSeatChosen() {
		return ((shoppingCart.getTotalQuantity()>QUANTITY_IS_ZERO) || (isThereAnySelectedSeat()));
	}
   
    /**
     * Adds a selected seat to the counter.
     */
    public void addSelectedSeat() {
    	seatSelectedCount++;
    }
    
    /**
     * Removes a selected seat from the counter.
     */
    public void removeSelectedSeat() {
    	seatSelectedCount--;
    }
    
    /**
     * Checks if is there any selected allocated\assigned seats.
     *
     * @return true, if is there any selected seat
     */
    public boolean isThereAnySelectedSeat() {
    	return seatSelectedCount>NO_SLECTED_SEATS;
    }

	/**
	 * Check if the type of seats are allocated\assigned in the order.
	 *
	 * @param seatAllocated the seat allocated
	 * @return the boolean
	 */
	public Boolean checkOrderIfSeatAllocated(Boolean seatAllocated) {
    	return seatAllocated;
    }	
	
	
	 //Getters and Setters
    /**
     * Gets the order id returned from the insert to the database.
     *
     * @return the order id returned from insert
     */
    public int getOrderId() {
		return orderIdReturnedFromInsert;
	}

	/**
	 * Sets the payment name.
	 *
	 * @param paymentName the new payment name
	 */
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	/**
	 * Gets the payment name.
	 *
	 * @return the payment name
	 */
	public String getPaymentName() {
        return paymentName;
    }

    
    /**
     * Gets the credit card type.
     *
     * @return the credit card type
     */
    public CreditCardType getCreditCardType() {
        return creditCardType;
    }

    /**
     * Sets the credit card type.
     *
     * @param creditCardType the new credit card type
     */
    public void setCreditCardType(CreditCardType creditCardType) {
        this.creditCardType = creditCardType;
    }

    /**
     * Gets the credit card number.
     *
     * @return the credit card number
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Sets the credit card number.
     *
     * @param creditCardNumber the new credit card number
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * Checks if is payment success.
     *
     * @return true, if is payment success
     */
    public boolean isPaymentSuccess() {
        return paymentSuccess;
    }

    /**
     * Sets the payment success.
     *
     * @param paymentSuccess the new payment success
     */
    public void setPaymentSuccess(boolean paymentSuccess) {
        this.paymentSuccess = paymentSuccess;
    }
     

	/**
	 * Checks if is seat allocated.
	 *
	 * @return true, if is seat allocated
	 */
	public boolean isSeatAllocated() {
		return seatAllocated;
	}
	
	/**
     * Sets the seats allocated.
     *
     * @param seatAllocated the new seat allocated
     */
	public void setSeatAllocated(boolean seatAllocated) {
		this.seatAllocated = seatAllocated;
	}


	/**
	 * Gets the credit card cvv.
	 *
	 * @return the credit card cvv
	 */
	public String getCreditCardCvv() {
		return creditCardCvv;
	}

	/**
	 * Sets the credit card cvv.
	 *
	 * @param creditCardCvv the new credit card cvv
	 */
	public void setCreditCardCvv(String creditCardCvv) {
		this.creditCardCvv = creditCardCvv;
	}
	
	/**
	 * Gets the months expiration list.
	 *
	 * @return the months expiration list
	 */
	public LinkedHashMap<String, String> getMonthsExpList() {
		return monthsExpList;
	}

	/**
	 * Sets the months expiration list.
	 *
	 * @param monthsExpList the months expiration list
	 */
	public void setMonthsExpList(LinkedHashMap<String, String> monthsExpList) {
		this.monthsExpList = monthsExpList;
	}

	/**
	 * Gets the years expiration list.
	 *
	 * @return the years expiration list
	 */
	public List<String> getYearsExpList() {
		return yearsExpList;
	}

	/**
	 * Sets the years expiration list.
	 *
	 * @param yearsExpList the new years expiration list
	 */
	public void setYearsExpList(List<String> yearsExpList) {
		this.yearsExpList = yearsExpList;
	}

	/**
	 * Gets the card expiration month.
	 *
	 * @return the card expiration month
	 */
	public String getCardExpirationMonth() {
		return cardExpirationMonth;
	}

	/**
	 * Sets the card expiration month.
	 *
	 * @param cardExpirationMonth the new card expiration month
	 */
	public void setCardExpirationMonth(String cardExpirationMonth) {
		this.cardExpirationMonth = cardExpirationMonth;
	}

	/**
	 * Gets the card expiration year.
	 *
	 * @return the card expiration year
	 */
	public String getCardExpirationYear() {
		return cardExpirationYear;
	}

	/**
	 * Sets the card expiration year.
	 *
	 * @param cardExpirationYear the new card expiration year
	 */
	public void setCardExpirationYear(String cardExpirationYear) {
		this.cardExpirationYear = cardExpirationYear;
	}

    /**
     * Checks if is ticket seat check was success.
     * This method is being used in the CheckoutFlow to navigate to seating-failure.xhtml page in case of a false.
     * @return true, if is ticket seat check success
     */
    public boolean isTicketSeatCheckSuccess() {
		return ticketSeatCheckSuccess;
	}

	/**
	 * Checks if is general order issue failure.
	 * This method is being used in the CheckoutFlow to navigate to order-failure.xhtml page in case of a false.
	 * @return true, if is general order issue failure
	 */
	public boolean isGeneralOrderIssueFailure() {
		return generalOrderIssueFailure;
	}

}
