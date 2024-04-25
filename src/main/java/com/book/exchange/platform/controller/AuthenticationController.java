package com.book.exchange.platform.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.book.exchange.platform.model.AuthResponse;
import com.book.exchange.platform.model.HelloWorld;
import com.book.exchange.platform.model.User;
import com.book.exchange.platform.service.AuthenticationService;

@Path("/auth")
public class AuthenticationController {
	
	//@Inject
	private AuthenticationService authenticationService= new AuthenticationService();

	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	public Response helloWorld() {

		HelloWorld helloWorld = new HelloWorld("Hello World !");

		return Response.ok(helloWorld).build();
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user) {
		AuthResponse response = authenticationService.register(user);
		if (response != null) {
			return Response.status(200).entity(response).build();
		} else {
			return Response.status(400).entity("User already exists").build();
		}

	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user) {
		AuthResponse response = authenticationService.login(user);
		if (response != null) {
			return Response.status(200).entity(response).build();
		} else {
			return Response.status(400).entity("Login failed").build();
		}

	}
}