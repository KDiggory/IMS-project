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

import com.qa.ims.controller.OrderController;
import com.qa.ims.controller.OrderItemController;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
	
	@Mock 
	private Utils utils;

	@Mock
	private OrderDAO DAO;
	
	@InjectMocks
	private OrderController controller;
	
	@InjectMocks
	private OrderItemController oIcontroller;
	
	@Test
	public void testCreate() { // coming up null like the one for Item controller
		final Long id = 1L;
		final Long custId = 1L;
		final Order order = new Order(id, custId);

		Mockito.when(utils.getLong()).thenReturn(id, custId);
		Mockito.when(DAO.create(order)).thenReturn(order);

		assertEquals(order, controller.create());

		Mockito.verify(utils, Mockito.times(2)).getLong();
		Mockito.verify(DAO, Mockito.times(1)).create(order);
	}
	
	@Test 
	public void testReadAll() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(1L, 1L));

		Mockito.when(DAO.readAll()).thenReturn(orders);

		assertEquals(orders, controller.readAll());

		Mockito.verify(DAO, Mockito.times(1)).readAll();
	}
	
	@Test
	public void testUpdate() { // not working
		Order updated = new Order(1L, 2L);

		Mockito.when(this.utils.getLong()).thenReturn(updated.getId(), updated.getCustomerId());
		Mockito.when(this.DAO.update(updated)).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.utils, Mockito.times(2)).getLong();
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
		Order testOrder = new Order(1L, 1L);
		
		Mockito.when(utils.getLong()).thenReturn(ID);
		

		Mockito.when(DAO.read(ID)).thenReturn(testOrder);

		assertEquals(testOrder, controller.readById());

		Mockito.verify(DAO, Mockito.times(1)).readId(ID);
	}
	
	@Test
	public void testReadAllOrders() {
		
		
	}
	@Test
	public void testreadByCustomer(){
		
	}
	@Test
	public void testgetTotal() {
		
	}
	
	@Test
	public void testReadByIdInput() {
		
	}
	@Test
	public void testGetTotalOrder() {
		
	}
	@Test
	public void testReadIdTable() {
		
	}
	@Test
	public void testAddToOrder() {
		
	}
	@Test
	public void testRemoveFromOrder() {
		
	}
	

}
