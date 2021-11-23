package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class CustomerTest {
	Customer customer;
	
	@Before
	public void setup() {
	customer = new Customer("Katie", "Diggory");
	}
//what is this?
	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Customer.class).verify();
	}
	
	@Test
	public void constructorTest() {
		Customer test1 = new Customer("Katie", "Diggory");
		Customer test2 = new Customer("Katie", "Diggory");
		assertEquals(test1, test2);
	}
	@Test
	public void constructorTest2() {
		Customer test1 = new Customer(1L,"Katie", "Diggory");
		Customer test2 = new Customer(1L,"Katie", "Diggory");
		assertEquals(test1, test2);
		
	}
	@Test
	public void getIdTest() { // not working yet - but must work as getId used in other tests..
		customer.setId(1L); // works when you set id first
		Long expected = customer.getId();
		Long test = 1L;
		assertEquals(expected, test); 
	}
	
	@Test
	public void setIdTest() {		
		customer.setId(2L);
		long expected = customer.getId();
		long test = 2L;
		assertEquals(expected, test); 
	}
	
	@Test
	public void getFirstNameTest() {
		String expected = customer.getFirstName();
		String test = "Katie";
		assertEquals(expected, test); 
	}
	
	@Test
	public void setFirstNameTest() {
		customer.setFirstName("Katherine");
		String expected = customer.getFirstName();
		String test = "Katherine";
		assertEquals(expected, test);
	}
	@Test
	public void getSurnameTest() {
		String expected = customer.getSurname();
		String test = "Diggory";
		assertEquals(expected, test); 
	}
	@Test
	public void setSurnameTest() {
		customer.setSurname("Diggory!");
		String expected = customer.getSurname();
		String test = "Diggory!";
		assertEquals(expected, test);
	}
	@Test
	public void toStringTest() {
		String expected = "id:" + customer.getId() + " first name:" + 
	customer.getFirstName() + " surname:" + customer.getSurname();
		assertEquals(expected, customer.toString());
	}
	
	

}
