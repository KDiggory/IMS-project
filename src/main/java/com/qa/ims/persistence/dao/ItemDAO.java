package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class ItemDAO implements Dao<Item> {

	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Item modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		String name = resultSet.getString("name");
		String size = resultSet.getString("size");
		Long cost = resultSet.getLong("cost");
		return new Item(id, name, size, cost);
	}

	@Override
	public List<Item> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items");) {
			List<Item> items = new ArrayList<>();
			while (resultSet.next()) {
				items.add(modelFromResultSet(resultSet));
			}
			return items;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();

	}


	@Override
	public Item read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM items WHERE id = ?");) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Item create(Item item) {
			try (Connection connection = DBUtils.getInstance().getConnection();
					PreparedStatement statement = connection
							.prepareStatement("INSERT INTO items(name, size, cost) VALUES (?, ?, ?)");) {
				statement.setString(1, item.getName());
				statement.setString(2, item.getSize());
				statement.setLong(3, item.getCost());	// the problem looks to be here somewhere?
				statement.executeUpdate();
				return readLatest();
			} catch (Exception e) {
				LOGGER.debug(e);
			LOGGER.error(e.getMessage());

			}
			return null;
	}

	@Override
	public Item update(Item item) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE items SET name = ?, size = ?, cost = ? WHERE id = ?");) {
			statement.setString(1, item.getName());
			statement.setString(2, item.getSize());
			statement.setDouble(3, item.getCost());			
			statement.setLong(4, item.getId());
			statement.executeUpdate();
			return read(item.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM items WHERE id = ?");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}
	
	public Item readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	public String getItemNums() { 
		String availableItemNums = "Available Items:\n";
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items");) {
			List<Order> orders = new ArrayList<>();
			HashMap<String, Long> hashMap = new HashMap<>();
			while (resultSet.next()) {
				Long id = modelFromResultSet(resultSet).getId();
				String name = modelFromResultSet(resultSet).getName();
				
				hashMap.put(name, id);
			}
			availableItemNums = availableItemNums + hashMap.toString();
			return availableItemNums;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return "";  
	}

	public String getItemNumsFromOrder(Long id) { // THIS is the column name not found problem! This is fixed
		String availableItemNums = "Available Items:\n";
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM items "
						+ "JOIN order_items ON items.id=order_items.itemId "
						+ "WHERE order_items.orderId=?;");) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery(); 
			List<Order> orders = new ArrayList<>();
			HashMap<String, Long> hashMap = new HashMap<>();
			while (resultSet.next()) {
				Long id2 = modelFromResultSet(resultSet).getId();
				String name = modelFromResultSet(resultSet).getName();
				
				hashMap.put(name, id2);
			}
			availableItemNums = availableItemNums + hashMap.toString();
			return availableItemNums;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return "";
	
	}

}

