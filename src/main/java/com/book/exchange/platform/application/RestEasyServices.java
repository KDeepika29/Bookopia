package com.book.exchange.platform.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.book.exchange.platform.controller.AuthenticationController;
import com.book.exchange.platform.controller.BookController;

@ApplicationPath("/")
public class RestEasyServices extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public RestEasyServices() {
		singletons.add(new AuthenticationController());
		singletons.add(new BookController());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}