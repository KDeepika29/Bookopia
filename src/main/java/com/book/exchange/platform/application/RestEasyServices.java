package com.book.exchange.platform.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import com.book.exchange.platform.controller.AuthenticationController;
import com.book.exchange.platform.controller.BookController;

@ApplicationPath("/")
public class RestEasyServices extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public RestEasyServices() {
		CorsFilter corsFilter = new CorsFilter();
		corsFilter.getAllowedOrigins().add("*");
		corsFilter.setAllowedMethods("OPTIONS,GET,POST,DELETE,PUT,PATCH");
		corsFilter.setAllowCredentials(true);
		corsFilter.setCorsMaxAge(200);
		singletons.add(corsFilter);
		singletons.add(new AuthenticationController());
		singletons.add(new BookController());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}