package com.danielly.jsf.cinema.services.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.danielly.jsf.cinema.dao.interfaces.UserDao;
import com.danielly.jsf.cinema.model.User;
import com.danielly.jsf.cinema.model.enums.UserRoles;
import com.danielly.jsf.cinema.services.ServiceView;


/**
 * The Class UsersAdmin provides methods to manage the users in the GUI interface.
 * It handle the inputs, query the database and notify the user when needed.
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 * 
 */


@Named("user_data")
@RequestScoped
public class UsersAdmin implements Serializable {
	
	//Attributes
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The new user. */
	@Inject
	private User newUser;
	
	/** The user dao. */
	@Inject
    private UserDao userDao;
	
	/** The service view which is used to run methods that related to the GUI. */
    @Inject
    private ServiceView serviceView;
	
	/** The users list. */
	private List<User> usersList;
	
	/** The roles list. */
	private List<String> rolesList;
	
	//input
	/** The username. */
	@Pattern(regexp = "[A-Za-z0-9]{2,20}", message = "Please enter a username consisting of only letters and digits, between 2 and 20 characters long.")
    private String username;

    /** The password. */
    @Size(min = 8, message = "Your password must consist of at least 8 characters.")
    private String password;

    /** The first name. */
    @Size(min = 1, max = 30, message = "Please enter a first name between 1 and 30 characters long.")
    private String firstName;

    /** The last name. */
    @Size(min = 1, max = 30, message = "Please enter a last name between 1 and 30 characters long.")
    private String lastName;

    /** The email address which is being validated by the ValidEmailAddress. */
    @ValidEmailAddress
    private String emailAddress;

    /** The role. */
    @NotNull
    private String role;
	
    /** The Constant SYSTEM_ADMIN_USER. */
    private static final String SYSTEM_ADMIN_USER = "admin";
	
	/** The Constant MESSAGE_USER_USERNAME_EXISTED. */
	private static final String MESSAGE_USER_USERNAME_EXISTED = "User with that username already exists.";
	
	/** The Constant MESSAGE_USER_CREATION_FAILURE. */
	private static final String MESSAGE_USER_CREATION_FAILURE = "There was a problem to create the user.";
	
	/** The Constant MESSAGE_USER_DELETION_FAILURE. */
	private static final String MESSAGE_USER_DELETION_FAILURE = "here was a problem to delete the user.";
	
	/** The Constant MESSAGE_CANNOT_DELETE_SYSTEM_ADMIN_USER. */
	private static final String MESSAGE_CANNOT_DELETE_SYSTEM_ADMIN_USER = "You cannot delete the default admin user.";
	
	
	
	//Initialize
    /**
     * Initialize method which is loaded before the class is put to service
     * It loads the list and selection which will be used in the creation and the deletion of a user.
     */
	@PostConstruct
	public void initialize() {
		usersList= userDao.getAllUsers();
		rolesList= new ArrayList<String>();
		for (UserRoles role : UserRoles.values()) {
			rolesList.add(role.getLabel());
    	}
	}

	
	//Class Methods	
	/**
	 * Adds the user.
	 */
	public void addUser()  {
		boolean createUserSuccess =false;
		if (checkIfUserExisted()) {
			serviceView.sendMessageToView(MESSAGE_USER_USERNAME_EXISTED);
		}
		else {
			createNewUserObject();
			createUserSuccess = userDao.insertUser(newUser);
		
			if (createUserSuccess)
			{   
				serviceView.reloadPage();
			}
			else {
				serviceView.sendMessageToView(MESSAGE_USER_CREATION_FAILURE);				
			}				
			}
		}
	
	
	/**
	 * Creates the new user object.
	 */
	private void createNewUserObject() {
		newUser.setUsername(username);
		newUser.setPassword(password);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setEmailAddress(emailAddress);
		UserRoles roleEnum = UserRoles.valueOfLabel(role);
		newUser.setRole(roleEnum.name());
	}
	
	
	/**
	 * Delete user.
	 *
	 * @param user the user
	 */
	public void deleteUser(User user){
		boolean deleteUserSuccess = false;
		if (user.getUsername().equals(SYSTEM_ADMIN_USER)) {
			serviceView.sendMessageToView(MESSAGE_CANNOT_DELETE_SYSTEM_ADMIN_USER);			
		}
		else {
			deleteUserSuccess=userDao.deleteUser(user.getUserId()); 
			if (deleteUserSuccess)
			{   
				serviceView.reloadPage();
			}		
			else {
				serviceView.sendMessageToView(MESSAGE_USER_DELETION_FAILURE);
			}
		}
		
	}
	
	
	/**
	 * Gets the label from enum.
	 * Will be used to get the enum from the label received in the input.
	 *
	 * @param rawRole the raw role
	 * @return the label from enum
	 */
	public String getLabelFromEnum(String rawRole) {
		 UserRoles roleEnum = UserRoles.valueOf(rawRole);
		 return roleEnum.getLabel();
	}
	

	
	/**
	 * Check if user existed.
	 *
	 * @return true, if successful
	 */
	private boolean checkIfUserExisted() {
		for (User user : usersList) {
			if (user.getUsername().equals(this.username)) {
				return true;
			}
		}
		return false;
	}	
	
	
	//Getters And Setters
	/**
	 * Gets the users list.
	 *
	 * @return the users list
	 */
	public List<User> getUsersList() {
		return usersList;
	}

	/**
	 * Sets the users list.
	 *
	 * @param usersList the new users list
	 */
	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

	/**
	 * Gets the roles list.
	 *
	 * @return the roles list
	 */
	public List<String> getRolesList() {
		return rolesList;
	}

	/**
	 * Sets the roles list.
	 *
	 * @param rolesList the new roles list
	 */
	public void setRolesList(List<String> rolesList) {
		this.rolesList = rolesList;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email address.
	 *
	 * @return the email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the email address.
	 *
	 * @param emailAddress the new email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
}
