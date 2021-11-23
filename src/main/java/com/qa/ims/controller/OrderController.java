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
		System.out.println(orders.toString());
		for (JoinTable order : orders) {
			//LOGGER.info(order); // with this in as well everything is printed twice
			
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

	@Override
	public Order update() {
		LOGGER.info("Please enter the id of the order you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter updated customer id");
		Long custId = utils.getLong();
		Order order = orderDAO.update(new Order(id, custId));
		OrderItemController orderItemController = new OrderItemController(orderItemDAO, utils);
		orderItemController.updateOrder(order); // problem here 
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

}
