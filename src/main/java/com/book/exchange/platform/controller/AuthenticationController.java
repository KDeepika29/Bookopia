package com.book.exchange.platform.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.book.exchange.platform.model.AuthResponse;
import com.book.exchange.platform.model.HelloWorld;
import com.book.exchange.platform.model.User;
import com.book.exchange.platform.service.AuthenticationService;
import com.book.exchange.platform.utils.JwtTokenGenerator;

@Path("/")
public class AuthenticationController {
	
	private AuthenticationService authenticationService= new AuthenticationService();

	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	public Response helloWorld() {

		HelloWorld helloWorld = new HelloWorld("Hello World !");

		return Response.ok(helloWorld).build();
	}
	
	@POST
	@Path("/auth/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user) {
		AuthResponse response = authenticationService.register(user);
		if (response != null) {
			return Response.status(201).entity(response).build();
		} else {
			return Response.status(400).entity("User already exists").build();
		}

	}
	
	@POST
	@Path("/auth/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user) {
		AuthResponse response = authenticationService.login(user);
		if (response != null) {
			return Response.status(200).entity(response).build();
		} else {
			return Response.status(404).entity("Invalid UserName/Password").build();
		}

	}
	
	@PUT
	@Path("/user/update-user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(User user) {
		AuthResponse response = authenticationService.updateUser(user);
		if (response != null) {
			return Response.status(200).entity(response).build();
		} else {
			return Response.status(500).entity("Failed to update user details").build();
		}

	}
	
	@GET
	@Path("/user/user-details")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserDetails(@Context HttpHeaders headers) {
		String token = headers.getHeaderString("token");
		String email = JwtTokenGenerator.getSubjectFromJwtToken(token);
		User response = authenticationService.getUserDetails(email);
		if (response != null) {
			return Response.status(200).entity(response).build();
		} else {
			return Response.status(500).entity("Failed to fetch user details").build();
		}

	}
}