package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ItemTest {
	Item item;
	
	@Before
	public void setup() {
		item = new Item("Wine", "1L", 10L);
	}
	
//	@Test
//	public void testEquals() {
//		EqualsVerifier.simple().forClass(Item.class).verify();
//	}
	
	@Test
	public void constructorTest() {
		Item test1 = new Item("Wine", "1L", 10L);
		Item test2 = new Item("Wine", "1L", 10L);
		assertEquals(test2, test1); // not working for some reason! 
	}
	
	@Test
	public void constructorTest2() {
		Item test1 = new Item(2L, "Wine", "1L", 10L);
		Item test2 = new Item(2L, "Wine", "1L", 10L);
		assertEquals(test2, test1);  // not working for some reason! 
	}
	
	@Test
	public void getIdTest() { // not working yet
		item.setId(1L); // have to set the id first before it is happy to get it for this one?!! why?
		Long expected = item.getId();
		Long test = 1L;
		assertEquals(expected, test); 
	}
	@Test
	public void setIdTest() {
		item.setId(2L);
		long expected = item.getId();
		long test = 2L;
		assertEquals(expected, test); 
	}
	@Test
	public void getNameTest() {
		String expected = item.getName();
		String test = "Wine";
		assertEquals(expected, test); 
	}
	@Test
	public void setNameTest() {
		item.setName("Fancy wine");
		String expected = item.getName();
		String test = "Fancy wine";
		assertEquals(expected, test);
	}
	
	@Test
	public void getSizeTest() {
		String expected = item.getSize();
		String test = "1L";
		assertEquals(expected, test);
	}
	
	@Test
	public void setSizeTest() {
		item.setSize("5L");
		String expected = item.getSize();
		String test = "5L";
		assertEquals(expected, test);
	}
	
	@Test
	public void getCostTest() {
		long expected = item.getCost();
		long test = 10L;
		assertEquals(expected, test); 
	}
	
	@Test
	public void setCostTest() {
		item.setCost(15L);
		long expected = item.getCost();
		long test = 15L;
		assertEquals(expected, test); 
	}
	@Test
	public void toStringTest() {
		String expected = "Item [id=" + item.getId() + ", name=" + item.getName() + ", "
				+ "size=" + item.getSize() + ", cost=" + item.getCost() + "]";
		assertEquals(expected, item.toString());
	}
}
