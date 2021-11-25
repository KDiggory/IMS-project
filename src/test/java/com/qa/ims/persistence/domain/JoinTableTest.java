package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class JoinTableTest {
	
	JoinTable joinTable;
	
	@Before
	public void setup() {
		joinTable = new JoinTable(1L, "Diggory", 1L, 1L, "cake", 1L, 1L, 1L);
	}
	@Test
	public void constructorTest() {		//this doesnt work - can't tell what the difference is from the trace though!
		JoinTable test1 = new JoinTable(1L, "Diggory", 1L, 1L, "cake", 1L, 1L, 1L);
		JoinTable test2 = new JoinTable(1L, "Diggory", 1L, 1L, "cake", 1L, 1L, 1L);
		assertEquals(test2, test1);
	}
	
	@Test
	public void getCustomerIdTest() {
		Long expected = joinTable.getCustomerId();
		Long test = 1L;
		assertEquals(expected, test);
	}
	
	@Test
	public void setCustomerIdTest() {
		joinTable.setCustomerId(5L);
		Long expected = joinTable.getCustomerId();
		Long test = 5L;
		assertEquals(expected, test);
	}
	@Test
	public void getCustomerSurnameTest() {
		String expected = joinTable.getCustomerSurname();
		String test = "Diggory";
		assertEquals(expected, test);
	}
	
	@Test
	public void setSizeTest() {
		joinTable.setCustomerSurname("Diggory!");
		String expected = joinTable.getCustomerSurname();
		String test = "Diggory!";
		assertEquals(expected, test);
	}
	@Test
	public void getOrderIdTest() {
		Long expected = joinTable.getOrderId();
		Long test = 1L;
		assertEquals(expected, test);
	}
	
	@Test
	public void setOrderIdTest() {
		joinTable.setOrderId(5L);
		Long expected = joinTable.getOrderId();
		Long test = 5L;
		assertEquals(expected, test);
	}
	@Test
	public void getItemIdTest() {
		Long expected = joinTable.getItemId();
		Long test = 1L;
		assertEquals(expected, test);
	}
	
	@Test
	public void setItemIdTest() {
		joinTable.setItemId(5L);
		Long expected = joinTable.getItemId();
		Long test = 5L;
		assertEquals(expected, test);
	}
	@Test
	public void getItemNameTest() {
		String expected = joinTable.getItemName();
		String test = "cake";
		assertEquals(expected, test);
	}
	
	@Test
	public void setItemNameTest() {
		joinTable.setItemName("Pies!");
		String expected = joinTable.getItemName();
		String test = "Pies!";
		assertEquals(expected, test);
	}
	@Test
	public void getItemCostTest() {
		Long expected = joinTable.getItemCost();
		Long test = 1L;
		assertEquals(expected, test);
	}
	
	@Test
	public void setItemCostTest() {
		joinTable.setItemCost(5L);
		Long expected = joinTable.getItemCost();
		Long test = 5L;
		assertEquals(expected, test);
	}
	@Test
	public void getNumItemsTest() {
		Long expected = joinTable.getNumItems();
		Long test = 1L;
		assertEquals(expected, test);
	}
	
	@Test
	public void setNumItemsTest() {
		joinTable.setNumItems(5L);
		Long expected = joinTable.getNumItems();
		Long test = 5L;
		assertEquals(expected, test);
	}
	@Test
	public void getTotalCostTest() {
		Long expected = joinTable.getTotalCost();
		Long test = 1L;
		assertEquals(expected, test);
	}
	
	@Test
	public void setTotalCostTest() {
		joinTable.setTotalCost(5L);
		Long expected = joinTable.getTotalCost();
		Long test = 5L;
		assertEquals(expected, test);
	}
	
	@Test
	public void EqualsTest() {
		JoinTable test1 = new JoinTable(1L, "Diggory", 1L, 1L, "cake", 1L, 1L, 1L);
		JoinTable test2 = new JoinTable(1L, "Diggory", 1L, 1L, "cake", 1L, 1L, 1L);
		assertTrue(test1.equals(test2) && test2.equals(test1));
		assertTrue(test1.hashCode()== test2.hashCode());
	}
	
	
}
