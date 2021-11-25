package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

public class ItemController implements CrudController<Item> {

	public static final Logger LOGGER = LogManager.getLogger();

	private ItemDAO itemDAO;
	private Utils utils;

	public ItemController(ItemDAO itemDAO, Utils utils) {
		super();
		this.itemDAO = itemDAO;
		this.utils = utils;
	}
	
	//reads all items to the logger
	@Override
	public List<Item> readAll() {
		List<Item> items = itemDAO.readAll();
		for (Item item : items) {
			LOGGER.info(item);
		}
		return items;
	}
	// creates an item - 3 parameters, name size and cost
	@Override
	public Item create() {
		LOGGER.info("Please enter the item name");
		String name = utils.getString();
		LOGGER.info("Please enter the item size");
		String size = utils.getString();
		LOGGER.info("Please enter the cost");
		Long cost = utils.getLong();
		Item item = itemDAO.create(new Item(name, size, cost));
		// need an if statement to determine if this has actually happened
		LOGGER.info("Item created");
		return item;
	}

	//updates the item by id
	@Override
	public Item update() {
		LOGGER.info( itemDAO.getItemNums() );
		LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LOGGER.info("Please enter the id of the item you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter the new item name");
		String name = utils.getString();
		LOGGER.info("Please enter the new item size");
		String size = utils.getString();
		LOGGER.info("Please enter the new cost");
		Long cost = utils.getLong();
		Item item = itemDAO.update(new Item(id, name, size, cost));
		LOGGER.info("Item updated");
		return item;
	}
	// deletes an item by id
	@Override
	public int delete() {
		LOGGER.info(itemDAO.getItemNums() );
		LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LOGGER.info("Please enter the id of the item you would like to delete");
		Long id = utils.getLong();
		return itemDAO.delete(id);
	}


	@Override
	public Item readById() {
		LOGGER.info( itemDAO.getItemNums() );
		LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LOGGER.info("Please enter the id of the item you would like to find");
		Long id = utils.getLong();
		Item item = itemDAO.read(id);
		LOGGER.info(item);
		return item;
	}



}