package com.decret.demo.controllers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.validation.support.BindingAwareConcurrentModel;

import com.decret.demo.controllers.HelloController;

public class HelloControllerTest {

	@Test
	public void testSayHello() throws Exception {

		HelloController controller = new HelloController();
		String view = controller.sayHello("Ajay", new BindingAwareConcurrentModel());
		assertEquals("hello", view);
	}

}
