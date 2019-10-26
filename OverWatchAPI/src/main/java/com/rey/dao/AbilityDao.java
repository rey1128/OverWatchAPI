package com.rey.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.rey.common.CommonConstants;
import com.rey.common.CommonUtils;
import com.rey.model.Ability;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
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
	}

	private void getAbilityById() {
		// TODO Auto-generated method stub

	}

	private void getAbility() {
		// TODO Auto-generated method stub

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
