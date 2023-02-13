package com.danielly.jsf.cinema.services.admin;


import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;

import com.danielly.jsf.cinema.dao.interfaces.PriceDao;
import com.danielly.jsf.cinema.dao.interfaces.ShowtimeDao;
import com.danielly.jsf.cinema.model.Price;
import com.danielly.jsf.cinema.model.Showtime;
import com.danielly.jsf.cinema.services.ServiceView;


/**
 * The Class PricesAdmin provides methods to manage the prices in the GUI interface.
 * It handle the inputs, query the database and notify the user when needed.
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 * 
 */

@Named("price_data")
@RequestScoped
public class PricesAdmin implements Serializable {

	//Attributes
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The price dao. */
	@Inject
	private PriceDao priceDao;

	/** The new price. */
	@Inject
	private Price newPrice;
	
	/** The showtime dao. */
	@Inject
    private ShowtimeDao showtimeDao;
	
	/** The service view which is used to run methods that related to the GUI. */
    @Inject
    private ServiceView serviceView;

    /** The price category. */
    @Pattern(regexp = "[a-zA-Z]+", message = "Please enter a valid category - A-Z without numbers.")
	private String priceCategory;
       
    /** The str price nis. */
    @Pattern(regexp = "^[1-9]+[0-9]*$", message = "The price cannot be 0 or negative number.")    
    private String strPriceNis;
    
    /** The price nis. */
    private int priceNis;	
	
    //Lists
	/** The prices list. */
	private List<Price> pricesList;
	
	/** The showtimes list. */
	private List<Showtime> showtimesList;
	
	//Messages that will be used to notify the user
	/** The Constant MESSAGE_CATEGORY_NAME_ALREADY_EXISTS. */
	private static final String MESSAGE_CATEGORY_NAME_ALREADY_EXISTS ="Category name already exists.";
	
	/** The Constant MESSAGE_PRICE_IS_BEING_USED_BY_SHOWTIME. */
	private static final String MESSAGE_PRICE_IS_BEING_USED_BY_SHOWTIME ="This price is being used in one or more showtimes.";
	
	/** The Constant MESSAGE_PRICE_NAME_EXISTED. */
	private static final String MESSAGE_PRICE_CREATION_FAILURE = "There was a problem to create the price.";
	
	/** The Constant MESSAGE_PRICE_NAME_EXISTED. */
	private static final String MESSAGE_PRICE_DELETION_FAILURE = "There was a problem to delete the price.";
	
	
	
	//Initialize
    /**
     * Initialize method which is loaded before the class is put to service
     * It loads the lists of price and showtimes which will be used in the creation and the deletion of a price.
     */
	@PostConstruct
	public void initialize() {
		pricesList = priceDao.getAllPrices();
		showtimesList = showtimeDao.getListOfShowtimes();
	}

	
	//Class Methods
	/**
	 * Adds the price.
	 */
	public void addPrice(){
		boolean createPriceSuccess=false;
		if (checkIfCategoryExisted()) {
			serviceView.sendMessageToView(MESSAGE_CATEGORY_NAME_ALREADY_EXISTS);
		} 
		else { //create the new price object
			priceNis=Integer.parseInt(this.strPriceNis);
			newPrice.setCategory(this.priceCategory);
			newPrice.setPriceNis(this.priceNis);
			
			//insert the database
			createPriceSuccess=priceDao.insertPrice(newPrice);
			
			if (createPriceSuccess)
			{  
				serviceView.reloadPage();
				
			}
			else {
				serviceView.sendMessageToView(MESSAGE_PRICE_CREATION_FAILURE);
			}
		}
	}
	
	/**
	 * Delete price.
	 *
	 * @param price the price
	 */
	public void deletePrice(Price price){
		boolean deletePriceSuccess=false;
		if(!checkIfPriceIsUsedInShowtime(price)) { //check if the price is being used in a showtime
			deletePriceSuccess=priceDao.deletePrice(price.getPriceId()); //delete from database
			if (deletePriceSuccess)
			{   
				serviceView.reloadPage();
			}
			else {
				serviceView.sendMessageToView(MESSAGE_PRICE_DELETION_FAILURE);
			}
		}
		else {
			serviceView.sendMessageToView(MESSAGE_PRICE_IS_BEING_USED_BY_SHOWTIME);
		}
	}
	

	/**
	 * Check if price is used in showtime.
	 *
	 * @param price the price
	 * @return true, if successful
	 */
	private boolean checkIfPriceIsUsedInShowtime(Price price) {
		for(Showtime showtime: showtimesList)
			if(showtime.getPriceId()==price.getPriceId())
			{
				return true;
			}
		return false;
	}
	
	/**
	 * Check if category existed.
	 *
	 * @return true, if successful
	 */
	private boolean checkIfCategoryExisted() {
		for (Price price : pricesList) {
			if (price.getCategory().equals(this.priceCategory)) {
				return true;
			}
		}
		return false;
	}	
		
	
	
	//Getters And Setters
	/**
	 * Gets the price nis as a String.
	 *
	 * @return the price nis as a String
	 */
	public String getStrPriceNis() {
		return strPriceNis;
	}

	/**
	 * Sets the price nis as a String.
	 *
	 * @param strPriceNis the new price nis as a String
	 */
	public void setStrPriceNis(String strPriceNis) {
		this.strPriceNis = strPriceNis;
	}	

	/**
	 * Gets the prices list.
	 *
	 * @return the prices list
	 */
	public List<Price> getPricesList() {
		return pricesList;
	}

	/**
	 * Sets the prices list.
	 *
	 * @param pricesList the new prices list
	 */
	public void setPricesList(List<Price> pricesList) {
		this.pricesList = pricesList;
	}

	/**
	 * Gets the price category.
	 *
	 * @return the price category
	 */
	public String getPriceCategory() {
		return priceCategory;
	}

	/**
	 * Sets the price category.
	 *
	 * @param priceCategory the new price category
	 */
	public void setPriceCategory(String priceCategory) {
		this.priceCategory = priceCategory;
	}

	/**
	 * Gets the price nis.
	 *
	 * @return the price nis
	 */
	public int getPriceNis() {
		return priceNis;
	}

	/**
	 * Sets the price nis.
	 *
	 * @param priceNis the new price nis
	 */
	public void setPriceNis(int priceNis) {
		this.priceNis = priceNis;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Price getPrice() {
		return newPrice;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(Price price) {
		this.newPrice = price;
	}

}
