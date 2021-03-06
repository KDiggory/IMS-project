package com.qa.ims;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.controller.Action;
import com.qa.ims.controller.CrudController;
import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.ItemController;
import com.qa.ims.controller.OrderController;
import com.qa.ims.controller.OrderItemController;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.Domain;
import com.qa.ims.utils.DBUtils;
import com.qa.ims.utils.Utils;

public class IMS {

	public static final Logger LOGGER = LogManager.getLogger();

	private final CustomerController customers;
	private final ItemController items;
	private final OrderController orders;
	private final OrderItemController orderItems;
	private final Utils utils;

	public IMS() {
		this.utils = new Utils(); 
		final CustomerDAO custDAO = new CustomerDAO();
		final ItemDAO itemDAO = new ItemDAO();
		final OrderDAO orderDAO = new OrderDAO();
		final OrderItemDAO orderItemDAO = new OrderItemDAO();
		this.customers = new CustomerController(custDAO, utils);
		this.orders = new OrderController(orderDAO, utils);
		this.items = new ItemController(itemDAO, utils);
		this.orderItems = new OrderItemController(orderItemDAO, utils);
	}

	public void imsSystem() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LOGGER.info("\tWelcome to the Inventory Management System!");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		DBUtils.connect();

		Domain domain = null;
		do {
			LOGGER.info("\tWhich entity would you like to use?");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			Domain.printDomains();
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

			domain = Domain.getDomain(utils);

			domainAction(domain);

		} while (domain != Domain.STOP);
	}

	private void domainAction(Domain domain) {
		boolean changeDomain = false;
		do {

			CrudController<?> active = null;
			switch (domain) {
			case CUSTOMER:
				active = this.customers;
				break;
			case ITEM:
				active = this.items;
				break;
			case ORDER:
				active = this.orders;
				break;
			case STOP:
				return;
			default:
				break;
			}
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			LOGGER.info(() -> "\nWhat would you like to do with " + domain.name().toLowerCase() + ":");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

			Action.printActions();
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			Action action = Action.getAction(utils);

			if (action == Action.RETURN) {
				changeDomain = true;
			} else {
				doAction(active, action);
			}
		} while (!changeDomain);
	}

	public void doAction(CrudController<?> crudController, Action action) {
		switch (action) {
		case CREATE:
			crudController.create();
			break;
		case READ:
			LOGGER.info("Would you like to read all or by id?");
			String answer = utils.getString();
			if (answer.contains("all")) {
				if (crudController == this.orders ) {
					((OrderController) crudController).readAllOrders(); // need a different read for the orders
				} else if ((crudController != this.orders ))  {
					crudController.readAll();
			} 
				}else if (answer.contains("id")) {
					{if (crudController == this.orders ) {
						LOGGER.info("Would you like to read by order id or customer id?");
						String answer2 = utils.getString();
						if (answer2.contains("order")) {
							// here 
							
							((OrderController) crudController).readIdTable();	
					} else if (answer2.contains("customer")) {
						((OrderController) crudController).readByCustomer();	
					}
						
					} else {
						crudController.readById();
					}
				} 
			} 
			break;
		case UPDATE:
			if (crudController == this.orders ) {
				LOGGER.info("When updating your order you can just add or remove items, would you like to add or remove items from an order?");
				String answer3 = utils.getString();
				if (answer3.contains("add")) {
				((OrderController) crudController).addToOrder();
				} else if(answer3.contains("remove")) {
					((OrderController) crudController).removeFromOrder();
				}
			} else if ((crudController != this.orders ))  {
				crudController.update();
		} 
			break;
		case DELETE:
			crudController.delete();
			break;
		case RETURN:
			break;
		default:
			break;
		}
	

}
}
