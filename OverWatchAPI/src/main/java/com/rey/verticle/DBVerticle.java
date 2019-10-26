package com.rey.verticle;

import com.rey.common.CommonConstants;
import com.rey.dao.AbilityDao;
import com.rey.dao.CommonDB;
import com.rey.dao.HeroDao;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;

public class DBVerticle extends AbstractVerticle {
	private Logger logger = LoggerFactory.getLogger(DBVerticle.class.getName());

	@Override
	public void start() throws Exception {
		super.start();
		JsonObject config = new JsonObject();
		config.put("url", "jdbc:hsqldb:mem:test?shutdown=true");
		config.put("driver_class", "org.hsqldb.jdbcDriver");
		config.put("max_pool_size", 10);

		JDBCClient client = JDBCClient.createShared(vertx, config);

		initDaos(client);
		vertx.eventBus().request(CommonConstants.INIT_DB, "", init -> {

			if (init.succeeded() && CommonConstants.OK_MSG.equals(init.result().body())) {
				logger.info("schema init successfully");
			}
		});

	}

	private void initDaos(JDBCClient client) {
		new AbilityDao(vertx, client);
		new HeroDao(vertx, client);
		new CommonDB(vertx, client);
	}
}
