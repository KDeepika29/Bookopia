package com.book.exchange.platform.dbconnection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Produces;

import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.Session;

@RequestScoped
public class DBCollection {
@Inject
private Session session;

@Produces
@RequestScoped
@Named("user")
public Collection getUserCol() {
	return session.getDefaultSchema().getCollection("user");
}
}
