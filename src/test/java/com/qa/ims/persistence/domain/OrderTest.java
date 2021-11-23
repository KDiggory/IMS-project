package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {
	
	Order order;
	
	@Before
	public void setup() {
		order = new Order(1L);
	}
	
	@Test
	public void constructorTest() {
		Order test1 = new Order(1L);
		Order test2 = new Order(1L);
		assertEquals(test2, test1);
	}
	@Test
	public void constructorTest2() {
		Order test1 = new Order(1L,2L);
		Order test2 = new Order(1L,2L);
		assertEquals(test2, test1);
	}
	@Test
	public void getIdTest() {
			order.setId(1L); // have to set the id first before it is happy to get it for this one?!! why?
			Long expected = order.getId();
			Long test = 1L;
			assertEquals(expected, test); 
	}
	@Test
	public void setIdTest() {
		order.setId(2L);
		long expected = order.getId();
		long test = 2L;
		assertEquals(expected, test); 
	}
	@Test
	public void getCustomerIdTest() {
		//	order.setCustomerId(1L); // have to set the id first before it is happy to get it for this one?!! why?
			Long expected = order.getCustomerId();
			Long test = 1L;
			assertEquals(expected, test); 
	}
	@Test
	public void setCustomerIdTest() {
		order.setCustomerId(2L);
		long expected = order.getCustomerId();
		long test = 2L;
		assertEquals(expected, test); 
	}
	@Test
	public void toStringTest() {
		String expected = "Order [id=" + order.getId() + ", customerId=" + order.getCustomerId() + "]";
		assertEquals(expected, order.toString());
	}

}
