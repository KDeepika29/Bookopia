package com.book.exchange.platform.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.book.exchange.platform.model.AuthResponse;
import com.book.exchange.platform.model.User;

@ApplicationScoped
public class AuthenticationService {

	private List<User> usersList = new ArrayList<User>();

	public AuthResponse register(User newUser) {

		AuthResponse authResponse = new AuthResponse();
		boolean userExists = false;
		if (!usersList.isEmpty()) {
			for (User users : usersList) {
				if (newUser.getEmail().equalsIgnoreCase(users.getEmail())) {
					userExists = true;
				}
			}
		}
		if (userExists) {
			authResponse.setSuccess(false);
			authResponse.setMessage("User already exists!!");
		} else {
			usersList.add(newUser);
			authResponse.setSuccess(true);
			authResponse.setMessage("User is registered successfully!!!");
			authResponse.setToken("abc");

		}
		return authResponse;
	}

	public AuthResponse login(User loginUser) {

		AuthResponse authResponse = new AuthResponse();
		boolean userExists = false;
		if (!usersList.isEmpty()) {
			for (User users : usersList) {
				if (loginUser.getEmail().equalsIgnoreCase(users.getEmail())) {
					userExists = true;
				}
			}
		}
		if (userExists) {
			authResponse.setSuccess(true);
			authResponse.setMessage("User login is successful!!");
			authResponse.setToken("abc");
		} else {
			authResponse.setSuccess(false);
			authResponse.setMessage("Invalid userName / password");

		}
		return authResponse;
	}
}
