package com.danielly.jsf.cinema.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The class Order holds the information about an order such as the total price and credit card information.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 */

public class Order implements Serializable {

	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2L;
    
    /** The order id. */
    private int orderId;
    
    /** The order date hour. */
    private LocalDateTime orderDateHour;
    
    /** The total price in NIS currency. */
    private int totalPriceNis;
	
	/** The showtime id. */
	private int showtimeId;
	
	/** The Credit Card number. */
	private String cardNumber;
	
	/** The Credit Card type. */
	private String cardType;
	
	/** The Credit Card expiration year. */
	public String cardExpirationYear;
	
	/** The Credit Card expiration month. */
	public String cardExpirationMonth;	
	
	/** The  Credit Card cvv. */
	private String cardCvv;
	
	/** The status of the order. */
	private String status;
	
	/** The payer name. */
	private String payerName;
	
	/** The order date hour formated. */
	private String orderDateHourFormated;
	
	/** The card expiration formated. */
	private String cardExpirationFormated;	

	
	//Empty Constructor
	/**
	 * Instantiates a new order.
	 */
	public Order(){
		
	}
			
	//Getters and Setters
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
	 * Gets the order date hour.
	 *
	 * @return the order date hour
	 */
	public LocalDateTime getOrderDateHour() {
		return orderDateHour;
	}

	/**
	 * Sets the order date hour.
	 *
	 * @param orderDateHour the new order date hour
	 */
	public void setOrderDateHour(LocalDateTime orderDateHour) {
		this.orderDateHour = orderDateHour;
	}

	/**
	 * Gets the total price nis.
	 *
	 * @return the total price nis
	 */
	public int getTotalPriceNis() {
		return totalPriceNis;
	}

	/**
	 * Sets the total price nis.
	 *
	 * @param totalPriceNis the new total price nis
	 */
	public void setTotalPriceNis(int totalPriceNis) {
		this.totalPriceNis = totalPriceNis;
	}

	/**
	 * Gets the showtime id.
	 *
	 * @return the showtime id
	 */
	public int getShowtimeId() {
		return showtimeId;
	}

	/**
	 * Sets the showtime id.
	 *
	 * @param showtimeId the new showtime id
	 */
	public void setShowtimeId(int showtimeId) {
		this.showtimeId = showtimeId;
	}

	/**
	 * Gets the card number.
	 *
	 * @return the card number
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * Sets the card number.
	 *
	 * @param cardNumber the new card number
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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
	 * Gets the card cvv.
	 *
	 * @return the card cvv
	 */
	public String getCardCvv() {
		return cardCvv;
	}

	/**
	 * Sets the card cvv.
	 *
	 * @param cardCvv the new card cvv
	 */
	public void setCardCvv(String cardCvv) {
		this.cardCvv = cardCvv;
	}

	/**
	 * Gets the order date hour formated.
	 *
	 * @return the order date hour formated
	 */
	public String getOrderDateHourFormated() {
		return orderDateHourFormated;
	}

	/**
	 * Sets the order date hour formated.
	 *
	 * @param orderDateHourFormated the new order date hour formated
	 */
	public void setOrderDateHourFormated(String orderDateHourFormated) {
		this.orderDateHourFormated = orderDateHourFormated;
	}

	/**
	 * Gets the card expiration formated.
	 *
	 * @return the card expiration formated
	 */
	public String getCardExpirationFormated() {
		return cardExpirationFormated;
	}

	/**
	 * Sets the card expiration formated.
	 *
	 * @param cardExpirationFormated the new card expiration formated
	 */
	public void setCardExpirationFormated(String cardExpirationFormated) {
		this.cardExpirationFormated = cardExpirationFormated;
	}

	/**
	 * Gets the card type.
	 *
	 * @return the card type
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * Sets the card type.
	 *
	 * @param cardType the new card type
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the payer name.
	 *
	 * @return the payerName
	 */
	public String getPayerName() {
		return payerName;
	}

	/**
	 * Sets the payer name.
	 *
	 * @param payerName the payer name
	 */
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

}
