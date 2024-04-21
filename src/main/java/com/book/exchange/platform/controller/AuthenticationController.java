package com.book.exchange.platform.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.book.exchange.platform.model.User;
import com.book.exchange.platform.service.IAuthenticationService;

@Path("/auth")
public class AuthenticationController {

	@Inject
	IAuthenticationService authenticationService;

	@POST
	@Path("/register")
	public Response register(User user) {
		String response = authenticationService.register(user);
		if (response != null) {
			return Response.status(200).entity(response).build();
		} else {
			return Response.status(400).entity("User already exists").build();
		}

	}

	@POST
	@Path("/login")
	public Response login(User user) {
		String response = authenticationService.login(user);
		if (response != null) {
			return Response.status(200).entity(response).build();
		} else {
			return Response.status(400).entity("Invalid user credentials").build();
		}
	}
	
	@GET
	@Path("/health")
	public String getHealth() {
		return "Health is up!!";
	}
}
