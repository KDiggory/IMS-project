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
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.DBUtils;

public class OrderItemDAO implements Dao<OrderItem> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public OrderItem modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		Long orderId = resultSet.getLong("orderId");
		Long itemId = resultSet.getLong("itemId");
		String itemName = resultSet.getString("itemName");
		Long numItems = resultSet.getLong("numItems");
		Long cost = resultSet.getLong("cost");
		return new OrderItem (orderId, itemId, itemName, numItems, cost);
	}

	@Override
	public List<OrderItem> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM order_items");) {
			List<OrderItem> orderItems = new ArrayList<>();
			while (resultSet.next()) {
				orderItems.add(modelFromResultSet(resultSet));
			}
			return orderItems;
		} catch (SQLException e) {
			LOGGER.debug(e); 
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	 
//	public List<JoinTable> readAllJoin() {
//		try (Connection connection = DBUtils.getInstance().getConnection();
//				Statement statement = connection.createStatement();
//				ResultSet resultSet = statement.executeQuery("SELECT * FROM order_items");) {
//			List<OrderItem> orderItems = new ArrayList<>();
//			while (resultSet.next()) {
//				orderItems.add(modelFromResultSet(resultSet));
//			}
//			return orderItems;
//		} catch (SQLException e) {
//			LOGGER.debug(e);
//			LOGGER.error(e.getMessage());
//		}
//		return new ArrayList<>();
//	}
	
	
	@Override
	public OrderItem read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM order_items WHERE id = ?");) {
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
	public OrderItem create(OrderItem orderItem) {
		OrderDAO order = new OrderDAO();
		Order latest = order.readLatest();
		
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO order_items (orderId, itemId, itemName, cost, numItems)"
								+ "VALUES ( ?,?,?,?,?)");) {
			statement.setLong(1,  latest.getId()); 
			statement.setLong(2, orderItem.getItemId());
			statement.setString(3,  orderItem.getItemName());
			statement.setLong(4,  orderItem.getCost());
			statement.setLong(5,  orderItem.getNumItems());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e); 
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public OrderItem readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM order_items ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override // made my own for this one 
	public OrderItem update(OrderItem orderItem) {
			return null;
		}

	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM order_items WHERE id = ?");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}
	
	public void deleteFromOrder(long id, long itemId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM order_items WHERE orderId = ? AND itemId=?");) {
			statement.setLong(1, id);										// this works in MySQL workbench
			statement.setLong(2,  itemId);
			statement.executeUpdate();
			System.out.println("Item deleted"); // getting here - yes // but it then doesnt go anywhere else
			
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
	}
	// this needs to delete from order if the orderitems is null!
	

	public OrderItem updateOrder(OrderItem orderItem) {
		Long id = orderItem.getOrderId();
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement(("UPDATE order_items SET itemId = ?, numItems = ? WHERE orderId = "+ id));){
			statement.setLong(1, orderItem.getItemId());
			statement.setLong(2,  orderItem.getNumItems());
			//statement.setLong(3,  orderItem.getOrderId());
			statement.executeUpdate();
			return read(orderItem.getOrderId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	public boolean ifExists(Long id) { // a problem here that means the whole order is getting deleted even if there is still something in it 
		boolean bool = false;
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT EXISTS(SELECT * FROM order_items WHERE orderId=?)");) {
		 statement.setLong(1, id);
		 ResultSet num = statement.executeQuery(); // this is the issue - need to get an int out not a result set
		// System.out.println(num.getFetchSize()); // its printing 0 here! but should be 1 as there is still something in there 
		 if (num.next()) {
			 bool = true;
		 }else if (!num.next()) {
			 bool = false;
//		 if (num.getFetchSize() > 0 ) {  This didn't work so I did what is currently working. 
//			 bool = true;
//			 System.out.println("TRUE");
//		 } else if (num.getFetchSize() == 0) {
//			 bool = false;
//			 System.out.println("FALSE");
			 }
			 } catch (Exception e) {
				 LOGGER.debug(e);
					LOGGER.error(e.getMessage());
			 }
			 return bool;		
	}
	
	
}




	

