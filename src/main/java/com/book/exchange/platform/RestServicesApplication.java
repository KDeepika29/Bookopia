package com.book.exchange.platform;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.book.exchange.platform.controller.AuthenticationController;

@ApplicationPath("/")
public class RestServicesApplication extends Application {
	private Set<Object> singletons = new HashSet<>();

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> serviceClasses = new HashSet<>();
		serviceClasses.add(AuthenticationController.class);
		return serviceClasses;
	}
}
