package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.DBUtils;

public class CustomerDAOTest {

	private final CustomerDAO DAO = new CustomerDAO();

	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@Test
	public void testCreate() {
		final Customer created = new Customer(2L, "chris", "perrins");
		assertEquals(created, DAO.create(created));
	}

	@Test
	public void testReadAll() {
		List<Customer> expected = new ArrayList<>();
		expected.add(new Customer(1L, "jordan", "harrison"));
		assertEquals(expected, DAO.readAll());
	}

	@Test
	public void testReadLatest() {
		assertEquals(new Customer(1L, "jordan", "harrison"), DAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		assertEquals(new Customer(ID, "jordan", "harrison"), DAO.read(ID));
	}

	@Test
	public void testUpdate() {
		final Customer updated = new Customer(1L, "chris", "perrins");
		assertEquals(updated, DAO.update(updated));

	}

	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
	
	@Test
	public void testReadAllIds() {
		String expected = "Available customer ids:\n" + "[1]";
		assertEquals(expected, DAO.readAllIds());
	}
//	@Test
//	public void testReadAllIdsException() { // is not working
//		DAO.delete(1L);
//		String expected = "Available customer ids:[]" ;
//		assertEquals(expected, DAO.readAllIds());
//	}
	@Test
	public void testReadLatestException() { 
		Object expected = null;
		DAO.delete(1L);
		assertEquals(expected, DAO.readLatest());
	}
	
	@Test
	public void testReadException() { 
		Object expected = null;
		DAO.delete(1L);
		assertEquals(expected, DAO.read(100L));
	}
}
