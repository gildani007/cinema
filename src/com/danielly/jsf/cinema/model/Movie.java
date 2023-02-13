package com.danielly.jsf.cinema.model;

import java.io.Serializable;

/**
 * The class Movie holds the information about a movie such as the name of the movie, director etc.
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0 
 */

public final class Movie implements Serializable {
	
	//Attributes
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The movie id. */
    private int movieId;
    
    /** The name. */
    private String name;
    
    /** The description. */
    private String description;
    
    /** The year. */
    private int year;
	
	/** The length. */
	private int length;
    
    /** The director. */
    private String director;
    
    /** The main actor. */
    private String mainActor;
    
    /** The genre. */
    private String genre;  
	
	/** The mpaa rating. */
	private String mpaaRating;
    
    /** The coming soon. */
    private boolean comingSoon;
    
    /** The image file name without the suffix. */
    private String imageName;
    
    /** The movie deleted is indicator if a movie deleted. */
	private boolean movie_deleted;
    
    
	//Empty Constructor
    /**
     * Instantiates a new movie.
     */
    public Movie(){
		
	}	
	
    //Getters and Setters
    /**
	 * Gets the movie id.
	 *
	 * @return the movie id
	 */
    public int getMovieId() {
        return movieId;
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
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the short description.
     *
     * @return the short description
     */
    public String getShortDescription() {
        int i = description.indexOf('.');
        if (i >= 0 && i < 100) {
            return description.substring(0, i + 1);
        } else {
            return description.substring(0, Math.min(description.length(), 100)) + "...";
        }
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
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength() {
		return length;
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
	 * Gets the main actor.
	 *
	 * @return the main actor
	 */
	public String getMainActor() {
		return mainActor;
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
     * Sets the movie id.
     *
     * @param movieId the new movie id
     */
    public void setMovieId(int movieId) {
		this.movieId = movieId;
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
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * Sets the length.
	 *
	 * @param length the new length
	 */
	public void setLength(int length) {
		this.length = length;
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
	 * Sets the main actor.
	 *
	 * @param mainActor the new main actor
	 */
	public void setMainActor(String mainActor) {
		this.mainActor = mainActor;
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
	 * Checks if is the movie is coming soon.
	 *
	 * @return true, if is coming soon
	 */
	public boolean isComingSoon() {
		return comingSoon;
	}

	/**
	 * Sets the movie as a coming soon movie.
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
	 * Checks if is movie deleted.
	 *
	 * @return true, if is movie deleted
	 */
	public boolean isMovie_deleted() {
		return movie_deleted;
	}

	/**
	 * Sets the movie deleted.
	 *
	 * @param movie_deleted the new movie deleted
	 */
	public void setMovie_deleted(boolean movie_deleted) {
		this.movie_deleted = movie_deleted;
	}

}
