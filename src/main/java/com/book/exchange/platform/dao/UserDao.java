package com.book.exchange.platform.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.json.JSONObject;

import com.book.exchange.platform.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.DocResult;

@ApplicationScoped
public class UserDao {

	@Inject
	@Named("user")
	private Collection userCol;

	public void saveUserDetails(User user) {
		if (null != user) {
			userCol.add(new JSONObject(user).toString()).execute();
		}
	}

	public List<User> getUsersList() {
		List<User> userList = new ArrayList<>();
		DocResult docResult = userCol.find().execute();
		for (DbDoc doc : docResult.fetchAll()) {
			try {
				userList.add(new ObjectMapper().readValue(doc.toFormattedString(), User.class));
			} catch (IOException ex) {

			}
		}
		return userList;
	}
}
