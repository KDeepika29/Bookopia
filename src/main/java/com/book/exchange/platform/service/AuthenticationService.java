package com.book.exchange.platform.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.book.exchange.platform.dao.UserDao;
import com.book.exchange.platform.model.AuthResponse;
import com.book.exchange.platform.model.User;
import com.book.exchange.platform.utils.JwtTokenGenerator;

@ApplicationScoped
public class AuthenticationService {

	private List<User> usersList = new ArrayList<User>();

	UserDao userDao = new UserDao();

//	public AuthResponse register(User newUser) {
//
//		AuthResponse authResponse = new AuthResponse();
//		boolean userExists = false;
//		if (!usersList.isEmpty()) {
//			for (User users : usersList) {
//				if (newUser.getEmail().equalsIgnoreCase(users.getEmail())) {
//					userExists = true;
//				}
//			}
//		}
//		if (userExists) {
//			authResponse.setSuccess(false);
//			authResponse.setMessage("User already exists!!");
//		} else {
//			usersList.add(newUser);
//			authResponse.setSuccess(true);
//			authResponse.setMessage("User is registered successfully!!!");
//			authResponse.setToken(JwtTokenGenerator.generateJwtToken(newUser.getEmail(), 3600000));
//
//		}
//		return authResponse;
//	}
//
//	public AuthResponse login(User loginUser) {
//
//		AuthResponse authResponse = new AuthResponse();
//		boolean userExists = false;
//		String password = "", user = "";
//		if (!usersList.isEmpty()) {
//			for (User users : usersList) {
//				if (loginUser.getEmail().equalsIgnoreCase(users.getEmail())) {
//					userExists = true;
//					password = users.getPassword();
//					user = users.getName();
//				}
//			}
//		}
//		if (userExists && password.equalsIgnoreCase(loginUser.getPassword())) {
//			authResponse.setSuccess(true);
//			authResponse.setName(user);
//			authResponse.setMessage("User login is successful!!");
//			authResponse.setToken(JwtTokenGenerator.generateJwtToken(loginUser.getEmail(), 3600000));
//			return authResponse;
//		}
//		return null;
//	}
//
//	public AuthResponse updateUser(User loginUser) {
//
//		AuthResponse authResponse = new AuthResponse();
//		if (!usersList.isEmpty()) {
//			for (User users : usersList) {
//				if (loginUser.getEmail().equalsIgnoreCase(users.getEmail())) {
//					if (null != loginUser.getEmail()) {
//						users.setEmail(loginUser.getEmail());
//					}
//
//					if (null != loginUser.getName()) {
//						users.setName(loginUser.getName());
//					}
//					if (null != loginUser.getLocation()) {
//						users.setLocation(loginUser.getLocation());
//					}
//					if (null != loginUser.getPassword()) {
//						users.setPassword(loginUser.getPassword());
//					}
//					if (null != loginUser.getGenre()) {
//						users.setGenre(loginUser.getGenre());
//					}
//					if (null != loginUser.getPreferences()) {
//						users.setPreferences(loginUser.getPreferences());
//					}
//					break;
//				}
//			}
//		}
//
//		authResponse.setSuccess(true);
//		authResponse.setMessage("User update is successful!!");
//		return authResponse;
//	}
//
//	public User getUserDetails(String email) {
//		if (!usersList.isEmpty()) {
//			for (User users : usersList) {
//				if (email.equalsIgnoreCase(users.getEmail())) {
//					return users;
//				}
//			}
//		}
//		return null;
//	}

	public AuthResponse register(User newUser) {
		AuthResponse authResponse = new AuthResponse();

		// Check if the user already exists in the database
		User existingUser = userDao.getUserByEmail(newUser.getEmail());
		if (existingUser != null) {
			authResponse.setSuccess(false);
			authResponse.setMessage("User already exists!!");
		} else {
			// Save the new user to the database
			userDao.saveUser(newUser);

			authResponse.setSuccess(true);
			authResponse.setMessage("User is registered successfully!!!");
			authResponse.setToken(JwtTokenGenerator.generateJwtToken(newUser.getEmail(), 3600000));
		}

		return authResponse;
	}

	public AuthResponse login(User loginUser) {
		AuthResponse authResponse = new AuthResponse();

		// Get the user details by email from the database
		User user = userDao.getUserByEmail(loginUser.getEmail());
		if (user != null) {
			// Check if the passwords match
			if (user.getPassword().equals(loginUser.getPassword())) {
				authResponse.setSuccess(true);
				authResponse.setMessage("User login is successful!!");
				authResponse.setToken(JwtTokenGenerator.generateJwtToken(loginUser.getEmail(), 3600000));
			} else {
				authResponse.setSuccess(false);
				authResponse.setMessage("Invalid password");
			}
		} else {
			authResponse.setSuccess(false);
			authResponse.setMessage("User not found");
		}

		return authResponse;
	}
	public AuthResponse updateUser(User updatedUser) {
		AuthResponse authResponse = new AuthResponse();
		User existingUser = userDao.getUserByEmail(updatedUser.getEmail());

		if (existingUser != null) {
			// Update the user details
			existingUser.setName(updatedUser.getName());
			existingUser.setLocation(updatedUser.getLocation());
			existingUser.setPassword(updatedUser.getPassword());
			existingUser.setGenre(updatedUser.getGenre());
			existingUser.setPreferences(updatedUser.getPreferences());

			// Save the updated user details in the database using userDao
			userDao.updateUser(existingUser);

			authResponse.setSuccess(true);
			authResponse.setMessage("User details updated successfully!");
		} else {
			authResponse.setSuccess(false);
			authResponse.setMessage("User does not exist or could not be updated.");
		}

		return authResponse;
	}

	public User getUserDetails(String email) {
		return userDao.getUserByEmail(email);
	}
}
