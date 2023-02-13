package com.danielly.jsf.cinema.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.danielly.jsf.cinema.dao.interfaces.OrderDao;
import com.danielly.jsf.cinema.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The class OrderDaoImpl contains all the methods implementation to query the Orders table.
 * It contains the basic CRUD methods and additional methods that were needed.
 * This class implements the OrderDao interface
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 */

@ApplicationScoped
public class OrderDaoImpl implements OrderDao {

	
	//Attributes
	/** The Database Connection Controller. */
	@Inject
	private DbConnectionController dbConnectionController;
    
    /** The connection to the database. */
    private Connection connection;  
    
    /** The list of all of the orders. */
    private List<Order> allOrders;

    /** The Constant NO_AFFECTED_ROWS is used to check if there any affected rows .*/
    private final static int NO_AFFECTED_ROWS = 0;
    
    /** The Constant ONE_AFFECTED_OR_MATCHED_ROW is used to check if there is 1 affected or matched row.*/
    private final static int ONE_AFFECTED_OR_MATCHED_ROW = 1;
    
    /** The Constant FIRST_COLUMN_TO_RETURN_THE_ID is used to define the column the Id can be found in the database. */
    private final static int FIRST_COLUMN_TO_RETURN_THE_ID = 1;
    
    
    //Empty Constructor
    /**
     * Instantiates a new OrderDaoImpl.
     */
    public OrderDaoImpl() { 
    }
    

    
    //Database Queries Methods
    
    /**
     * Gets the list of all of the orders from the orders table in the database.
     *
     * @return the all orders
     */
    @Override
    public List<Order> getAllOrders() {

    	Statement stmt = null;
    	ResultSet rs = null;
    	allOrders = new ArrayList<>();
    	try{   
    		connection = dbConnectionController.getConnection();  
    		stmt=connection.createStatement();    
    	    rs=stmt.executeQuery("select * from orders");    
    		while(rs.next())
    			{  
	    		Order order = new Order();  
	    		order.setOrderId(rs.getInt("order_id"));  
	    		order.setOrderDateHour(rs.getTimestamp("order_date_hour").toLocalDateTime());  
	    		order.setTotalPriceNis(rs.getInt("total_price_nis"));
	    		order.setShowtimeId(rs.getInt("showtime_id"));  
	    		order.setCardNumber(rs.getString("card_number"));
	    		order.setCardType(rs.getString("card_type"));
	    		order.setCardExpirationYear(rs.getString("card_expiration_year"));
	    		order.setCardExpirationMonth(rs.getString("card_expiration_month"));
	    		order.setCardExpirationFormated(rs.getString("card_expiration_month")+"/"+rs.getString("card_expiration_year"));
	    		order.setCardCvv(rs.getString("card_cvv"));
	    		order.setStatus(rs.getString("status"));
	    		order.setPayerName(rs.getString("payer_name"));
	    		order.setOrderDateHourFormated(convertLocalDateToString(rs.getTimestamp("order_date_hour").toLocalDateTime().toLocalDate())); 
	    		
	    		allOrders.add(order);
    			}
    		}catch(SQLException e){  
    			System.out.println(e);  
    		}finally {
    			 dbConnectionController.closeConnection(rs, stmt, connection);
    		} 
    return allOrders;
    }  

    /**
     * Gets the order from the orders table in the database.
     *
     * @param id the id
     * @return the order
     */
    @Override
    public Order getOrder(int id) {
    	connection =  dbConnectionController.getConnection();
    	Statement stmt = null;
    	ResultSet rs = null;
        try {
        	 stmt = connection.createStatement();
        	 rs = stmt.executeQuery("SELECT * FROM orders WHERE order_id=" + id);
        	 
        	 if(rs.next())
        	 {
 	    		Order order = new Order();  
 	    		order.setOrderId(rs.getInt("order_id"));  
 	    		order.setOrderDateHour(rs.getTimestamp("order_date_hour").toLocalDateTime());  
 	    		order.setTotalPriceNis(rs.getInt("total_price_nis"));
 	    		order.setShowtimeId(rs.getInt("showtime_id"));  
 	    		order.setCardNumber(rs.getString("card_number"));
 	    		order.setCardType(rs.getString("card_type"));
	    		order.setCardExpirationYear(rs.getString("card_expiration_year"));
	    		order.setCardExpirationMonth(rs.getString("card_expiration_month"));
 	    		order.setCardCvv(rs.getString("card_cvv"));
 	    		order.setStatus(rs.getString("status"));
 	    		order.setPayerName(rs.getString("payer_name"));
 	    		order.setOrderDateHourFormated(convertLocalDateToString(rs.getTimestamp("order_date_hour").toLocalDateTime().toLocalDate())); 
	             
	            return order;
        	 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	 dbConnectionController.closeConnection(rs, stmt, connection);
    	}  
        return null;
    }
    

    
    /**
     * Insert an order from the orders table in the database..
     *
     * @param order the order
     * @return true, if successful
     */
    @Override
	public boolean insertOrder(Order order) {
    	connection = dbConnectionController.getConnection();
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("INSERT INTO orders (order_date_hour, total_price_nis, showtime_id, card_number, card_type, card_expiration_year, card_expiration_month, card_cvv, status, payer_name) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        	 ps.setTimestamp(1, Timestamp.valueOf(order.getOrderDateHour()));
             ps.setInt(2, order.getTotalPriceNis());
             ps.setInt(3, order.getShowtimeId());
             ps.setString(4, order.getCardNumber());
             ps.setString(5, order.getCardType());
             ps.setString(6, order.getCardExpirationYear());
             ps.setString(7, order.getCardExpirationMonth());
             ps.setString(8, order.getCardCvv());
             ps.setString(9, order.getStatus());
             ps.setString(10, order.getPayerName());
           
             int i = ps.executeUpdate();
            
          if(i == ONE_AFFECTED_OR_MATCHED_ROW) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	dbConnectionController.closeConnection(ps, connection);
    	}  
        return false;
    }
    
    
    
	/**
	 * Insert order and return the id from the orders table in the database.
	 *
	 * @param order the order
	 * @return the int
	 */
	@Override
	public int insertOrderAndReturnId(Order order) {
		connection =  dbConnectionController.getConnection(); 
    	PreparedStatement ps = null;
    	ResultSet rsKey = null;
    	String insertSql = "INSERT INTO orders (order_date_hour, total_price_nis, showtime_id, card_number, card_type, card_expiration_year, card_expiration_month, card_cvv, status, payer_name) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	int resultOrderId=0;
        try {
        	 ps = connection.prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS);
        	 ps.setTimestamp(1, Timestamp.valueOf(order.getOrderDateHour()));
             ps.setInt(2, order.getTotalPriceNis());
             ps.setInt(3, order.getShowtimeId());
             ps.setString(4, order.getCardNumber());
             ps.setString(5, order.getCardType());
             ps.setString(6, order.getCardExpirationYear());
             ps.setString(7, order.getCardExpirationMonth());
             ps.setString(8, order.getCardCvv());
             ps.setString(9, order.getStatus());
             ps.setString(10, order.getPayerName());
             
             int i = ps.executeUpdate();
            
          if(i!=NO_AFFECTED_ROWS) {
        	   rsKey = ps.getGeneratedKeys();
        	  if(rsKey.next()) 
        	  {
        		  resultOrderId = rsKey.getInt(FIRST_COLUMN_TO_RETURN_THE_ID);
        		    return resultOrderId;
        	  }
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	 dbConnectionController.closeConnection(rsKey, ps, connection);
    	}  
        return NO_AFFECTED_ROWS;
    }
	
    
    /**
     * Update order in the orders table in the database.
     *
     * @param order the order
     * @return true, if successful
     */
    @Override
	public boolean updateOrder(Order order) {
    	connection =  dbConnectionController.getConnection();
    	PreparedStatement ps = null;
        try {
        	 ps = connection.prepareStatement("UPDATE orders SET order_date_hour=?, total_price_nis=?, showtime_id=?, card_number=?, card_type=?, card_expiration_year=?, card_expiration_month=?, card_cvv=?, status=?, payer_name=? WHERE order_id=?");
        	 ps.setTimestamp(1, Timestamp.valueOf(order.getOrderDateHour()));
             ps.setInt(2, order.getTotalPriceNis());
             ps.setInt(3, order.getShowtimeId());
             ps.setString(4, order.getCardNumber());
             ps.setString(5, order.getCardType());
             ps.setString(6, order.getCardExpirationYear());
             ps.setString(7, order.getCardExpirationMonth());
             ps.setString(8, order.getCardCvv());
             ps.setString(9, order.getStatus());
             ps.setString(10, order.getPayerName());
             ps.setInt(11, order.getOrderId());
             
             int i = ps.executeUpdate();
            
          if(i == ONE_AFFECTED_OR_MATCHED_ROW) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	 dbConnectionController.closeConnection(ps, connection);
    	}  
        return false;
    }
	
	
    /**
     * Delete order from the orders table in the database.
     *
     * @param id the id
     * @return true, if successful
     */
    @Override
    public boolean deleteOrder(int id) {
	connection =  dbConnectionController.getConnection();  
	Statement stmt = null;
    try {
        stmt = connection.createStatement();
        int i = stmt.executeUpdate("DELETE FROM orders WHERE order_id=" + id);
      if(i == ONE_AFFECTED_OR_MATCHED_ROW) {
    return true;
      }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }finally {
    	 dbConnectionController.closeConnection(stmt, connection);
	}  
    return false;
    }
    
    
    //Converter
    /**
     * Convert local date to string.
     *
     * @param localDate the local date
     * @return the string
     */
    public String convertLocalDateToString(LocalDate localDate) {
    	DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String resultString = localDate.format(formatters);
    	
    	return resultString;
    }
    
}
