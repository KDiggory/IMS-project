package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.JoinTable;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAOTest {
	
	private final OrderDAO DAO = new OrderDAO();
	
	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}
	
	@Test
	public void testCreate() {
		final Order created = new Order(1L, 1L);
		assertEquals(created, DAO.create(created));
	}
	@Test
	public void testModelFromResultSet() { // no idea how to test this?! - would need to use mockito
		
	}
	@Test
	public void testModelFromResultSetJoin() { // no idea how to test this?! - would need to use mockito	
	}
	
	@Test
	public void testreadAllOrders() {
		Order order = new Order(3L, 1L);
		JoinTable entry = new JoinTable(1L, "harrison",1L, 1L, "beer",2L, 2L, 2L);
		 List<JoinTable> expected = new ArrayList<JoinTable>();
		 expected.add(entry);
		 assertEquals(expected, DAO.readAllOrders());
		 
		
	}
	@Test
	public void testreadId() { // cant see why this isnt working but apparently they arent the same - look identical
		JoinTable expected = new JoinTable(1L, "harrison",1L, 1L, "beer",2L, 2L, 2L);
		assertEquals(expected, DAO.readId(1L));
	}
	
	@Test
	public void testReadIdTable() { 
		Order order = new Order(3L, 1L);
		List<JoinTable> expected = new ArrayList<>();
		assertEquals(expected, DAO.readIdTable(3L));
	}
	
	@Test
	public void testReadByCustomer() { // not working, can't work out why! Looks exactly the same imo
		DAO.create(new Order(1L, 5L));
		String expected = "[\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n"
				+ "Order number:1\r\n"
				+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n"
				+ "customerId:		1\r\n"
				+ "customerSurname:	harrison\r\n"
				+ "itemId:			1\r\n"
				+ "itemName:		beer\r\n"
				+ "itemCost:		2\r\n"
				+ "numItems:		2\r\n"
				+ "totalCost:		2\r]";
		assertEquals(expected, DAO.readByCustomer(1L));
		
	}
	@Test
	public void testTotalCost() { // will need items? - no because it uses the sql data in test resources and assigns it
		DAO.create(new Order(1L, 5L));
		Long expected = 2L;
		assertEquals(2L, DAO.totalCost(1L)); 
	}											
	@Test 
	public void testTotalCostByOrder() { 
		DAO.create(new Order(1L, 5L));
		assertEquals(2L, DAO.totalCostByOrder(1L));
		
	}
	
	@Test
	public void testGetOrderNums() {
		DAO.create(new Order(1L, 5L));
		String expected = "Available order numbers:\n [1]";
		assertEquals(expected, DAO.getOrderNums());
		
		
	}
	@Test
	public void testGetCustomerNums() {
		DAO.create(new Order(1L, 5L));
		String expected = "Available customers with orders:[1]";
		assertEquals(expected, DAO.getCustomerNums());
		
	}
	
	@Test
	public void testReadLatest() {
		assertEquals(new Order(1L, 1L), DAO.readLatest());
	}
	@Test
	public void testRead() {
		final long ID = 3L;
		assertEquals(new Order(ID, 2L), DAO.read(ID));
	}
	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
	

}
