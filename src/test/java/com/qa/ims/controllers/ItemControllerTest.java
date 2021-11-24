package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.ItemController;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest { 
	
	@Mock
	private Utils utils;

	@Mock
	private ItemDAO DAO;

	@InjectMocks
	private ItemController controller;
	
	@Test
	public void testCreate() { 
		final String name = "pie";
		final String size = "large";
		final Long cost = 5L;
		final Item created = new Item(name, size, cost);

		Mockito.when(utils.getString()).thenReturn(name, size);
		Mockito.when(utils.getLong()).thenReturn(cost);
		Mockito.when(DAO.create(created)).thenReturn(created);

		assertEquals(created, controller.create());

		Mockito.verify(utils, Mockito.times(2)).getString();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(DAO, Mockito.times(1)).create(created);
	}
	
	@Test 
	public void testReadAll() {
		List<Item> items = new ArrayList<>();
		items.add(new Item(1L, "cake", "cupcake", 1L));

		Mockito.when(DAO.readAll()).thenReturn(items);

		assertEquals(items, controller.readAll());

		Mockito.verify(DAO, Mockito.times(1)).readAll();
	}
	
	@Test
	public void testUpdate() { 
		Item updated = new Item(1L, "cake", "cupcake", 1L);

		Mockito.when(this.utils.getLong()).thenReturn(1L);
		Mockito.when(this.utils.getString()).thenReturn(updated.getName(), updated.getSize());
		Mockito.when(this.DAO.update(updated)).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.utils, Mockito.times(2)).getLong();
		Mockito.verify(this.utils, Mockito.times(2)).getString();
		Mockito.verify(this.DAO, Mockito.times(1)).update(updated);
	}
	
	@Test
	public void testDelete() {
		final long ID = 1L;
		

		Mockito.when(utils.getLong()).thenReturn(ID);
		Mockito.when(DAO.delete(ID)).thenReturn(1);

		assertEquals(1L, this.controller.delete());

		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(DAO, Mockito.times(1)).delete(ID);
	}
	
	@Test
	public void testReadById() {
		final long ID = 1L;
		Item testItem = new Item(1L, "cake", "cupcake", 1L);
		
		Mockito.when(utils.getLong()).thenReturn(ID);
		//Mockito.when(DAO.read(ID)).thenReturn(testItem.getId()); // this doesnt work
		

		Mockito.when(DAO.read(ID)).thenReturn(testItem);

		assertEquals(testItem, controller.readById());

		Mockito.verify(DAO, Mockito.times(1)).read(ID);
	}
		
	}
	


