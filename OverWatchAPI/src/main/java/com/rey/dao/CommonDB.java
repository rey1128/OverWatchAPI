package com.rey.dao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.rey.common.CommonConstants;
import com.rey.common.CommonUtils;
import com.rey.model.Ability;
import com.rey.model.Hero;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.client.WebClient;

public class CommonDB {
	private Logger logger = LoggerFactory.getLogger(CommonDB.class);
	private JDBCClient client;
	private Vertx vertx;

	public CommonDB(Vertx myVertx, JDBCClient myClient) {
		this.vertx = myVertx;
		this.client = myClient;
		registerConsumers();
		fetchData();
	}

	private void registerConsumers() {
		initDBSchema();
	}

	private void initDBSchema() {
		vertx.eventBus().consumer(CommonConstants.INIT_DB).handler(msg -> {

			String sql = getSQLFromResource("schema_creation.sql");
			client.getConnection(conn -> {
				if (conn.succeeded()) {
					conn.result().execute(sql, created -> {
						if (created.succeeded()) {
							msg.reply(CommonConstants.OK_MSG);
						} else {
							logger.error("error with init db schema");
						}
					});
				}
			});

		});
	}

	private void fetchData() {
		WebClient client = WebClient.create(vertx);
		client.getAbs("http://overwatch-api.net/api/v1/hero/").send(hr -> {
			if (hr.succeeded()) {
				Gson gson = new Gson();
				Map<String, Object> map = gson.fromJson(hr.result().bodyAsString(),
						CommonUtils.hashMapStringObjectTypeToken.getType());
				// insert into db
				vertx.eventBus().request(CommonConstants.HERO_INSERT, Json.encode(map.get("data")), insert -> {
					if (insert.succeeded() && CommonConstants.OK_MSG.equals(insert.result().body())) {
						logger.info("heros are saved successfully");
					} else {
						logger.error("error with init inserting heros");
						logger.error(insert.cause());
					}

				});

			} else {
				logger.error("error with touching overwatch-api");
				logger.error(hr.cause());
			}

		});

		client.getAbs("http://overwatch-api.net/api/v1/ability/").send(hr -> {
			if (hr.succeeded()) {
				Gson gson = new Gson();
				Map<String, Object> map = gson.fromJson(hr.result().bodyAsString(),
						CommonUtils.hashMapStringObjectTypeToken.getType());

				// insert into db
				vertx.eventBus().request(CommonConstants.ABILITY_INSERT, Json.encode(map.get("data")), insert -> {
					if (insert.succeeded() && CommonConstants.OK_MSG.equals(insert.result().body())) {
						logger.info("abilies are saved successfully");
					} else {
						logger.error("error with init inserting abilities");
						logger.error(insert.cause());
					}
				});

			} else {
				logger.error("error with touching overwatch-api");
				logger.error(hr.cause());
			}

		});

	}

	private String getSQLFromResource(String filePath) {
		String content = null;
		try {
			content = IOUtils.toString(CommonDB.class.getResourceAsStream(filePath), Charset.defaultCharset());
		} catch (IOException e) {
			logger.error("error with reading resource: " + filePath);
			e.printStackTrace();
		}

		return content;

	}

}
