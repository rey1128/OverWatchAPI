package com.rey.controller;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

public class HeroController {
	private Vertx vertx;
	private Logger logger=LoggerFactory.getLogger(HeroController.class);

	public HeroController(Vertx myVertx) {
		this.vertx = myVertx;
	}

	public void getHeros(RoutingContext context) {
		context.response().end("getHeros");
	}

	public void getHerosById(RoutingContext context) {
		context.response().end("getHerosById");
	}

	public void getHeroAbilities(RoutingContext context) {
		context.response().end("getHeroAbilities");
	}
}
