package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.DBUtils;

public class OrderItemDAOTest {
	
private final OrderItemDAO DAO = new OrderItemDAO();
	
	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}
	
	@Test
	public void testCreate() {
		final OrderItem created = new OrderItem(1L, 1L, "beer", 2L, 2L); // coming up as order 60 in the one tested against - increments each time you do it?!
		assertEquals(created, DAO.create(created));							// was because on deleting tables in sql schema was put wrong, moved those with most fk to top and it was sorted
	}
	
	@Test
	public void testReadAll() {
		List<OrderItem> expected = new ArrayList<>();
		expected.add(new OrderItem(1L, 1L, "beer", 2L, 2L));
		assertEquals(expected, DAO.readAll());
	}
	
	@Test
	public void testRead() {
		final long ID = 1L;
		assertEquals(new OrderItem(ID,1L, "beer", 2L, 2L), DAO.read(ID));
	}
	
	
//	@Test 
//	public void testDeleteFromOrder() { // why is this like this??
//		assertEquals(1, DAO.deleteFromOrder(1L, 1L));
//		
//	}
	
	@Test
	public void updateOrder() {
		OrderItem item = new OrderItem(1L, 1L,"beer", 1L, 2L);
		assertEquals(item, DAO.updateOrder(item));
		}
	
	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
	
	@Test
	public void testReadLatest() {
		assertEquals(new OrderItem(1L, 1L, "beer", 2L, 2L), DAO.readLatest());
	}
	@Test
	public void testfExists() {
		boolean bool = true;
		assertEquals(bool, DAO.ifExists(5L));
		boolean bool2 = false;
		assertNotEquals(bool2, DAO.ifExists(5L)); 
	}
	
	
}
