package com.rey.common;

public class CommonConstants {
	public final static String USER_AGENT_STRING = "User-Agent";
	public static final String CONTENT_TYPE = "content-type";
	public static final String CONTENT_TYPE_JSON = "application/json";
	
	
	// event bus address
	public final static String INIT_DB="db.init";
	public final static String HERO_INSERT="hero.insert";
	public final static String HERO_GET="hero.get";
	public final static String HERO_GET_BY_ID="hero.get.byid";

	public final static String ABILITY_INSERT="ability.insert";
	public final static String ABILITY_GET="ability.get";
	public final static String ABILITY_GET_BY_HEROID="ability.get.byheroid";
	public final static String ABILITY_GET_BY_ID="ability.get.byid";
	// msg
	public final static String OK_MSG="ok";
	public final static String NULL_OBJ="null";
	
	public final static String RESOUCE_NOT_FOUND="required resource is not found";
	public final static String BAD_REQUEST="bad request, please check your query";
}
