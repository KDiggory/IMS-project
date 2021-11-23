package com.qa.ims.persistence.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.JoinTable;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		Long customerId = resultSet.getLong("customerId");
		Long orderId = resultSet.getLong("orderId");
		Long itemId = resultSet.getLong("itemId");
		String itemName = resultSet.getString("itemName");
		Long totalCost = resultSet.getLong("totalCost");
		Long numItems = resultSet.getLong("numItems");
		Long itemCost = resultSet.getLong("itemCost");
		
		return new Order(id, customerId);
		
	}
	
	public JoinTable modelFromResultSetJoin(ResultSet resultSet) throws SQLException {
//		Long id = resultSet.getLong("id");
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
				ResultSet resultSet = statement.executeQuery("SELECT customerId AS customerId, surname AS customerSurname, orderId, itemId, itemName, order_items.cost AS totalCost, "
						+ "numItems, items.cost AS itemCost FROM orders "
						+ "INNER JOIN order_items ON orders.id=order_items.orderId "
						+ "INNER JOIN items ON order_items.id=items.id "
						+ "INNER JOIN customers ON orders.customerId=customers.id;" );) {
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
	
		
// Can't use this one because I didnt make a whole new set of classes for join table
	@Override
	public Order read(Long id) {
		return null;
	}
	
	
	public JoinTable readId(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(
				"SELECT customerId AS customerId, surname AS customerSurname, orderId, itemId, itemName, order_items.cost AS totalCost, "
				+ "numItems, items.cost AS itemCost FROM orders "
				+ "INNER JOIN order_items ON orders.id=order_items.orderId "
				+ "INNER JOIN items ON order_items.id=items.id "
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
	
	
	@Override
	public Order update(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE orders SET customerId=? WHERE id = ?");) {
			statement.setLong(1, order.getCustomerId());
			statement.setLong(2, order.getId());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}



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

	@Override
	public List<Order> readAll() {
		// TODO Auto-generated method stub
		return null;
	}
	}

	

