package com.rey.dao;

import io.vertx.core.Vertx;
import io.vertx.ext.jdbc.JDBCClient;

public class AbilityDao {
	private JDBCClient client;
	private Vertx vertx;

	public AbilityDao(Vertx myVertx, JDBCClient myClient) {
		this.vertx = myVertx;
		this.client = myClient;
	}
}
