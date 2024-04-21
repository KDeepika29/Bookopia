package com.book.exchange.platform.dbconnection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;

import com.mysql.cj.xdevapi.Session;

@RequestScoped
public class DBSession {
	private Session session;
	@Inject
	DBClient dbConn;

	@Produces
	@RequestScoped
	public Session getSession() {
		return session;
	}

	@PreDestroy
	public void close() {
		session.close();
	}

	@PostConstruct
	public void init() {
		session = dbConn.getClient().getSession();
	}
}
