package com.danielly.jsf.cinema.model.enums;



// TODO: Auto-generated Javadoc
/**
 * The class UserRoles holds the role of the user in enum.
 * It'b being used in the Administrator interface.
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */

public enum UserRoles {
	
	/** The admin. */
	ADMIN("Administrator"),
	
	/** The sales. */
	SALES("Sales"),
	
	/** The reporting. */
	REPORTING("Reporting");
	
	/** The label. */
	private String label;
	
	/**
	 * Instantiates a new user roles.
	 *
	 * @param envLable the env lable
	 */
	UserRoles(String envLable){
		this.label=envLable;	
	}
	
	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Value of label.
	 *
	 * @param label the label
	 * @return the user roles
	 */
	public static UserRoles valueOfLabel(String label) {
	    for (UserRoles role : values()) {
	        if (role.label.equals(label)) {
	            return role;
	        }
	    }
	    return null;
	}
}