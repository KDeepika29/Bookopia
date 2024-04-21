package com.book.exchange.platform.service;

import com.book.exchange.platform.model.User;

public interface IAuthenticationService {
	String register(User user);

	String login(User user);
}
