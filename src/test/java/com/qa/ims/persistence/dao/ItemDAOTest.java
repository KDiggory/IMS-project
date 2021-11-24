package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.qa.ims.persistence.domain.Item;
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
	public void testUpdate() {
		final Item updated = new Item(1L, "cake", "huge", 2L);
		assertEquals(updated, DAO.update(updated));

	}
	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}
	
	@Test
	public void testReadLatest() {
		assertEquals(new Item(1L, "beer", "very large", 2L), DAO.readLatest());
	}
	
	@Test (expected=Exception.class) // How to get this to work?
	public void exceptionTesting() {
		DAO.read(100L);
		
	}
	@Test
	public void exceptionTesting2() { // surely this shouldn't work??
		String exceptionMessage = "something";
		try {
			DAO.read(10L);
		}catch (Exception e) {
			assertEquals(e.getMessage(), exceptionMessage);
		}
	}
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test (expected = Exception.class)
    public void exceptionTesting3() {
		DAO.read(20L);
    }
		}
	
	


