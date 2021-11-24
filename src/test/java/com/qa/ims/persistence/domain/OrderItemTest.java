package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class OrderItemTest {
	
	OrderItem orderItem;
	
	@Before
	public void setup() {
		orderItem = new OrderItem(1L,1L, "Wine",1L,5L);
	}
	
	@Test
	public void constructorTest() {
		OrderItem test1 = new OrderItem(1L,1L, "Wine",1L,5L);
		OrderItem test2 = new OrderItem(1L,1L, "Wine",1L,5L);
		assertEquals(test2, test1);
	}
	
	@Test
	public void constructorTest2() {
		OrderItem test1 = new OrderItem(1L,1L,1L, "Wine",1L,5L);
		OrderItem test2 = new OrderItem(1L, 1L,1L, "Wine",1L,5L);
		assertEquals(test2, test1);
	}
	
	@Test
	public void constructorTest3() {
		OrderItem test1 = new OrderItem(1L,1L,1L);
		OrderItem test2 = new OrderItem(1L,1L,1L);
		assertEquals(test2, test1);
	}
	
	@Test
	public void getIdTest() { // not working yet
		orderItem.setId(1L); // have to set the id first because constructor done without 
		Long expected = orderItem.getId();
		Long test = 1L;
		assertEquals(expected, test); 
	}
	@Test
	public void setIdTest() {
		orderItem.setId(2L);
		long expected = orderItem.getId();
		long test = 2L;
		assertEquals(expected, test); 
	}
	
	@Test
	public void getOrderIdTest() { 
		Long expected = orderItem.getOrderId();
		Long test = 1L;
		assertEquals(expected, test); 
	}
	@Test
	public void setOrderIdTest() {
		orderItem.setOrderId(2L);
		long expected = orderItem.getOrderId();
		long test = 2L;
		assertEquals(expected, test); 
	}
	@Test
	public void getItemIdTest() { 
		Long expected = orderItem.getItemId();
		Long test = 1L;
		assertEquals(expected, test); 
	}
	@Test
	public void setItemIdTest() {
		orderItem.setItemId(2L);
		long expected = orderItem.getItemId();
		long test = 2L;
		assertEquals(expected, test); 
	}
	@Test
	public void getItemNameTest() { 
		String expected = orderItem.getItemName();
		String test = "Wine";
		assertEquals(expected, test); 
	}
	@Test
	public void setItemNameTest() {
		orderItem.setItemName("Cake");
		String expected = orderItem.getItemName();
		String test = "Cake";
		assertEquals(expected, test); 
	}
	@Test
	public void getNumItemsTest() { 
		Long expected = orderItem.getNumItems();
		Long test = 1L;
		assertEquals(expected, test); 
	}
	@Test
	public void setNumItemsTest() {
		orderItem.setNumItems(2L);
		long expected = orderItem.getNumItems();
		long test = 2L;
		assertEquals(expected, test); 
	}
	@Test
	public void getCostTest() { 
		Long expected = orderItem.getCost();
		Long test = 5L;
		assertEquals(expected, test); 
	}
	@Test
	public void setCostTest() {
		orderItem.setCost(2L);
		long expected = orderItem.getCost();
		long test = 2L;
		assertEquals(expected, test); 
	}
	
	@Test
	public void toStringTest() {
		String expected = "OrderItem [id=" + orderItem.getId() + ", orderId=" + orderItem.getOrderId() + 
				", itemId=" + orderItem.getItemId() + ", itemName=" + orderItem.getItemName()
				+ ", numItems=" + orderItem.getNumItems() + ", cost=" + orderItem.getCost() + "]";
		assertEquals(expected, orderItem.toString());
	}

}
