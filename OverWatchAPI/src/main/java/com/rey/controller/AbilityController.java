package com.rey.controller;

import java.util.List;

import com.google.gson.Gson;
import com.rey.common.CommonConstants;
import com.rey.common.CommonUtils;
import com.rey.model.Ability;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
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
		vertx.eventBus().request(CommonConstants.ABILITY_GET, "", msg -> {
			if (msg.succeeded()) {

				Gson gson = new Gson();
				List<Ability> abilities = gson.fromJson(msg.result().body().toString().toLowerCase(),
						CommonUtils.abilityListTypeToken.getType());
				context.response().end(Json.encodePrettily(abilities));

			} else {
				logger.error("error with get heros from db");
				logger.error(msg.cause());
				context.response().setStatusCode(503).end(Json.encode("cannot get heros now"));
			}
		});
	}

	public void getAbilityById(RoutingContext context) {
		String idStr = context.request().getParam("ability_id");
		int id = 0;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			String info = CommonConstants.BAD_REQUEST + ", ability_id is " + idStr;
			context.response().setStatusCode(400).end(info);
			return;
		}

		JsonObject params = new JsonObject();
		params.put("id", id);
		vertx.eventBus().request(CommonConstants.ABILITY_GET_BY_ID, Json.encode(params), msg -> {
			if (msg.succeeded()) {
				if (CommonConstants.NULL_OBJ.equals(msg.result().body())) {
					String info = CommonConstants.RESOUCE_NOT_FOUND + ", ability_id is " + idStr;
					context.response().setStatusCode(404).end(Json.encode(info));
				} else {
					Gson gson = new Gson();
					Ability ability = gson.fromJson(msg.result().body().toString().toLowerCase(), Ability.class);
					context.response().end(Json.encodePrettily(ability));
				}
			} else {
				logger.error("error with get heros from db");
				logger.error(msg.cause());
				context.response().setStatusCode(503).end(Json.encode("cannot get heros now"));
			}
		});
	}

}
