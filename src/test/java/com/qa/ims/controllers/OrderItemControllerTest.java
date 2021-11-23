package com.qa.ims.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.OrderItemController;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemControllerTest {
	
	@Mock
	private Utils utils;

	@Mock
	private OrderItemDAO DAO;
	
	@InjectMocks
	private OrderItemController controller;
	
	@Test
	public void testCreate() {
		
	}
	@Test
	public void testUpdate() {
		
	}
	@Test
	public void testDelete() {
		
	}
	@Test
	public void testReadById() {
		
	}
	
	

}
