package com.rey.controller;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

public class AbilityController {
	private Vertx vertx;
	private Logger logger = LoggerFactory.getLogger(AbilityController.class);

	public AbilityController(Vertx myVertx) {
		this.vertx = myVertx;
	}

	public void getAbility(RoutingContext context) {
		context.response().end("getAbility");
	}

	public void getAbilityById(RoutingContext context) {
		context.response().end("getAbilityById");
	}

}
