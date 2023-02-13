package com.danielly.jsf.cinema.model.enums;

/**
 * The class MpaaFilmRating holds the Rating of the movie in enum.
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */

public enum MpaaFilmRating {
	
	/** G – General Audiences. */
	G("G"),
	
	/** PG – Parental Guidance Suggested. */
	PG("PG"),
	
	/** PG-13 – Parents Strongly Cautioned. */
	PG13("PG-13"),
	
	/** R – Restricted. */
	R("R"),
	
	/** NC-17 – Adults Only. */
	NC17("NC-17");
	
	/** The label. */
	private String label;
	
	
	
	/**
	 * Instantiates a new MPAA film rating.
	 *
	 * @param newlabel the new label
	 */
	MpaaFilmRating(String newlabel){
		this.label=newlabel;	
	}
	
	
	
	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
}