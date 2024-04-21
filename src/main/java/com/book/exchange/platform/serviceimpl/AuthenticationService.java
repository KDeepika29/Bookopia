package com.book.exchange.platform.serviceimpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.book.exchange.platform.dao.UserDao;
import com.book.exchange.platform.model.User;
import com.book.exchange.platform.service.IAuthenticationService;


@ApplicationScoped
public class AuthenticationService implements IAuthenticationService{
	
	@Inject
	private UserDao userDao;

	@Override
	public String register(User user) {
		userDao.saveUserDetails(user);
		return "Registered Successfully";
	}

	@Override
	public String login(User user) {
		// TODO Auto-generated method stub
		return null;
	}


}
