package com.book.exchange.platform.dbconnection;

import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;

import com.book.exchange.platform.utils.ConfigUtils;
import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.ClientFactory;

@RequestScoped
public class DBClient {
	private Client mySqlClient;

	public Client getClient() {
		return mySqlClient;
	}

	public DBClient() {
		this.mySqlClient = new ClientFactory().getClient(getConnectionURL(), ConfigUtils.poolConfig);
		buildConnection();
	}

	public void buildConnection() {
		this.mySqlClient.getSession().getDefaultSchema().createCollection("user", true);

	}

	private String getConnectionURL() {
		String connectionURL = null;
		if (null != System.getenv("DB_CONNECTION_URL")) {
			connectionURL = System.getenv("DB_CONNECTION_URL");
		}
		return connectionURL;
	}

	@PreDestroy
	public void close() {
		if (mySqlClient != null) {
			mySqlClient.close();
		}
	}
}
