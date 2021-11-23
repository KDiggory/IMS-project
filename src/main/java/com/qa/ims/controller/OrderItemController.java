package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.Utils;

public class OrderItemController implements CrudController<OrderItem> {

	public static final Logger LOGGER = LogManager.getLogger();

	private OrderDAO orderDAO;
	private CustomerDAO customerDAO;
	private ItemDAO itemDAO;
	private OrderItemDAO orderItemDAO;
	private Utils utils;

	public OrderItemController(OrderItemDAO orderItemDAO, Utils utils) {
		super();
		this.utils = utils;
		this.orderItemDAO = orderItemDAO;
	}

	@Override
	public List<OrderItem> readAll() {
		List<OrderItem> orderItems = orderItemDAO.readAll();
		for (OrderItem orderItem : orderItems) {
			LOGGER.info(orderItem);
		}
		return orderItems;
	}

	@Override
	public OrderItem create() {
		OrderDAO orderDAO = new OrderDAO();
		CustomerDAO customerDAO = new CustomerDAO();
		ItemDAO itemDAO = new ItemDAO();
		OrderItemDAO orderItemDAO = new OrderItemDAO();

		OrderController orderController = new OrderController(orderDAO, utils);
		Order order = orderController.readLatest();
		Long orderId = order.getId();

		LOGGER.info("Please enter the Item id");
		Long itemId = utils.getLong();
		LOGGER.info("Please enter the number of this item you would like");
		Long numItems = utils.getLong();

//		Long custId = order.getCustomerId();
		Item item = itemDAO.read(itemId);
		String itemName = item.getName();
		Long cost = item.getCost();
		cost = cost * numItems;
		OrderItem orderItem = orderItemDAO.create(new OrderItem(orderId, itemId, itemName, numItems, cost));
		LOGGER.info("Order Item created");
		return orderItem;
	}

	@Override
	public OrderItem update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return 0;
	}

}
