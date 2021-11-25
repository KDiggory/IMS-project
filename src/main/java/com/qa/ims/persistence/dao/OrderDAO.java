package com.qa.ims.persistence.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.JoinTable;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");	
		Long customerId = resultSet.getLong("customerId");
		return new Order(id, customerId);
		
	}
	
	public JoinTable modelFromResultSetJoin(ResultSet resultSet) throws SQLException {

		Long customerId = resultSet.getLong("customerId");
		Long orderId = resultSet.getLong("orderId");
		Long itemId = resultSet.getLong("itemId");
		String itemName = resultSet.getString("itemName");
		Long totalCost = resultSet.getLong("totalCost");
		Long numItems = resultSet.getLong("numItems");
		Long itemCost = resultSet.getLong("itemCost");
		String customerSurname = resultSet.getString("customerSurname");		
		return new JoinTable(customerId, customerSurname, orderId, itemId, itemName,itemCost, numItems,totalCost);
		
	}
	
	
	
	

	
	public List<JoinTable> readAllOrders() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT customerId, surname AS customerSurname, orderId, itemId, itemName, order_items.cost AS totalCost, " // This SQL statement works in workbench
						+ "numItems, items.cost AS itemCost FROM orders "
						+ "INNER JOIN order_items ON orders.id=order_items.orderId "
						+ "INNER JOIN items ON items.id=order_items.itemId "
						+ "INNER JOIN customers ON orders.customerId=customers.id; " );) {
			List<JoinTable> orders = new ArrayList<>();
			while (resultSet.next()) {
				orders.add(modelFromResultSetJoin(resultSet)); 
				
			}
			System.out.println(orders.size()); // prints 3
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
		
// Can't use this one because I didnt make a whole new set of classes for join table
	@Override
	public Order read(Long id) {
		return null;
	}
	
	
	public JoinTable readId(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(
				"SELECT customerId, surname AS customerSurname, orderId, itemId, itemName, order_items.cost AS totalCost, "
				+ "numItems, items.cost AS itemCost FROM orders "
				+ "INNER JOIN order_items ON orders.id=order_items.orderId "
				+ "INNER JOIN items ON items.id=order_items.itemId "
				+ "INNER JOIN customers ON orders.customerId=customers.id "
				+ "WHERE order_items.orderId=?" );) {		
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSetJoin(resultSet);
				
			}
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	public List<JoinTable> readIdTable(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(
				"SELECT customerId, surname AS customerSurname, orderId, itemId, itemName, order_items.cost AS totalCost, "
				+ "numItems, items.cost AS itemCost FROM orders "
				+ "INNER JOIN order_items ON orders.id=order_items.orderId "
				+ "INNER JOIN items ON items.id=order_items.itemId "
				+ "INNER JOIN customers ON orders.customerId=customers.id "
				+ "WHERE order_items.orderId=?" );) {		
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			List<JoinTable> orders = new ArrayList<>();
			while (resultSet.next()) {
				orders.add(modelFromResultSetJoin(resultSet)); 
			} return orders;
		} catch (Exception e) {
			
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order create(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO orders (customerId)"
								+ "VALUES (?)");) {
			statement.setLong(1, order.getCustomerId());
			statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		} 
		return null;
	}
	
	//needs changing - dont need this for order
	@Override
	public Order update(Order order) {
//		try (Connection connection = DBUtils.getInstance().getConnection();
//				PreparedStatement statement = connection
//						.prepareStatement("UPDATE orders SET customerId=? WHERE id = ?");) {
//			statement.setLong(1, order.getCustomerId());
//			statement.setLong(2, order.getId());
//			statement.executeUpdate();
//			
//			return readLatest();
//		} catch (Exception e) {
//			LOGGER.debug(e);
//			LOGGER.error(e.getMessage());
//		}
		return null;
	}


	//needs changing - or does it because of the on delete cascade??
	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id = ?");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

	
	//needs changing?
	public Order readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override // dont need this, done my own
	public List<Order> readAll() {
		return null;
	}

	public List<JoinTable> readByCustomer(long id) {
		int read = 1;
		Long totalCost = 0L;
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(
				"SELECT customerId, surname AS customerSurname, orderId, itemId, itemName, order_items.cost AS totalCost, "
				+ "numItems, items.cost AS itemCost FROM orders "
				+ "INNER JOIN order_items ON orders.id=order_items.orderId "
				+ "INNER JOIN items ON items.id=order_items.itemId "
				+ "INNER JOIN customers ON orders.customerId=customers.id "
				+ "WHERE orders.customerId=?" );) {	
			statement.setLong(1, id);
				ResultSet resultSet = statement.executeQuery();
			List<JoinTable> orders = new ArrayList<>();
			while (resultSet.next()) {
				orders.add(modelFromResultSetJoin(resultSet));
			} 
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	public long totalCost(long id) {
		long totalCost = 0L;
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(
				"SELECT customerId, surname AS customerSurname, orderId, itemId, itemName, order_items.cost AS totalCost, "
				+ "numItems, items.cost AS itemCost FROM orders "
				+ "INNER JOIN order_items ON orders.id=order_items.orderId "
				+ "INNER JOIN items ON items.id=order_items.itemId "
				+ "INNER JOIN customers ON orders.customerId=customers.id "
				+ "WHERE orders.customerId=?" );) {	
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
		List<JoinTable> orders = new ArrayList<>();
		while (resultSet.next()) {
			orders.add(modelFromResultSetJoin(resultSet));
			
		} for (JoinTable order : orders) {
			totalCost = totalCost + order.getTotalCost();
		}
		return totalCost;
	} catch (SQLException e) {
		LOGGER.debug(e);
		LOGGER.error(e.getMessage());
	}
	return totalCost;
	}
	
	public long totalCostByOrder(long id) {
		long totalCost = 0L;
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(
				"SELECT customerId, surname AS customerSurname, orderId, itemId, itemName, order_items.cost AS totalCost, "
				+ "numItems, items.cost AS itemCost FROM orders "
				+ "INNER JOIN order_items ON orders.id=order_items.orderId "
				+ "INNER JOIN items ON items.id=order_items.itemId "
				+ "INNER JOIN customers ON orders.customerId=customers.id "
				+ "WHERE order_items.orderId=?" );) {	
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
		List<JoinTable> orders = new ArrayList<>();
		while (resultSet.next()) {
			orders.add(modelFromResultSetJoin(resultSet));
			
		} for (JoinTable order : orders) {
			totalCost = totalCost + order.getTotalCost();
		}
		return totalCost;
	} catch (SQLException e) {
		LOGGER.debug(e);
		LOGGER.error(e.getMessage());
	}
	return totalCost;
	}
	
	public String getOrderNums() {
		String availableOrderNums = "Available order numbers:\n ";
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");) {
			List<Order> orders = new ArrayList<>();
			Set<Long> longHash = new HashSet<>();
			while (resultSet.next()) {
				Long availId = modelFromResultSet(resultSet).getId();
				longHash.add(availId);
			}
			availableOrderNums = availableOrderNums + longHash.toString();
			return availableOrderNums;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return "";
	}
	
	public String getCustomerNums() {
		String availableCustNums = "Available customers with orders:";
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");) {
			List<Order> orders = new ArrayList<>();
			Set<Long> longHash = new HashSet<>();
			while (resultSet.next()) {
				Long availId = modelFromResultSet(resultSet).getCustomerId();
				longHash.add(availId);
			}
			availableCustNums = availableCustNums + longHash.toString();
			return availableCustNums;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return "";
	}

	public String getAllCustomerNums() {
		String availableCustNums = "Available customers: ";
		CustomerDAO custDAO = new CustomerDAO();
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");) {
			List<Order> orders = new ArrayList<>();
			Set<Long> longHash = new HashSet<>();
			while (resultSet.next()) {
				Long availId = custDAO.modelFromResultSet(resultSet).getId();
				longHash.add(availId);
			}
			availableCustNums = availableCustNums + longHash.toString();
			return availableCustNums;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return "";
	}
	
}

	

