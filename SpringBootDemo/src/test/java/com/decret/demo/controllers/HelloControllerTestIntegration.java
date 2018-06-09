package com.decret.demo.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.decret.demo.controllers.HelloController;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)

public class HelloControllerTestIntegration {

	@Autowired
	WebApplicationContext context;
	MockMvc mockMvc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTestSayHello() throws Exception {
		mockMvc.perform(get("/hello").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(view().name("hello")).andExpect(model().attribute("user", is("World")));
	}

	@Test
	public void testTestSayHelloWithParam() throws Exception {

		mockMvc.perform(get("/hello").param("name", "Ajay").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(view().name("hello")).andExpect(model().attribute("user", is("Ajay")));

	}

}
