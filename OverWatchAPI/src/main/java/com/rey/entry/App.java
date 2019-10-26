package com.rey.entry;

import com.rey.verticle.WebVerticle;

import io.vertx.core.Vertx;

public class App {
	
	public static void main(String[] args) {
		Vertx vertx=Vertx.vertx();
		vertx.deployVerticle(WebVerticle.class.getName());
	}

}
