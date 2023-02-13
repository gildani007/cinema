package com.danielly.jsf.cinema.services.admin;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;

import com.danielly.jsf.cinema.dao.interfaces.HallDao;
import com.danielly.jsf.cinema.dao.interfaces.MovieDao;
import com.danielly.jsf.cinema.dao.interfaces.PriceDao;
import com.danielly.jsf.cinema.dao.interfaces.SearchDao;
import com.danielly.jsf.cinema.dao.interfaces.ShowtimeDao;
import com.danielly.jsf.cinema.dao.interfaces.ShowtimeSeatsDao;
import com.danielly.jsf.cinema.model.Hall;
import com.danielly.jsf.cinema.model.Movie;
import com.danielly.jsf.cinema.model.Price;
import com.danielly.jsf.cinema.model.Showtime;
import com.danielly.jsf.cinema.services.ServiceView;

/**
 * The Class ShowtimesAdmin provides methods to manage the showtimes in the GUI interface.
 * It handle the inputs, query the database and notify the user when needed.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */

	@Named("showtime_data")
	@RequestScoped
	public class ShowtimesAdmin implements Serializable{	  
		
		//Attributes
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The showtime dao. */
		@Inject
	    private ShowtimeDao showtimeDao;
	    	  
		/** The new showtime. */
		@Inject
		private Showtime newShowtime;
		
		/** The showtime seats dao. */
		@Inject
		private ShowtimeSeatsDao showtimeSeatsDao;
		
		/** The search dao. */
		@Inject
		private SearchDao searchDao;
		
		/** The movie dao. */
		@Inject
		private MovieDao movieDao;
		
		/** The price dao. */
		@Inject
		private PriceDao priceDao;
		
		/** The hall dao. */
		@Inject
		private HallDao hallDao;
		
		/** The hall. */
		@Inject
		private Hall hall;
		
		/** The movie. */
		@Inject 
		private Movie movie;
			
		/** The service view which is used to run methods that related to the GUI. */
	    @Inject
	    private ServiceView serviceView;
		
	    /** The showtimes. */
    	private List<Showtime> showtimes;
	    
    	/** The showtime. */
    	private Showtime showtime;
	    
    	/** The showtime date hour. */
    	private LocalDateTime showtimeDateHour;
	       
	    /** The movie id. */
    	private int movieId;
	    
    	/** The price id. */
    	private int priceId;
		
		/** The hall id. */
		private int hallId;
		
		/** The available tickets. */
		private int availableTickets;
		
		/** The total tickets. */
		private int totalTickets;
		
		/** The total tickets will hold the input of the user. */
		@Pattern(regexp = "^[1-9]+[0-9]*$", message = "The number of tickets cannot be 0 or negative number.") 
		private String strTotalTickets;
		
		/** The movie id as a String. */
		private String strMovieId;
		
		/** The price id as a String. */
		private String strPriceId;
		
		/** The hall id as a String. */
		private String strHallId;	
	
		/** The showtime hour as a String. */
		private String strShowtimeHour;
		
		/** The showtime date as a String. */
		private String strShowtimeDate;
	    
    	/** The movies list. */
    	private List<Movie> moviesList;
	    
    	/** The prices list. */
    	private List<Price> pricesList;
	    
    	/** The halls list. */
    	private List<Hall> hallsList;
	    
    	
    	//Selections
	    /** The movies map selection. */
    	private HashMap<String, String> moviesMapSelection;
	    
    	/** The prices map selection. */
    	private HashMap<String, String> pricesMapSelection;
	    
    	/** The halls map selection. */
    	private HashMap<String, String> hallsMapSelection;
	    
	   
    	/** The Constant NO_SHOWTIME_ID will be used to check if a valid showtime id received by the query of the database. */
    	private static final int NO_SHOWTIME_ID = 0;
		
    	//Messages that will be used to notify the user
		/** The Constant MESSAGE_TOTAL_TICKETS_HIGHER_AVAILABLE_SEATS. */
		private static final String MESSAGE_TOTAL_TICKETS_HIGHER_AVAILABLE_SEATS = "The total tickets number cannot be higher than the available seats in the hall.";
		
		/** The Constant MESSAGE_HALL_OCCUPIED_THIS_HOUR. */
		private static final String MESSAGE_HALL_OCCUPIED_THIS_HOUR = "This is hall is already occupied at this hour.";
		
		/** The Constant MESSAGE_SHOWTIME_CREATION_FAILURE. */
		private static final String MESSAGE_SHOWTIME_CREATION_FAILURE = "There was a problem to add the showtime.";
		
		/** The Constant MESSAGE_SHOWTIME_SEATS_CREATION_FAILURE. */
		private static final String MESSAGE_SHOWTIME_SEATS_CREATION_FAILURE = "There was a problem to create the seats for this showtime.";
		
		/** The Constant MESSAGE_SHOWTIME_DELETION_FAILURE. */
		private static final String MESSAGE_SHOWTIME_DELETION_FAILURE = "There was a problem to delete the showtime.";
		
		/** The Constant MESSAGE_MOVIE_NOT_FOUND. */
		private static final String MESSAGE_MOVIE_NOT_FOUND = "Cannot find a movie with this id";
		
		/** The Constant MESSAGE_PRICE_NOT_FOUND. */
		private static final String MESSAGE_PRICE_NOT_FOUND = "Cannot find a price with this id";
		
		/** The Constant MESSAGE_HALL_NOT_FOUND. */
		private static final String MESSAGE_HALL_NOT_FOUND = "Cannot find a hall with this id";
		
		/** The Constant MESSAGE_DATE_OR_HOUR_INVALID. */
		private static final String MESSAGE_DATE_OR_HOUR_INVALID = "The date and the hour cannot be empty";
	    
		 /** The Constant HIGHER_IS_INDICATOR_FOR_ANY_DATE_INPUT is used to check if there any date input received from the GUI.*/
	    private final static int HIGHER_IS_INDICATOR_FOR_ANY_DATE_INPUT = 2;
	   
		
		//Initialize
	    /**
	     * Initialize method which is loaded before the class is put to service
	     * It loads the all the lists and selections which will be used in the creation and the deletion of a movie.
	     */
    	@PostConstruct
	    public void initialize() {
	    	showtimes = showtimeDao.getListOfCurrentShowtimes();
	    	moviesList = movieDao.getListOfReleasedMovies();
	    	pricesList = priceDao.getAllPrices();
	    	hallsList = hallDao.getAllHalls();
	    	moviesMapSelection = getSelectionMovies();
	    	pricesMapSelection = getSelectionPrices();
	    	hallsMapSelection = getSelectionHalls();
	    
	    }

	    
	    
	    //Class Methods   
	    /**
    	 * Adds the showtime.
    	 */
    	public void addShowtime() {
	    	int newShowtimeId = 0;
	    	totalTickets = Integer.parseInt(this.strTotalTickets);
	    	
	    	if (strShowtimeHour.length()<HIGHER_IS_INDICATOR_FOR_ANY_DATE_INPUT || strShowtimeDate.length()<HIGHER_IS_INDICATOR_FOR_ANY_DATE_INPUT) {
	    		serviceView.sendMessageToView(MESSAGE_DATE_OR_HOUR_INVALID);
	    	}
	    	else {
		    	//combine the hour and the date to LocalDateTime
		    	combineToLocalDateTime();
		    		    	
		    	hall = hallDao.getHall(this.hallId);
		    	if (totalTickets>hall.getTotalNumberOfSeats()) {	 //check that the total tickets number is not higher than the number of the seats in the hall
		    		serviceView.sendMessageToView(MESSAGE_TOTAL_TICKETS_HIGHER_AVAILABLE_SEATS);	    	
		    	}	 
		    	else if(checkIfHallIsOccupied()){
		    		serviceView.sendMessageToView(MESSAGE_HALL_OCCUPIED_THIS_HOUR);
		    	}
		    	else {
		    		setTheNewShowtime();
			    	newShowtimeId = showtimeDao.insertShowtimeAndReturnId(newShowtime); 
			    	if (newShowtimeId==NO_SHOWTIME_ID) {
			    		serviceView.sendMessageToView(MESSAGE_SHOWTIME_CREATION_FAILURE);
			    	}
			    	else
			    	{
			    		if (!showtimeSeatsDao.insertShowtimeSeats(newShowtimeId)) {
			    			serviceView.sendMessageToView(MESSAGE_SHOWTIME_SEATS_CREATION_FAILURE);
			    		}
			    		else
			    		{
			    			serviceView.reloadPage();
			    		}
			    	}	    	
		    	}
	    	}
	    }
	    
	    /**
    	 * Combine to local date time.
    	 */
    	private void combineToLocalDateTime() {
	    	LocalDate showtimeDate = searchDao.convertStringToDate(strShowtimeDate);
			LocalTime showtimeHour = searchDao.convertStringToTime(strShowtimeHour);
	    	showtimeDateHour = LocalDateTime.of(showtimeDate,showtimeHour);
	    	
	    }
	    
	    /**
    	 * Sets the the new showtime.
    	 */
    	private void setTheNewShowtime() {
	    	this.newShowtime.setShowtimeDateHour(showtimeDateHour);
	    	this.newShowtime.setTotalTickets(totalTickets);
	    	this.newShowtime.setMovieId(movieId);
	    	this.newShowtime.setPriceId(priceId);
	    	this.newShowtime.setHallId(hallId);
	    	this.newShowtime.setAvailableTickets(totalTickets); //the number will be the total tickets as the starting point
	    }
	    
	    
	    /**
    	 * Check if hall is occupied.
    	 *
    	 * @return true, if successful
    	 */
    	private boolean checkIfHallIsOccupied() {
	    	for(Showtime showtime: showtimes) {
	    		movie = movieDao.getMovie(showtime.getMovieId());
	    		if(showtime.getHallId()==hallId) {
	    			if	((showtime.getShowtimeDateHour().isEqual(showtimeDateHour) 
	    				|| ((showtimeDateHour.isAfter(showtime.getShowtimeDateHour()) 
	    				&& (showtimeDateHour.isBefore(showtime.getShowtimeDateHour().plusMinutes(movie.getLength()))))))) //check with the length of the movie
	    					{
			    			return true;
	    					}
	    		}
	    	}
	    	return false;
	    }
	    
	    
		/**
		 * Delete showtime.
		 * This method only set this record as deleted in the database
		 * @param showtime the showtime
		 */
		public void deleteShowtime(Showtime showtime) {
			boolean deleteShowtimeSuccess;
			showtime.setShowtime_deleted(true);
			deleteShowtimeSuccess = showtimeDao.updateShowtime(showtime);
			if (!deleteShowtimeSuccess) {
				serviceView.sendMessageToView(MESSAGE_SHOWTIME_DELETION_FAILURE);
			}
			else
			{
				serviceView.reloadPage();
			}
	    }
	    
	    
	    
	   /**
   	 * Gets the movie name from id.
   	 *
   	 * @param movieId the movie id
   	 * @return the movie name from id
   	 */
   	public String getMovieNameFromId(int movieId) {
		   String strResult = "";
		   for(Movie movie : moviesList) {
			   if (movie.getMovieId()==movieId) {
				   strResult =movie.getName();
			   	   return strResult;
			   }
		   }
		   serviceView.sendMessageToView(MESSAGE_MOVIE_NOT_FOUND); 
		   return strResult;
	   }

	   
	   
	   /**
   	 * Gets the price nis from id.
   	 *
   	 * @param priceId the price id
   	 * @return the price nis from id
   	 */
   	public String getPriceNisFromId(int priceId) {
		   String strResult = "";
		   for(Price price : pricesList) {
			   if (price.getPriceId()==priceId) {
				   strResult = Integer.toString(price.getPriceNis());
			   	   return strResult;
			   }
		   }
		   serviceView.sendMessageToView(MESSAGE_PRICE_NOT_FOUND);
		   return strResult;
	   }

	   
	   
	   
	   /**
   	 * Gets the hall name from id.
   	 *
   	 * @param hallId the hall id
   	 * @return the hall name from id
   	 */
   	public String getHallNameFromId(int hallId) {
		   String strResult = "";
		   for(Hall hall : hallsList) {
			   if (hall.getHallId()==hallId) {
				   strResult = hall.getHallName();
			   	   return strResult;
			   }
		   }
		   serviceView.sendMessageToView(MESSAGE_HALL_NOT_FOUND);
		   return strResult;
	   }
	    

	   
	   
	   
		/**
		 * Gets the selection movies.
		 *
		 * @return the selection movies
		 */
		//select movie to add new showtime - used in the initialize
		public HashMap<String,String> getSelectionMovies(){  
		    HashMap<String,String> selectionMoviesResult = new HashMap<String,String>();	
			  
			  for (Movie movie:moviesList) {
				  selectionMoviesResult.put(movie.getName(), Integer.toString(movie.getMovieId()));      //label,value
			  }
		    return selectionMoviesResult;		   
		}
		
		
		
		/**
		 * Gets the selection prices.
		 *
		 * @return the selection prices
		 */
		//select price category to add new showtime - used in the initialize
		public HashMap<String,String> getSelectionPrices(){  
		    HashMap<String,String> selectionPricesResult = new HashMap<String,String>();	
			  
			  for (Price price:pricesList) {
				  selectionPricesResult.put(price.getCategory(), Integer.toString(price.getPriceId()));      //label,value
			  }
		    return selectionPricesResult;		   
		}
		
				
		
		/**
		 * Gets the selection halls.
		 *
		 * @return the selection halls
		 */
		//select movie to add new showtime - used in the initialize
		public HashMap<String,String> getSelectionHalls(){  
		    HashMap<String,String> selectionHallsResult = new HashMap<String,String>();	
			  
			  for (Hall hall:hallsList) {
				  selectionHallsResult.put(hall.getHallName()+" - Total Seats: "+Integer.toString(hall.getTotalNumberOfSeats()), Integer.toString(hall.getHallId()));      //label,value
			  }
		    return selectionHallsResult;		   
		}    
	    
		
		//Getters And Setters
	    /**
    	 * Gets the showtime.
    	 *
    	 * @return the showtime
    	 */
    	public Showtime getShowtime() {
			return showtime;
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
		 * Gets the showtime date hour.
		 *
		 * @return the showtime date hour
		 */
		public LocalDateTime getShowtimeDateHour() {
			return showtimeDateHour;
		}

		/**
		 * Sets the showtime date hour.
		 *
		 * @param showtimeDateHour the new showtime date hour
		 */
		public void setShowtimeDateHour(LocalDateTime showtimeDateHour) {
			this.showtimeDateHour = showtimeDateHour;
		}

		/**
		 * Gets the total tickets.
		 *
		 * @return the total tickets
		 */
		public int getTotalTickets() {
			return totalTickets;
		}

		/**
		 * Sets the total tickets.
		 *
		 * @param totalTickets the new total tickets
		 */
		public void setTotalTickets(int totalTickets) {
			this.totalTickets = totalTickets;
		}

		/**
		 * Gets the movie id.
		 *
		 * @return the movie id
		 */
		public int getMovieId() {
			return movieId;
		}

		/**
		 * Sets the movie id.
		 *
		 * @param movieId the new movie id
		 */
		public void setMovieId(int movieId) {
			this.movieId = movieId;
		}

		/**
		 * Gets the price id.
		 *
		 * @return the price id
		 */
		public int getPriceId() {
			return priceId;
		}

		/**
		 * Sets the price id.
		 *
		 * @param priceId the new price id
		 */
		public void setPriceId(int priceId) {
			this.priceId = priceId;
		}

		/**
		 * Gets the hall id.
		 *
		 * @return the hall id
		 */
		public int getHallId() {
			return hallId;
		}

		/**
		 * Sets the hall id.
		 *
		 * @param hallId the new hall id
		 */
		public void setHallId(int hallId) {
			this.hallId = hallId;
		}

		/**
		 * Gets the available tickets.
		 *
		 * @return the available tickets
		 */
		public int getAvailableTickets() {
			return availableTickets;
		}

		/**
		 * Sets the available tickets.
		 *
		 * @param availableTickets the new available tickets
		 */
		public void setAvailableTickets(int availableTickets) {
			this.availableTickets = availableTickets;
		}

		/**
		 * Gets the showtime hour.
		 *
		 * @return the showtime hour
		 */
		public String getShowtimeHour() {
			return strShowtimeHour;
		}

		/**
		 * Sets the showtime hour.
		 *
		 * @param showtimeHour the new showtime hour
		 */
		public void setShowtimeHour(String showtimeHour) {
			this.strShowtimeHour = showtimeHour;
		}

		/**
		 * Gets the showtime date.
		 *
		 * @return the showtime date
		 */
		public String getShowtimeDate() {
			return strShowtimeDate;
		}

		/**
		 * Sets the showtime date.
		 *
		 * @param showtimeDate the new showtime date
		 */
		public void setShowtimeDate(String showtimeDate) {
			this.strShowtimeDate = showtimeDate;
		}

		/**
		 * Gets the movie id as a String.
		 *
		 * @return the movie id as a String
		 */
		public String getStrMovieId() {
			return strMovieId;
		}

		/**
		 * Sets the movie id String to the int movie id.
		 *
		 * @param strMovieId the movie id as a String
		 */
		//set the int MovieId
		public void setStrMovieId(String strMovieId) {
			this.setMovieId(Integer.parseInt(strMovieId));
			this.strMovieId = strMovieId;
		}
		
		/**
		 * Gets the price id as a String.
		 *
		 * @return the price id as a String
		 */
		public String getStrPriceId() {
			return strPriceId;
		}
		
		/**
		 * Sets the price id String to the int price id.
		 *
		 * @param strPriceId the price id as a String
		 */
		//set the int PriceId
		public void setStrPriceId(String strPriceId) {
			this.setPriceId(Integer.parseInt(strPriceId));
			this.strPriceId = strPriceId;
		}

		/**
		 * Gets the hall id as a String.
		 *
		 * @return the hall id as a String
		 */
		public String getStrHallId() {
			return strHallId;
		}
		
		/**
		 * Sets the hall id String to the int hall id.
		 *
		 * @param strHallId the hall id as a String
		 */
		//set the int HallId
		public void setStrHallId(String strHallId) {
			this.setHallId(Integer.parseInt(strHallId));
			this.strHallId = strHallId;
		}
	
		/**
		 * Gets the new showtime.
		 *
		 * @return the new showtime
		 */
		public Showtime getNewShowtime() {
			return newShowtime;
		}

		/**
		 * Sets the new showtime.
		 *
		 * @param newShowtime the new new showtime
		 */
		public void setNewShowtime(Showtime newShowtime) {
			this.newShowtime = newShowtime;
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
		 * Gets the showtime hour as a String.
		 *
		 * @return the showtime hour as a String
		 */
		public String getStrShowtimeHour() {
			return strShowtimeHour;
		}

		/**
		 * Sets the showtime hour as a String.
		 *
		 * @param strShowtimeHour the new showtime hour as a String
		 */
		public void setStrShowtimeHour(String strShowtimeHour) {
			this.strShowtimeHour = strShowtimeHour;
		}

		/**
		 * Gets the showtime date as a String.
		 *
		 * @return the showtime date as a String
		 */
		public String getStrShowtimeDate() {
			return strShowtimeDate;
		}

		/**
		 * Sets the showtime date as a String.
		 *
		 * @param strShowtimeDate the new showtime date as a String
		 */
		public void setStrShowtimeDate(String strShowtimeDate) {
			this.strShowtimeDate = strShowtimeDate;
		}

		/**
		 * Gets the movies list.
		 *
		 * @return the movies list
		 */
		public List<Movie> getMoviesList() {
			return moviesList;
		}

		/**
		 * Sets the movies list.
		 *
		 * @param moviesList the new movies list
		 */
		public void setMoviesList(List<Movie> moviesList) {
			this.moviesList = moviesList;
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
		 * Gets the movies map selection.
		 *
		 * @return the movies map selection
		 */
		public HashMap<String, String> getMoviesMapSelection() {
			return moviesMapSelection;
		}

		/**
		 * Sets the movies map selection.
		 *
		 * @param moviesMapSelection the movies map selection
		 */
		public void setMoviesMapSelection(HashMap<String, String> moviesMapSelection) {
			this.moviesMapSelection = moviesMapSelection;
		}

		/**
		 * Gets the prices map selection.
		 *
		 * @return the prices map selection
		 */
		public HashMap<String, String> getPricesMapSelection() {
			return pricesMapSelection;
		}

		/**
		 * Sets the prices map selection.
		 *
		 * @param pricesMapSelection the prices map selection
		 */
		public void setPricesMapSelection(HashMap<String, String> pricesMapSelection) {
			this.pricesMapSelection = pricesMapSelection;
		}

		/**
		 * Gets the halls map selection.
		 *
		 * @return the halls map selection
		 */
		public HashMap<String, String> getHallsMapSelection() {
			return hallsMapSelection;
		}

		/**
		 * Sets the halls map selection.
		 *
		 * @param hallsMapSelection the halls map selection
		 */
		public void setHallsMapSelection(HashMap<String, String> hallsMapSelection) {
			this.hallsMapSelection = hallsMapSelection;
		}

		/**
		 * Gets the showtimes.
		 *
		 * @return the showtimes
		 */
		public List<Showtime> getShowtimes(){
	        return showtimes;
	    }
	    
	    /**
    	 * Sets the showtimes.
    	 *
    	 * @param showtimes the new showtimes
    	 */
    	public void setShowtimes(List<Showtime> showtimes) {
			this.showtimes = showtimes;
		}


		/**
		 * Gets the total tickets as a String.
		 *
		 * @return the total tickets as a String
		 */
		public String getStrTotalTickets() {
			return strTotalTickets;
		}

		/**
		 * Sets the total tickets as a String.
		 *
		 * @param strTotalTickets the new total tickets as a String
		 */
		public void setStrTotalTickets(String strTotalTickets) {
			this.strTotalTickets = strTotalTickets;
		}
	    
	}
