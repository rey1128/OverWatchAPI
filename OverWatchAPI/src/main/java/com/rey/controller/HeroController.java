package com.rey.controller;

import java.util.List;

import com.google.gson.Gson;
import com.rey.common.CommonConstants;
import com.rey.common.CommonUtils;
import com.rey.model.Ability;
import com.rey.model.Hero;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

public class HeroController {
	private Vertx vertx;
	private Logger logger = LoggerFactory.getLogger(HeroController.class);

	public HeroController(Vertx myVertx) {
		this.vertx = myVertx;
	}

	public void getHeros(RoutingContext context) {
		vertx.eventBus().request(CommonConstants.HERO_GET, "", msg -> {
			if (msg.succeeded()) {

				Gson gson = new Gson();
				List<Hero> heros = gson.fromJson(msg.result().body().toString().toLowerCase(),
						CommonUtils.heroListTypeToken.getType());
				context.response().putHeader(CommonConstants.CONTENT_TYPE, CommonConstants.CONTENT_TYPE_JSON)
						.end(Json.encodePrettily(heros));

			} else {
				logger.error("error with get heros from db");
				logger.error(msg.cause());
				context.response().putHeader(CommonConstants.CONTENT_TYPE, CommonConstants.CONTENT_TYPE_JSON)
						.setStatusCode(503).end(Json.encode("cannot get heros now"));
			}
		});
	}

	public void getHerosById(RoutingContext context) {
		String idStr = context.request().getParam("hero_id");
		int id = 0;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			String info = CommonConstants.BAD_REQUEST + ", hero_id is " + idStr;
			context.response().putHeader(CommonConstants.CONTENT_TYPE, CommonConstants.CONTENT_TYPE_JSON)
					.setStatusCode(400).end(Json.encode(info));
			return;
		}

		JsonObject params = new JsonObject();
		params.put("id", id);
		vertx.eventBus().request(CommonConstants.HERO_GET_BY_ID, Json.encode(params), msg -> {
			if (msg.succeeded()) {
				if (CommonConstants.NULL_OBJ.equals(msg.result().body())) {
					String info = CommonConstants.RESOUCE_NOT_FOUND + ", hero_id is " + idStr;
					context.response().putHeader(CommonConstants.CONTENT_TYPE, CommonConstants.CONTENT_TYPE_JSON)
							.setStatusCode(404).end(Json.encode(info));
				} else {
					Gson gson = new Gson();
					Hero hero = gson.fromJson(msg.result().body().toString().toLowerCase(), Hero.class);
					context.response().putHeader(CommonConstants.CONTENT_TYPE, CommonConstants.CONTENT_TYPE_JSON)
							.end(Json.encodePrettily(hero));
				}
			} else {
				logger.error("error with get heros from db");
				logger.error(msg.cause());
				context.response().putHeader(CommonConstants.CONTENT_TYPE, CommonConstants.CONTENT_TYPE_JSON)
						.setStatusCode(503).end(Json.encode("cannot get heros now"));
			}
		});
	}

	public void getHeroAbilities(RoutingContext context) {
		String idStr = context.request().getParam("hero_id");
		int id = 0;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			String info = CommonConstants.BAD_REQUEST + ", hero_id is " + idStr;
			context.response().putHeader(CommonConstants.CONTENT_TYPE, CommonConstants.CONTENT_TYPE_JSON)
					.setStatusCode(400).end(info);
			return;
		}

		JsonObject params = new JsonObject();
		params.put("id", id);

		vertx.eventBus().request(CommonConstants.ABILITY_GET_BY_HEROID, Json.encode(params), msg -> {
			if (msg.succeeded()) {
				if (CommonConstants.NULL_OBJ.equals(msg.result().body())) {
					String info = CommonConstants.RESOUCE_NOT_FOUND + ", hero_id is " + idStr;
					context.response().putHeader(CommonConstants.CONTENT_TYPE, CommonConstants.CONTENT_TYPE_JSON)
							.setStatusCode(404).end(Json.encode(info));
				} else {
					Gson gson = new Gson();
					List<Ability> abilities = gson.fromJson(msg.result().body().toString().toLowerCase(),
							CommonUtils.abilityListTypeToken.getType());
					context.response().putHeader(CommonConstants.CONTENT_TYPE, CommonConstants.CONTENT_TYPE_JSON).end(Json.encodePrettily(abilities));
				}
			} else {
				logger.error("error with get heros ability from db");
				logger.error(msg.cause());
				context.response().putHeader(CommonConstants.CONTENT_TYPE, CommonConstants.CONTENT_TYPE_JSON)
						.setStatusCode(503).end(Json.encode("cannot get abilites now"));
			}
		});
	}
}
