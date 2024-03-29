package com.rey.entry;

import com.rey.verticle.DBVerticle;
import com.rey.verticle.WebVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class App {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle(DBVerticle.class.getName());
		vertx.deployVerticle(WebVerticle.class.getName(), (new DeploymentOptions()).setInstances(4));

	}

}
