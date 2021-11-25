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
		
		// always accessed through order create
		OrderDAO orderDAO = new OrderDAO();		//move these to the top of the class 
		CustomerDAO customerDAO = new CustomerDAO();
		ItemDAO itemDAO = new ItemDAO();
		OrderItemDAO orderItemDAO = new OrderItemDAO();

		OrderController orderController = new OrderController(orderDAO, utils);
		Order order = orderController.readLatest();
		Long orderId = order.getId();
		
		LOGGER.info(itemDAO.getItemNums());  // prints out the item numbers available

		LOGGER.info("Please enter the Item id");
		Long itemId = utils.getLong();
		LOGGER.info("Please enter the number of this item you would like");
		Long numItems = utils.getLong();

		Long custId = order.getCustomerId();
		Item item = itemDAO.read(itemId);
		String itemName = item.getName();
		Long cost = item.getCost();
		//System.out.println(cost);
		Long costTotal = cost * numItems;
		//System.out.println(costTotal);
		OrderItem orderItem = orderItemDAO.create(new OrderItem(orderId, itemId, itemName, numItems, costTotal)); // this is WAS the problem
		LOGGER.info("Order Item created");
		//LOGGER.info(orderItemDAO.readLatest()); // add an option to add more items here - reference add to order method
		LOGGER.info("Would you like to add another item to this order?");
		String answer = utils.getString();
		if (answer.contains("y")){
			addToOrder(orderId);
		} else if (answer.contains("n")) {
			LOGGER.info("You can come back and do this later if you change your mind");
		}
		return orderItemDAO.readLatest();
	}

	public OrderItem addToOrder(Long id) {
		OrderDAO orderDAO = new OrderDAO();		//move these to the top of the class 
		CustomerDAO customerDAO = new CustomerDAO();
		ItemDAO itemDAO = new ItemDAO();
		OrderItemDAO orderItemDAO = new OrderItemDAO();

		OrderController orderController = new OrderController(orderDAO, utils);
		JoinTable order = orderController.readByIdInput(id); 
		LOGGER.info(itemDAO.getItemNums());
		LOGGER.info("Please enter the Item id");
		Long itemId = utils.getLong();
		LOGGER.info("Please enter the number of this item you would like");
		Long numItems = utils.getLong();
		Long custId = order.getCustomerId(); // this is not working because order is null!
		Item item = itemDAO.read(itemId); 
		String itemName = item.getName();
		Long cost = item.getCost();
		Long costTotal = cost * numItems;
		OrderItem orderItem = orderItemDAO.create(new OrderItem(id, itemId, itemName, numItems, costTotal)); // this is WAS the problem
		LOGGER.info("Order Item created");
		
		LOGGER.info("Would you like to add another item to this order?");
		String answer = utils.getString();
		if (answer.contains("y")){
			addToOrder(id);
		} else if (answer.contains("n")) {
			LOGGER.info("You can come back and do this later if you change your mind");
		}
		
		  
		return null;
		//add another item to the order - create but dont use the read latest id for order - input the order id
		// print the available ids - makes it easier for customer to choose
	}
	
	public OrderItem removeFromOrder(Long id) {
		OrderController orderController = new OrderController(orderDAO, utils);
		ItemDAO itemDAO = new ItemDAO(); 
		
		LOGGER.info(itemDAO.getItemNumsFromOrder(id));  
		LOGGER.info("Please enter the id of the item you would like to remove");
		Long itemId = utils.getLong();
		orderItemDAO.deleteFromOrder(id,itemId); // problem here - sql syntax problem
		LOGGER.info(itemDAO.getItemNumsFromOrder(id));  
		if (orderItemDAO.ifExists(id) == false) { // problem is here - not deleting order if empty
			orderController.deleteNoInput(id);	
			System.out.println("in if statement"); 
		} else {
			
		}
		return null;
	}
	
	public OrderItem updateOrder(Order order) {
		OrderDAO orderDAO = new OrderDAO();
		CustomerDAO customerDAO = new CustomerDAO();
		ItemDAO itemDAO = new ItemDAO();
		OrderItemDAO orderItemDAO = new OrderItemDAO();
		LOGGER.info(itemDAO.getItemNums());
		OrderController orderController = new OrderController(orderDAO, utils);
		LOGGER.info("Please enter updated item id");
		Long itemId = utils.getLong();
		LOGGER.info("Please enter number of these you would like");
		Long numItems = utils.getLong();
		Long id = order.getId();
		OrderItem orderItem = orderItemDAO.updateOrder(new OrderItem(id, itemId, numItems)); // problem here! - the customerId is updating but falling overt when it gets to changing the itemId
		//LOGGER.info(orderItemDAO.read(orderItem.getOrderId())); // problem is now here - removing this line means it doesn't throw an exception just an sql error
		return null;
	}

	
	// Update order is not working - it changes the customer Id but not the rest. Currently it is saying its an illegal operation on an empty result set even though there are things in there. Also need to set on cascase update if possible?
	@Override
	public int delete() { // this isn't required for order items as you delete from the order not the order item.
		return 0;
	}

	public OrderItem readById(Long id) {
		OrderItem orderItem = orderItemDAO.read(id);
		return orderItem;
	}
	
	// not needed for order items - had to make own because didnt make whole new set for order items as they arent accessed alone
	@Override
	public OrderItem readById() {
		return null;
	}
	
	// not needed for order items - had to make own because didnt make whole new set for order items as they arent accessed alone 
	@Override
	public OrderItem update() {
		return null;
	}

}
