package com.rey.dao;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import com.rey.common.CommonConstants;
import com.rey.verticle.WebVerticle;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;

public class CommonDB {
	private Logger logger = LoggerFactory.getLogger(CommonDB.class);
	private JDBCClient client;
	private Vertx vertx;

	public CommonDB(Vertx myVertx, JDBCClient myClient) {
		this.vertx = myVertx;
		this.client = myClient;
		registerConsumers();
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

	public static void main(String[] args) throws Exception {
		String sql = (new CommonDB(null, null)).getSQLFromResource("schema_creation.sql");
		System.out.println(sql);
	}
}
