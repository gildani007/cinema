package com.danielly.jsf.cinema.model.enums;

/**
 * The class OrderStatus holds the status of the order in enum.
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */

public enum OrderStatus {
	
	/** The success status. */
	SUCCESS("Success"),
	
	/** The canceled status. */
	CANCELED("Cancelled");
	
	/** The label. */
	private String label;
	
	/**
	 * Instantiates a new order status.
	 *
	 * @param newLabel the new label
	 */
	OrderStatus(String newLabel){
		this.label=newLabel;	
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