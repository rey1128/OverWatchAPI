package com.rey.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.rey.common.CommonConstants;
import com.rey.common.CommonUtils;
import com.rey.model.Ability;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;

public class AbilityDao {
	private JDBCClient client;
	private Vertx vertx;
	private Logger logger = LoggerFactory.getLogger(AbilityDao.class);

	public AbilityDao(Vertx myVertx, JDBCClient myClient) {
		this.vertx = myVertx;
		this.client = myClient;
		registerConsumer();
	}

	private void registerConsumer() {
		insertAbility();
		getAbility();
		getAbilityById();
		getAbilityByHeroId();
	}

	private void getAbilityByHeroId() {
		
		vertx.eventBus().consumer(CommonConstants.ABILITY_GET_BY_HEROID).handler(msg -> {
			JsonObject msgJson = (JsonObject) Json.decodeValue(msg.body().toString());
			int id = msgJson.getInteger("id");
			JsonArray params = new JsonArray();
			params.add(id);

			client.getConnection(conn -> {
				if (conn.succeeded()) {
					conn.result().queryWithParams("select * from ability where hero_id=?", params, rs -> {
						if (rs.succeeded()) {
							List<JsonObject> heros=rs.result().getRows();
							if(heros.isEmpty()) {
								msg.reply(CommonConstants.NULL_OBJ);
							}else {
								msg.reply(Json.encode(rs.result().getRows()));	
							}
							
						} else {
							logger.error("error with query ability by hero_id from db, hero_id is " + id);
							logger.error(rs.cause());
						}
						conn.result().close();
					});
				}
			});
		});
	}

	private void getAbilityById() {

		vertx.eventBus().consumer(CommonConstants.ABILITY_GET_BY_ID).handler(msg -> {
			JsonObject msgJson = (JsonObject) Json.decodeValue(msg.body().toString());
			int id = msgJson.getInteger("id");
			JsonArray params = new JsonArray();
			params.add(id);

			client.getConnection(conn -> {
				if (conn.succeeded()) {
					conn.result().queryWithParams("select * from ability where id=?", params, rs -> {
						if (rs.succeeded()) {
							List<JsonObject> heros=rs.result().getRows();
							if(heros.isEmpty()) {
								msg.reply(CommonConstants.NULL_OBJ);
							}else {
								msg.reply(Json.encode(rs.result().getRows().get(0)));	
							}
							
						} else {
							logger.error("error with query ability from db, ability_id is " + id);
							logger.error(rs.cause());
						}
						conn.result().close();
					});
				}
			});
		});

	}

	private void getAbility() {
	
		vertx.eventBus().consumer(CommonConstants.ABILITY_GET).handler(msg -> {

			client.getConnection(conn -> {
				if (conn.succeeded()) {
					conn.result().query("select * from ability", rs -> {
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

	private void insertAbility() {
		vertx.eventBus().consumer(CommonConstants.ABILITY_INSERT).handler(msg -> {
			List<Ability> abilities = convertJsonToAbility(msg.body().toString());
			client.getConnection(conn -> {
				if (conn.succeeded()) {
					conn.result().batchWithParams(
							"insert into ability (id, name, description, is_ultimate, hero_id) values(?,?,?,?,?)",
							buildBatchParams(abilities), insert -> {
								if (insert.succeeded()) {
									msg.reply(CommonConstants.OK_MSG);
								} else {
									logger.error("error with insert abilies into db");
									logger.error(insert.cause());
								}
								conn.result().close();
							});
				}
			});

		});
	}

	private List<Ability> convertJsonToAbility(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, CommonUtils.abilityListTypeToken.getType());
	}

	private List<JsonArray> buildBatchParams(List<Ability> abilities) {

		// insert into ability (id, name, description, is_ultimate, hero_id)
		// values(?,?,?,?,?)
		List<JsonArray> abilitysParams = new ArrayList<>();
		abilities.forEach(a -> {
			JsonArray params = new JsonArray();
			params.add(a.getId());
			params.add(a.getName());
			params.add(a.getDescription());
			params.add(a.isIs_ultimate());
			params.add(a.getHero().getId());

			abilitysParams.add(params);
		});
		return abilitysParams;
	}
}
