package com.qa.ims.controller;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.JoinTable;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order>{
	
	public static final Logger LOGGER = LogManager.getLogger();

	private OrderDAO orderDAO;
	private CustomerDAO customerDAO;
	private ItemDAO itemDAO;
	private Utils utils;
	private OrderItemDAO orderItemDAO;

	public OrderController(OrderDAO orderDAO, Utils utils) {
		super();
		this.orderDAO = orderDAO;
		this.utils = utils;
	}
	

	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		for (Order order : orders) {
			LOGGER.info(order);
		}
		return orders;
	}
	
	public List<JoinTable> readAllOrders() {
		List<JoinTable> orders = orderDAO.readAllOrders();
		//System.out.println(orders.size());
		for (JoinTable order : orders) {
			LOGGER.info(order); // with this in as well everything is printed twice
		}
		return orders;
	}

	@Override
	public Order create() {
		LOGGER.info("Please enter the customer id");
		Long customerId = utils.getLong();
		Order order = orderDAO.create(new Order(customerId));
		OrderItemController orderItemController = new OrderItemController(orderItemDAO, utils);
		orderItemController.create();
		return order;
	}


	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id); 
	}
	
	public int deleteNoInput(Long id) {
		OrderDAO orderDAO = new OrderDAO();
		return orderDAO.delete(id); 
	}
	
	

	@Override
	public Order update() { // dont use this
//		LOGGER.info("Please enter the id of the order you would like to update");
//		Long id = utils.getLong();
//		LOGGER.info("Please enter updated customer id");
//		Long custId = utils.getLong();
//		Order order = orderDAO.update(new Order(id, custId));
//		OrderItemController orderItemController = new OrderItemController(orderItemDAO, utils);
//		orderItemController.updateOrder(order); // problem here 
		return null;
	}
	
	public Order readLatest() {
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.readLatest();
		return order;
		
	}
	
	@Override
	public Order readById() {
	LOGGER.info("Please enter the id of the order you would like to find");
	Long id = utils.getLong();
	JoinTable order = orderDAO.readId(id);
	LOGGER.info(order);
		return null;
	}
	
	public JoinTable readByIdInput(Long id) {
		JoinTable order = orderDAO.readId(id);
		LOGGER.info(order);
			return order;
		} 
	
	public List<JoinTable> readByCustomer(){
		int read = 1;
		System.out.println( orderDAO.getCustomerNums() );
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LOGGER.info("Please enter the customer id of the orders you would like to find");
		Long id = utils.getLong();
		List<JoinTable> orders = orderDAO.readByCustomer(id);
		// System.out.println("orders for customer: " + id); // could make it add the name here? - how to get this from OrderDAO? 
		for (JoinTable order : orders) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("\t\tItem number: " + read);
			LOGGER.info(order); 
			read ++; 
		}
		getTotal(id, read);
		return orders;	
	} 
	
	public Long getTotal(Long id, int read) {
		Long totalSum = orderDAO.totalCost(id);
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("\nThe total sum for customer number " + id + ",\nFor " + (read-1) + " orders: £" + totalSum);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		return totalSum;
		
	}
	
	public Long getTotalOrder(Long id, int read) {
		Long totalSum = orderDAO.totalCostByOrder(id);
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("\nThe total sum for order number " + id + ",\nFor " + (read-1) + " orders: £" + totalSum);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		return totalSum;
		
	}
	public List<JoinTable> readIdTable() {
		int read = 1;
		System.out.println(orderDAO.getOrderNums() );
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LOGGER.info("Please enter the order id you would like to find");
		Long id = utils.getLong();
		List<JoinTable> orders = orderDAO.readIdTable(id);
		for (JoinTable order : orders) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("\t\tItem number: " + read);
			LOGGER.info(order); 
			read ++;
		}
		getTotalOrder(id, read);  
		return orders;	
		 
		
	}
	public void addToOrder() {
		System.out.println(orderDAO.getOrderNums() );
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LOGGER.info("Please enter the order id you would like to add to");
		Long id = utils.getLong();
		OrderItemDAO orderItemDAO = new OrderItemDAO();
		OrderItemController controller = new OrderItemController(orderItemDAO, utils);
		controller.addToOrder(id);
	}


	public void removeFromOrder() { // This is where column name not found message is coming from?
		System.out.println(orderDAO.getOrderNums() );
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LOGGER.info("Please enter the order id you would like to remove from");
		Long id = utils.getLong();
		OrderItemDAO orderItemDAO = new OrderItemDAO();
		OrderItemController controller = new OrderItemController(orderItemDAO, utils);
		controller.removeFromOrder(id);
		
	}

}
