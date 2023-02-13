package com.danielly.jsf.cinema.services.admin;


import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;

import com.danielly.jsf.cinema.dao.interfaces.HallDao;
import com.danielly.jsf.cinema.dao.interfaces.SeatDao;
import com.danielly.jsf.cinema.dao.interfaces.ShowtimeDao;
import com.danielly.jsf.cinema.model.Hall;
import com.danielly.jsf.cinema.model.Seat;
import com.danielly.jsf.cinema.model.Showtime;
import com.danielly.jsf.cinema.services.ServiceView;


/**
 * The Class HallsAdmin provides methods to manage the halls in the GUI interface.
 * It handle the inputs, query the database and notify the user when needed.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


@Named("hall_data")
@RequestScoped
public class HallsAdmin implements Serializable {
	
	//Attributes
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The new hall. */
	@Inject
	private Hall newHall; 
	
	/** The seat. */
	@Inject
	private Seat seat; 
	
	/** The service view which is used to run methods that related to the GUI. */
    @Inject
    private ServiceView serviceView;
		
	/** The hall dao. */
	@Inject
    private HallDao hallDao;
	
	/** The seat dao. */
	@Inject
    private SeatDao seatDao;
			
	/** The showtime dao. */
	@Inject
    private ShowtimeDao showtimeDao;
	
	//Attributes for the new hall
	/** The total number of seats. */
	private int totalNumberOfSeats;
	
	/** The number of rows. */
	private int numberOfRows;
	
	/** The seats per row. */
	private int seatsPerRow;
	
	/** The new hall id. */
	private int newHallId=0;
	
	//Input
	/** The hall name which will contain the input. */
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Please use A-Z and 0-9 for the name of the hall.")   
	private String hallName;
	
	/** The total number of seats as a string which will contain the input. */
	@Pattern(regexp = "^[1-9]+[0-9]*$", message = "The total number of tickets cannot be 0 or negative number.")   
	private String strTotalNumberOfSeats;
	
	/** The number of rows as a string which will contain the input. */
	@Pattern(regexp = "^[1-9]+[0-9]*$", message = "The number of rows cannot be 0 or negative number.")   
	private String strNumberOfRows;
	
	/** The number of seats per row as a string which will contain the input. */
	@Pattern(regexp = "^[1-9]+[0-9]*$", message = "The number of seats per row cannot be 0 or negative number.")   
	private String strSeatsPerRow;
	
	//Lists
	/** The halls list will be used to check if a hall with the same name already existed. */
	private List<Hall> hallsList;
	
	/** The showtimes list will be used to check if a hall is in used in one or more showtime.
	 * This check is being done before deletion of the hall */
	private List<Showtime> showtimesList;	
	
	//booleans for checks creation and deletion of hall and seats 
	/** The hall success. */
	private boolean hallCreateSuccess = false;
	
	/** The hall delete success. */
	private boolean hallDeleteSuccess = false;
	
	/** The seats success. */
	private boolean seatsCreateSuccess = false;
	
	/** The seats success. */
	private boolean seatsDeleteSuccess = false;
	
	/** The Constant NO_ORDER_ID will be used to check if a valid order id received by the query of the database. */
	private static final int NO_ORDER_ID = 0;
	
	//Messages that will be used to notify the user
	/** The Constant MESSAGE_HALL_EXISTED_IN_SHOWTIME. */
	private static final String MESSAGE_HALL_EXISTED_IN_SHOWTIME = "This hall is being used in one or more showtimes.";
	
	/** The Constant MESSAGE_HALL_NAME_EXISTED. */
	private static final String MESSAGE_HALL_NAME_EXISTED = "Hall with that name already exists.";
	
	/** The Constant MESSAGE_HALL_NAME_EXISTED. */
	private static final String MESSAGE_HALL_CREATION_FAILURE = "There was a problem to create the hall.";
	
	/** The Constant MESSAGE_HALL_NAME_EXISTED. */
	private static final String MESSAGE_HALL_DELETION_FAILURE = "There was a problem to delete the hall.";
	
	/** The Constant MESSAGE_TOTAL_NUMBER_SEATS_SUM_ISSUE. */
	private static final String MESSAGE_TOTAL_NUMBER_SEATS_SUM_ISSUE = "The total number of seats should consist of the number of rows and the number of seats per row.";
	
	/** The Constant MESSAGE_SEATS_CREATION_FOR_HALL_FAILURE. */
	private static final String MESSAGE_SEATS_CREATION_FOR_HALL_FAILURE = "There was a problem to create the seats for this hall.";
	
	/** The Constant MESSAGE_SEATS_DELETION_FOR_HALL_FAILURE. */
	private static final String MESSAGE_SEATS_DELETION_FOR_HALL_FAILURE = "There was a problem to delete the seats for this hall.";
		
	
	//Initialize
    /**
     * Initialize method which is loaded before the class is put to service
     * It loads the lists of halls and showtimes which will be used in the creation and the deletion of a hall.
     */
	@PostConstruct
	public void initialize() {
		hallsList = hallDao.getAllHalls();
		showtimesList = showtimeDao.getListOfShowtimes();
	}

		
	//Class Methods
	/**
	 * Adds the a new hall.
	 */
	public void addHall()  {
		totalNumberOfSeats= Integer.parseInt(strTotalNumberOfSeats);
		numberOfRows= Integer.parseInt(strNumberOfRows);
		seatsPerRow= Integer.parseInt(strSeatsPerRow);
		
		if (checkIfHallExisted()) {			
			serviceView.sendMessageToView(MESSAGE_HALL_NAME_EXISTED);
		}
		else if (!checkTotalNumberOfSeats()) { // check if the total is based on numberOfRows * seatsPerRow
			serviceView.sendMessageToView(MESSAGE_TOTAL_NUMBER_SEATS_SUM_ISSUE);
		} 
		else {
			createNewHallObject();
			newHallId = hallDao.insertHallAndReturnId(newHall);
			hallCreateSuccess = (newHallId != NO_ORDER_ID);

			if (hallCreateSuccess) {
				// create the seats for the new hall
				seatsCreateSuccess = createSeatsForHall();

				if (seatsCreateSuccess) {
					serviceView.reloadPage();
				} else {
					serviceView.sendMessageToView(MESSAGE_SEATS_CREATION_FOR_HALL_FAILURE);
				}
			} 
			else 
			{
				serviceView.sendMessageToView(MESSAGE_HALL_CREATION_FAILURE);
			}
		}
	}
	
	
	/**
	 * Creates the new hall object.
	 */
	private void createNewHallObject() {
		newHall.setHallName(hallName);
		newHall.setTotalNumberOfSeats(totalNumberOfSeats);
		newHall.setNumberOfRows(numberOfRows);
		newHall.setSeatsPerRow(seatsPerRow);
	}
	
	/**
	 * Creates the seats for a hall.
	 *
	 * @return true, if successful
	 */
	private boolean createSeatsForHall() {
		boolean isSeatsCreatedForHall=true;
		
		for (int i=1; i<newHall.getNumberOfRows()+1;i++)       // i= row 
			for (int j=1; j<newHall.getSeatsPerRow()+1;j++) {   // j= seat number
				seat.setHallId(this.newHallId); //the new hall Id we received from insertHallAndReturnId
				seat.setRowNumber(i);
				seat.setSeatNumber(j);
				
				isSeatsCreatedForHall= seatDao.insertSeat(seat);
				
				if (!isSeatsCreatedForHall) {					
					//rollback
					seatDao.deleteAllSeatOfAHall(newHall.getHallId());
					return isSeatsCreatedForHall;
				}
			}
		return isSeatsCreatedForHall;
	}
	
	/**
	 * Delete hall and the seats for that hall.
	 * This method check before the delete that the hall is not being used by one or more showtimes.
	 * @param hall the hall
	 */
	public void deleteHall(Hall hall){
		if(!checkIfHallIsUsedInShowtime(hall)) { 
			seatsDeleteSuccess=seatDao.deleteAllSeatOfAHall(hall.getHallId());
			if (seatsDeleteSuccess)		
			{   
				hallDeleteSuccess=hallDao.deleteHall(hall.getHallId()); 
				if (hallDeleteSuccess) {
					serviceView.reloadPage();
				}
				else {
					serviceView.sendMessageToView(MESSAGE_HALL_DELETION_FAILURE);
				}
			}
			else {
				serviceView.sendMessageToView(MESSAGE_SEATS_DELETION_FOR_HALL_FAILURE);
			}
		}
		else {
			serviceView.sendMessageToView(MESSAGE_HALL_EXISTED_IN_SHOWTIME);
		}
	}
		
	
	/**
	 * Check if hall is used in showtime.
	 *
	 * @param hall the hall
	 * @return true, if successful
	 */
	private boolean checkIfHallIsUsedInShowtime(Hall hall) {
		boolean result=false;
		for(Showtime showtime: showtimesList)
			if(showtime.getHallId()==hall.getHallId())
			{
				result=true;
				break;
			}
		return result;
	}
	
	/**
	 * Check total number of seats.
	 *
	 * @return true, if successful
	 */
	private boolean checkTotalNumberOfSeats() {
		return (totalNumberOfSeats==numberOfRows*seatsPerRow);
	}
	
	/**
	 * Check if hall existed.
	 *
	 * @return true, if successful
	 */
	private boolean checkIfHallExisted() {
		boolean result = false;
		for (Hall hall : hallsList) {
			if (hall.getHallName().equals(this.hallName)) {
				result = true;
				return result;
			}
		}
		return result;
	}	
		
	
	//Getters And Setters
	/**
	 * Gets the new hall.
	 *
	 * @return the new hall
	 */
	public Hall getNewHall() {
		return newHall;
	}

	/**
	 * Sets the new hall.
	 *
	 * @param newHall the new new hall
	 */
	public void setNewHall(Hall newHall) {
		this.newHall = newHall;
	}

	/**
	 * Gets the halls list.
	 *
	 * @return the halls list
	 */
	public List<Hall> getHallsList() {
		return hallsList;
	}


	/**
	 * Sets the halls list.
	 *
	 * @param hallsList the new halls list
	 */
	public void setHallsList(List<Hall> hallsList) {
		this.hallsList = hallsList;
	}


	/**
	 * Gets the hall name.
	 *
	 * @return the hall name
	 */
	public String getHallName() {
		return hallName;
	}


	/**
	 * Sets the hall name.
	 *
	 * @param hallName the new hall name
	 */
	public void setHallName(String hallName) {
		this.hallName = hallName;
	}


	/**
	 * Gets the total number of seats.
	 *
	 * @return the total number of seats
	 */
	public int getTotalNumberOfSeats() {
		return totalNumberOfSeats;
	}


	/**
	 * Sets the total number of seats.
	 *
	 * @param totalNumberOfSeats the new total number of seats
	 */
	public void setTotalNumberOfSeats(int totalNumberOfSeats) {
		this.totalNumberOfSeats = totalNumberOfSeats;
	}


	/**
	 * Gets the number of rows.
	 *
	 * @return the number of rows
	 */
	public int getNumberOfRows() {
		return numberOfRows;
	}


	/**
	 * Sets the number of rows.
	 *
	 * @param numberOfRows the new number of rows
	 */
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}


	/**
	 * Gets the seats per row.
	 *
	 * @return the seats per row
	 */
	public int getSeatsPerRow() {
		return seatsPerRow;
	}


	/**
	 * Sets the seats per row.
	 *
	 * @param seatsPerRow the new seats per row
	 */
	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}


	/**
	 * Gets the String total number of seats .
	 *
	 * @return the String total number of seats
	 */
	public String getStrTotalNumberOfSeats() {
		return strTotalNumberOfSeats;
	}


	/**
	 * Sets the total number of seats as a String.
	 *
	 * @param strTotalNumberOfSeats the new total number of seats as a String
	 */
	public void setStrTotalNumberOfSeats(String strTotalNumberOfSeats) {
		this.strTotalNumberOfSeats = strTotalNumberOfSeats;
	}


	/**
	 * Gets the String number of rows.
	 *
	 * @return the String number of rows
	 */
	public String getStrNumberOfRows() {
		return strNumberOfRows;
	}


	/**
	 * Sets the number of rows as a String.
	 *
	 * @param strNumberOfRows the new number of rows as a String
	 */
	public void setStrNumberOfRows(String strNumberOfRows) {
		this.strNumberOfRows = strNumberOfRows;
	}


	/**
	 * Gets the String seats per row.
	 *
	 * @return the String seats per row
	 */
	public String getStrSeatsPerRow() {
		return strSeatsPerRow;
	}


	/**
	 * Sets the seats per row as a String.
	 *
	 * @param strSeatsPerRow the new seats per row as a String
	 */
	public void setStrSeatsPerRow(String strSeatsPerRow) {
		this.strSeatsPerRow = strSeatsPerRow;
	}	
}
