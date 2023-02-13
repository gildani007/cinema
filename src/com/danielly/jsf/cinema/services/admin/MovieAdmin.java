package com.danielly.jsf.cinema.services.admin;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.danielly.jsf.cinema.dao.interfaces.MovieDao;
import com.danielly.jsf.cinema.dao.interfaces.ShowtimeDao;
import com.danielly.jsf.cinema.model.Movie;
import com.danielly.jsf.cinema.model.Showtime;
import com.danielly.jsf.cinema.model.enums.MpaaFilmRating;
import com.danielly.jsf.cinema.services.ServiceView;


/**
 * The Class MovieAdmin provides methods to manage the movies in the GUI interface.
 * It handle the inputs, query the database and notify the user when needed.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


@Named("movie_data")
@RequestScoped
public class MovieAdmin implements Serializable{	  

	//Attributes
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The showtime dao. */
	@Inject
    private ShowtimeDao showtimeDao;	    	  
	
	/** The movie dao. */
	@Inject
	private MovieDao movieDao;		
	
	/** The new movie. */
	@Inject 
	private Movie newMovie;
	
	/** The service view which is used to run methods that related to the GUI. */
    @Inject
    private ServiceView serviceView;

	//Attributes for the new movie and inputs
	/** The name. */
	@Size(min = 1, max = 30, message = "Please enter a movie name.")
	private String name;
	
	/** The description. */
	@Size(min = 10, max = 300, message = "Please enter the decription for the movie. The decription is too short.")
    private String description;
	
	/** The year. */
	@Pattern(regexp = "20\\d{2}", message = "Please enter a valid year.")
    private String strYear;  

	/** The length. */
	@Pattern(regexp = "^[1-9]+[0-9]*$", message = "The length cannot be 0 or negative number and must conatin only numbers.")
	private String strLength;
    
	/** The director. */
	@Size(min = 1, max = 30, message = "Please enter a director name.")
    private String director;
    
	/** The main actor. */
	@Size(min = 1, max = 30, message = "Please enter a actor name.")
    private String mainActor;
    
	/** The genre. */
	@Size(min = 1, max = 30, message = "Please enter a genre.")
    private String genre;
    
    /** The year. */
	private int year;
    
	/** The length. */
	private int length;
	
	/** The mpaa rating. */
	private String mpaaRating;
     
    /** The coming soon. */
	private boolean comingSoon;
    
	/** The image name. */
	private String imageName;
	
    //Lists
	/** The movies list. */
	private List<Movie> moviesList;
    
	/** The showtimes list. */
	private List<Showtime> showtimesList;
    
	/** The mpaa rating list. */
	private List<String> mpaaRatingList;
	
	//Upload image process attributes
	/** The uploaded small jpg file. */
	private Part uploadedSmallJpgFile;
	
	/** The uploaded large jpg file. */
	private Part uploadedLargeJpgFile;
	
	/** The folder which the files will be uploaded to. */
	private String folder = "C:\\cinema\\images";
	
	/** The image success small size. */
	private boolean imageSuccessSmallSize;
	
	/** The image success large size. */
	private boolean imageSuccessLargeSize;	
    
	//Dimensions
	/** The Constant largeSize. */
	private static final int largeSize = 400;
    
	/** The Constant smallSize. */
	private static final int smallSize = 120;
    
	//Messages that will be used to notify the user
	/** The Constant DOT_NOT_FOUND_IN_THE_FILE_NAME. */
	private static final int DOT_NOT_FOUND_IN_THE_FILE_NAME=-1;
    
	/** The Constant ADD_ONE_POSITION_TO_GET_SUFFIX. */
	private static final int ADD_ONE_POSITION_TO_GET_SUFFIX=1;
    
	/** The Constant ADD_ONE_POSITION_TO_GET_FILE_NAME. */
	private static final int ADD_ONE_POSITION_TO_GET_FILE_NAME=1;
    
	/** The Constant MESSAGE_MOVIE_NAME_ALREADY_EXISTS. */
	private static final String MESSAGE_MOVIE_NAME_ALREADY_EXISTS="Movie name is already exists";
    
	/** The Constant MESSAGE_NO_UPLOADED_IMAGE_FOUND. */
	private static final String MESSAGE_NO_UPLOADED_IMAGE_FOUND="No uploaded images are found";
    
	/** The Constant MESSAGE_FAILURE_TO_ADD_MOVIE. */
	private static final String MESSAGE_FAILURE_TO_ADD_MOVIE="There was a problem to add the movie.";
    
	/** The Constant MESSAGE_UNEXPECTED_DIMENSIONS. */
	private static final String MESSAGE_UNEXPECTED_DIMENSIONS="The dimension of the image needs to be 120X120 or 400X400.";
    
	/** The Constant MESSAGE_NO_JPG_IMAGE. */
	private static final String MESSAGE_NO_JPG_IMAGE="Please upload jpg image only.";
    
	/** The Constant MESSAGE_UNIDENTIFED_IMAGE_FILE. */
	private static final String MESSAGE_UNIDENTIFED_IMAGE_FILE="Not a known image file: ";
    
	/** The Constant MESSAGE_MOVIE_BEING_USED_BY_SHOWTIME. */
	private static final String MESSAGE_MOVIE_BEING_USED_BY_SHOWTIME="This movie is being used in one or more showtimes.";
    
	/** The Constant MESSAGE_FAILURE_TO_DELETE_MOVIE. */
	private static final String MESSAGE_FAILURE_TO_DELETE_MOVIE="There was a problem to delete the movie.";

    
	//Initialize
    /**
     * Initialize method which is loaded before the class is put to service
     * It loads the lists of MPAA Rating, movies and showtimes which will be used in the creation and the deletion of a movie.
     */
	@PostConstruct
    public void initialize() {
    	mpaaRatingList= new ArrayList<String>();
    	showtimesList = showtimeDao.getListOfShowtimes();
    	moviesList = movieDao.getListOfReleasedMovies();
    	for (MpaaFilmRating rating : MpaaFilmRating.values()) {
    		mpaaRatingList.add(rating.getLabel());
    	}
    }

	
	//Class Methods
	/**
	 * Adds the movie.
	 */
	public void addMovie() {
		boolean createMovieSuccess = false;
    	if (checkIfMovieNameExists()) {
    		serviceView.sendMessageToView(MESSAGE_MOVIE_NAME_ALREADY_EXISTS);
    	}
    	else {
	    	setTheNewMovie();		    	
	    	if ((uploadedSmallJpgFile==null) || (uploadedLargeJpgFile==null)) {
	    		serviceView.sendMessageToView(MESSAGE_NO_UPLOADED_IMAGE_FOUND);		    		
	    	}
	    	else {
	    		imageSuccessSmallSize = saveFile(uploadedSmallJpgFile);
		    	imageSuccessLargeSize = saveFile(uploadedLargeJpgFile);
		    	
		    	if(imageSuccessSmallSize && imageSuccessLargeSize) {
		    		createMovieSuccess=movieDao.insertMovie(newMovie); 
			    					    	
			    	if (!createMovieSuccess) {
			    		deleteImages(this.imageName); //rollback
			    		serviceView.sendMessageToView(MESSAGE_FAILURE_TO_ADD_MOVIE);		    		
			    	}
			    	else {
			    		serviceView.reloadPage();
			    	}
		    	}
		    	else {
		    		deleteImages(this.imageName); //rollback
		    	}
	    	}	
	    }	    	
    }
    
	
    /**
	 * Check if movie name exists.
	 *
	 * @return true, if successful
	 */
	private boolean checkIfMovieNameExists() {
    		for(Movie movie : moviesList) {
    			if (movie.getName()==this.name)
    				return true;
    		}	    	
    	return false;
    }
   	    
    
    /**
	 * Sets the the new movie.
	 */
	private void setTheNewMovie() {
    	this.newMovie.setName(name);
    	this.newMovie.setDescription(description);
    	this.newMovie.setYear(year);
    	this.newMovie.setLength(length);
    	this.newMovie.setDirector(director);
    	this.newMovie.setMainActor(mainActor);
    	this.newMovie.setGenre(genre);
    	this.newMovie.setMpaaRating(mpaaRating);
    	this.newMovie.setComingSoon(comingSoon);	    	
    	this.imageName = this.name.replace(' ', '-');
    	this.newMovie.setImageName(imageName);
    }
    
    
    /**
	 * Save file.
	 *
	 * @param uploadedImageFile the uploaded image file
	 * @return true, if successful
	 */
	public boolean saveFile(Part uploadedImageFile){
    	boolean isFileSaved = false;
		try (InputStream input = uploadedImageFile.getInputStream()) {
			String fileName = uploadedImageFile.getSubmittedFileName();
			
			int lastCha = fileName.lastIndexOf('\\'); //get only the file name
			fileName = fileName.substring(lastCha+ADD_ONE_POSITION_TO_GET_FILE_NAME);
			
			if (fileName.endsWith(".jpg")) {			
				File newFile = new File(folder, fileName); //upload the file
				Files.copy(input, newFile.toPath());
				input.close();					
				Dimension dimension = getImageDimension(newFile);					
				if (dimension!=null) {
					
					//if it's the expected small dimension
					if ((dimension.getHeight()==smallSize) && (dimension.width==smallSize)) {							
						Path source = Paths.get(newFile.getPath());
						Path target = new File(folder,this.imageName+"-"+smallSize+"x"+smallSize+".jpg").toPath();							
						Files.copy(source, target);
						newFile.delete();
						return true;
					}
					
					//if it's expected large dimension
					else if((dimension.getHeight()==largeSize) && (dimension.width==largeSize)) {
							Path source = Paths.get(newFile.getPath());
							Path target = new File(folder,this.imageName+"-"+largeSize+"x"+largeSize+".jpg").toPath();
							Files.copy(source, target);
							newFile.delete();
							return true;
					}
					else {
						newFile.delete();
						serviceView.sendMessageToView(MESSAGE_UNEXPECTED_DIMENSIONS);
					}
				}
			}
			else {
				serviceView.sendMessageToView(MESSAGE_NO_JPG_IMAGE);
			}
		}
	    catch (IOException e) {
	        e.printStackTrace();
	    }
		return isFileSaved;
	}

    
    /**
	 * Gets the image dimension.
	 *
	 * @param imgFile the img file
	 * @return the image dimension
	 */
	private Dimension getImageDimension(File imgFile) {
    	  int pos = imgFile.getName().lastIndexOf(".");
    	  if (pos == DOT_NOT_FOUND_IN_THE_FILE_NAME) {
    		  serviceView.sendMessageToView(MESSAGE_FAILURE_TO_ADD_MOVIE);
    		  return null;
    	  }   	  
    	  else {
  	    	  
	    	  String suffix = imgFile.getName().substring(pos + ADD_ONE_POSITION_TO_GET_SUFFIX);
	    	  Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
	    	  while(iter.hasNext()) {
	    	    ImageReader reader = iter.next();
	    	    try {
	    	      ImageInputStream stream = new FileImageInputStream(imgFile);
	    	      reader.setInput(stream);
	    	      int width = reader.getWidth(reader.getMinIndex());
	    	      int height = reader.getHeight(reader.getMinIndex());
	    	      stream.close();
	    	      return new Dimension(width, height);
	    	    } catch (IOException e) {
	    	    	e.printStackTrace();
	    	    } finally {
	    	      reader.dispose();
	    	      
	    	    }		    	  }
	    	  
	    	  serviceView.sendMessageToView(MESSAGE_UNIDENTIFED_IMAGE_FILE + imgFile.getAbsolutePath());	    	
    	  	}
		return null;
    	 }
    
    
	/**
	 * Delete movie.
	 *
	 * @param movie the movie
	 */
	public void deleteMovie(Movie movie) {
		boolean deleteMovieSuccess;
		for (Showtime showtime : showtimesList)
			if (showtime.getMovieId()==movie.getMovieId() && !showtime.isShowtime_deleted()) {				
				serviceView.sendMessageToView(MESSAGE_MOVIE_BEING_USED_BY_SHOWTIME);
				break;			
			}
			else {
				movie.setMovie_deleted(true);
				deleteMovieSuccess = movieDao.updateMovie(movie);
				if (!deleteMovieSuccess) {
					serviceView.sendMessageToView(MESSAGE_FAILURE_TO_DELETE_MOVIE);
				}
				else
				{
					deleteImages(movie.getImageName());
					serviceView.reloadPage();
				}				
			}
	}

			  

	/**
	 * Delete images.
	 *
	 * @param imageName the image name
	 * @return true, if successful
	 */
	public boolean deleteImages(String imageName) {
		boolean deleteSmall=false;
		boolean deleteLarge=false;
		boolean result=false;
		
		File largeImageToDelete = new File(folder, imageName+"-"+largeSize+"x"+largeSize+".jpg");
		File smallImageToDelete = new File(folder, imageName+"-"+smallSize+"x"+smallSize+".jpg");
		
		if (largeImageToDelete.exists()) {
			deleteSmall=largeImageToDelete.delete();
		}
		if (smallImageToDelete.exists()) {
			deleteLarge=smallImageToDelete.delete();
		}
		
		if (deleteSmall && deleteLarge)
			return true;
		
		return result;
		
	}

	
	//Getters And Setters
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 *
	 * @param length the new length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Gets the director.
	 *
	 * @return the director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Sets the director.
	 *
	 * @param director the new director
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * Gets the main actor.
	 *
	 * @return the main actor
	 */
	public String getMainActor() {
		return mainActor;
	}

	/**
	 * Sets the main actor.
	 *
	 * @param mainActor the new main actor
	 */
	public void setMainActor(String mainActor) {
		this.mainActor = mainActor;
	}

	/**
	 * Gets the genre.
	 *
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Sets the genre.
	 *
	 * @param genre the new genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * Gets the mpaa rating.
	 *
	 * @return the mpaa rating
	 */
	public String getMpaaRating() {
		return mpaaRating;
	}

	/**
	 * Sets the mpaa rating.
	 *
	 * @param mpaaRating the new mpaa rating
	 */
	public void setMpaaRating(String mpaaRating) {
		this.mpaaRating = mpaaRating;
	}

	/**
	 * Checks if is coming soon.
	 *
	 * @return true, if is coming soon
	 */
	public boolean isComingSoon() {
		return comingSoon;
	}

	/**
	 * Sets the coming soon.
	 *
	 * @param comingSoon the new coming soon
	 */
	public void setComingSoon(boolean comingSoon) {
		this.comingSoon = comingSoon;
	}

	/**
	 * Gets the image name.
	 *
	 * @return the image name
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * Sets the image name.
	 *
	 * @param imageName the new image name
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * Gets the new movie.
	 *
	 * @return the new movie
	 */
	public Movie getNewMovie() {
		return newMovie;
	}

	/**
	 * Sets the new movie.
	 *
	 * @param newMovie the new new movie
	 */
	public void setNewMovie(Movie newMovie) {
		this.newMovie = newMovie;
	}

	/**
	 * Gets the uploaded small jpg file.
	 *
	 * @return the uploaded small jpg file
	 */
	public Part getUploadedSmallJpgFile() {
		return uploadedSmallJpgFile;
	}

	/**
	 * Sets the uploaded small jpg file.
	 *
	 * @param uploadedSmallJpgFile the new uploaded small jpg file
	 */
	public void setUploadedSmallJpgFile(Part uploadedSmallJpgFile) {
		this.uploadedSmallJpgFile = uploadedSmallJpgFile;
	}

	/**
	 * Gets the uploaded large jpg file.
	 *
	 * @return the uploaded large jpg file
	 */
	public Part getUploadedLargeJpgFile() {
		return uploadedLargeJpgFile;
	}

	/**
	 * Sets the uploaded large jpg file.
	 *
	 * @param uploadedLargeJpgFile the new uploaded large jpg file
	 */
	public void setUploadedLargeJpgFile(Part uploadedLargeJpgFile) {
		this.uploadedLargeJpgFile = uploadedLargeJpgFile;
	}
      
    /**
	 * Gets the mpaa rating list.
	 *
	 * @return the mpaa rating list
	 */
	public List<String> getMpaaRatingList() {
		return mpaaRatingList;
	}

	/**
	 * Sets the mpaa rating list.
	 *
	 * @param mpaaRatingList the new mpaa rating list
	 */
	public void setMpaaRatingList(List<String> mpaaRatingList) {
		this.mpaaRatingList = mpaaRatingList;
	}
	
	/**
	 * Gets the str year.
	 *
	 * @return the str year
	 */
	public String getStrYear() {
		return strYear;
	}

	/**
	 * Sets the str year.
	 *
	 * @param strYear the new str year
	 */
	public void setStrYear(String strYear) {
		this.year=Integer.parseInt(strYear);
		this.strYear = strYear;
	}
   
    /**
	 * Gets the str length.
	 *
	 * @return the str length
	 */
	public String getStrLength() {
		return strLength;
	}

	/**
	 * Sets the str length.
	 *
	 * @param strLength the new str length
	 */
	public void setStrLength(String strLength) {
		this.length=Integer.parseInt(strLength);
		this.strLength = strLength;
	}
	
}
