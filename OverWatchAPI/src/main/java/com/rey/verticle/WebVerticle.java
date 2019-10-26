package com.rey.verticle;

import com.rey.controller.AbilityController;
import com.rey.controller.HeroController;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

public class WebVerticle extends AbstractVerticle {
	private Logger logger = LoggerFactory.getLogger(WebVerticle.class);
	private HeroController heroController;
	private AbilityController abilityController;

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		super.start();

		Router router = Router.router(vertx);
		router.get("/").handler(context -> {
			context.response().end("this is index");
		});
		initControllers();
		router.get("/api/heros").handler(heroController::getHeros);
		router.get("/api/heros/:hero_id").handler(heroController::getHerosById);
		router.get("/api/heros/:hero_id/abilities").handler(heroController::getHeroAbilities);
		router.get("/api/abilities/").handler(abilityController::getAbility);
		router.get("/api/abilities/:ability_id").handler(abilityController::getAbilityById);

		vertx.createHttpServer().requestHandler(router::handle).listen(9999, hr -> {
			if (hr.succeeded()) {
				logger.info("server is listening at 9999...");
			} else {
				logger.error("error with starting server");
			}
		});
	}

	private void initControllers() {
		this.abilityController = new AbilityController(vertx);
		this.heroController = new HeroController(vertx);

	}
}
