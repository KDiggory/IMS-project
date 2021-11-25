package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.OrderController;
import com.qa.ims.controller.OrderItemController;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemControllerTest {
	
	@Mock
	private Utils utils;

	@Mock
	private OrderItemDAO DAO;
	
	@Mock
	private OrderDAO orderDAO;
	
	@InjectMocks
	private OrderItemController controller;
	
	@InjectMocks
	private OrderController orderController;
	
	@Test // not working
	public void testCreate() { // problem with asking for entry as is saying answer is null
	final Item item = new Item("beer", "large", 1L);
	final Customer customer = new Customer(1L, "katie", "diggory");
	final Order order = new Order(1L, 1L);
	final OrderItem created = new OrderItem(1L, 1L, "beer", 1L, 1L);

	Mockito.when(utils.getLong()).thenReturn(1L);
	Mockito.when(DAO.create(created)).thenReturn(created); 

	assertEquals(created, controller.create());

	Mockito.verify(utils, Mockito.times(2)).getString();
	Mockito.verify(DAO, Mockito.times(1)).create(created);
	}
		
	
	@Test
	public void testUpdate() { // failing as returned item is null
		OrderItem updated = new OrderItem(1L, 1L, "cake", 1L, 1L);

		Mockito.when(this.utils.getLong()).thenReturn(1L);
		Mockito.when(this.utils.getString()).thenReturn(updated.getItemName());
		Mockito.when(this.DAO.update(updated)).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(1)).getString();
		Mockito.verify(this.DAO, Mockito.times(1)).update(updated);
	}
		
	
	@Test
	public void testDelete() { // same problem about answer bring null
		final long ID = 1L;

		Mockito.when(utils.getLong()).thenReturn(ID);
		Mockito.when(DAO.delete(ID)).thenReturn(1);

		assertEquals(1L, this.controller.delete());

		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(DAO, Mockito.times(1)).delete(ID);
	}
		
	
	@Test
	public void testReadById() { // same problem about answer bring null
		final long ID = 1L;
		OrderItem testOrder = new OrderItem(1L, 1L, "cake", 1L, 1L);
		
		Mockito.when(utils.getLong()).thenReturn(ID);

		Mockito.when(DAO.read(ID)).thenReturn(testOrder);

		assertEquals(testOrder, controller.readById());

		Mockito.verify(DAO, Mockito.times(1)).read(1L);
		
	}
	
	

}
