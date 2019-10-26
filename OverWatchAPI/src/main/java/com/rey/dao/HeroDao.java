package com.rey.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.rey.common.CommonConstants;
import com.rey.common.CommonUtils;
import com.rey.model.Hero;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;

public class HeroDao {
	private Logger logger = LoggerFactory.getLogger(HeroDao.class);
	private JDBCClient client;
	private Vertx vertx;

	public HeroDao(Vertx myVertx, JDBCClient myClient) {
		this.vertx = myVertx;
		this.client = myClient;
		registerConsumer();
	}

	private void registerConsumer() {
		insertHeros();
		getHeros();
		getHeroById();
	}

	private void getHeroById() {

		vertx.eventBus().consumer(CommonConstants.HERO_GET_BY_ID).handler(msg -> {
			JsonObject msgJson = (JsonObject) Json.decodeValue(msg.body().toString());
			int id = msgJson.getInteger("id");
			JsonArray params = new JsonArray();
			params.add(id);

			client.getConnection(conn -> {
				if (conn.succeeded()) {
					conn.result().queryWithParams("select * from hero where id=?", params, rs -> {
						if (rs.succeeded()) {
							msg.reply(Json.encode(rs.result().getRows().get(0)));
						} else {
							logger.error("error with query hero from db, hero_id is " + id);
							logger.error(rs.cause());
						}
						conn.result().close();
					});
				}
			});
		});
	}

	private void getHeros() {
		vertx.eventBus().consumer(CommonConstants.HERO_GET_BY_ID).handler(msg -> {

			client.getConnection(conn -> {
				if (conn.succeeded()) {
					conn.result().query("select * from hero", rs -> {
						if (rs.succeeded()) {
							msg.reply(Json.encode(rs.result().getRows()));
						} else {
							logger.error("error with query hero from db");
							logger.error(rs.cause());
						}
						conn.result().close();
					});
				}
			});
		});

	}

	private void insertHeros() {
		vertx.eventBus().consumer(CommonConstants.HERO_INSERT).handler(msg -> {
			List<Hero> heros = convertJsonToHero(msg.body().toString());
			client.getConnection(conn -> {
				if (conn.succeeded()) {
					conn.result().batchWithParams(
							"insert into hero (id, name, real_name, health, armour, shield) values(?,?,?,?,?,?)",
							buildBatchParams(heros), insert -> {
								if (insert.succeeded()) {
									msg.reply(CommonConstants.OK_MSG);
								} else {
									logger.error("error with insert heros into db");
									logger.error(insert.cause());
								}
								conn.result().close();
							});
				}
			});

		});
	}

	private List<Hero> convertJsonToHero(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, CommonUtils.heroListTypeToken.getType());
	}

	private List<JsonArray> buildBatchParams(List<Hero> heros) {

		// insert into hero (id, name, real_name, health, armour, shield)
		// values(?,?,?,?,?,?)
		List<JsonArray> herosParams = new ArrayList<>();
		heros.forEach(h -> {
			JsonArray params = new JsonArray();
			params.add(h.getId());
			params.add(h.getName());
			params.add(h.getReal_name());
			params.add(h.getHealth());
			params.add(h.getArmour());
			params.add(h.getShield());
			herosParams.add(params);
		});
		return herosParams;
	}
}
