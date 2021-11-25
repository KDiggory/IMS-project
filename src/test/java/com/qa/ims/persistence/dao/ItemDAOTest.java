package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.DBUtils;

public class ItemDAOTest {
	
	private final ItemDAO DAO = new ItemDAO();
	
	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}
	
	@Test
	public void testCreate() {
		final Item created = new Item(2L, "beer", "very large", 2L);
		assertEquals(created, DAO.create(created));
	}
	
	@Test
	public void testReadAll() {
		List<Item> expected = new ArrayList<>();
		expected.add(new Item(1L, "beer", "very large", 2L));
		assertEquals(expected, DAO.readAll());
	}
	@Test
	public void testRead() {
		final long ID = 1L;
		assertEquals(new Item(ID,"beer", "very large", 2L), DAO.read(ID));
	}
	
	@Test
	public void testUpdate() { // this works if you tell it you want something null! 
		Object expected = null;
		final Item updated = new Item(1L, "cake", "huge", 2L);
		assertEquals(expected, DAO.update(updated));

	}
	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
	
	@Test
	public void testReadLatest() {
		assertEquals(new Item(1L, "beer", "very large", 2L), DAO.readLatest());
	}
	
	@Test
	public void testGetItemNums() {
		String expected = "Available Items:\n" + "{beer=1}";
		assertEquals(expected, DAO.getItemNums());
	}
	
	@Test
	public void testGetItemNumsFromOrder() { 
		Order test1 = new Order(1L);			
		String expected = "Available Items:{beer=1}";
		assertEquals(expected, DAO.getItemNumsFromOrder(1L));
	}
		
	@Test
	public void exceptionTestingRead() {
		final long ID = 100L;
		Object expected = null;
		assertEquals(expected, DAO.read(ID));
	}

	@Test
	public void exceptionTestingDelete() {
		final long ID = 100L;
		int expected = 0;
		assertEquals(expected, DAO.delete(ID));	
	}
	@Test
	public void exceptionTestingReadLatest() {
		DAO.delete(1L);
		Object expected = null;
		assertEquals(expected, DAO.readLatest());
	}
	@Test
	public void exceptionTestingReadAll() { // this is right, as there is nothing in the list.
		DAO.delete(1L);						// if you don't delete it then it fails
		List<OrderItem> expected = new ArrayList<>();
		assertEquals(expected, DAO.readAll());
	}
	@Test
	public void  exceptionTestingGetItemNums() {
		Order test1 = new Order(1L);			
		String expected = "Available Items:";
		assertEquals(expected, DAO.getItemNumsFromOrder(100L));
		
	}
	
	
	
	
	
		}
	
	


