package com.rey.overwatch.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rey.overwatch.dao.Ability;
import com.rey.overwatch.dao.Hero;

@Service
public class DataLoaderService {
	@Autowired
	private HeroService heroSrv;
	@Autowired
	private AbilityService abilitySrv;
	@Autowired
	private HTTPClientService httpClientSrv;

	final String HERO_ABS_URL = "http://overwatch-api.net/api/v1/hero";
	final String ABILITY_ABS_URL = "http://overwatch-api.net/api/v1/ability";
	final TypeToken<ArrayList<Hero>> heroListTypeToken = new TypeToken<ArrayList<Hero>>() {
	};
	final TypeToken<ArrayList<Ability>> abilityListTypeToken = new TypeToken<ArrayList<Ability>>() {
	};

	public void initHeros() {
		initHeroFromOverWatch(HERO_ABS_URL);
	}
	private void initHeroFromOverWatch(String url) {
		String herosJsonStr = httpClientSrv.get(url);
		JSONObject jo = new JSONObject(herosJsonStr);
		String data = JSONObject.valueToString(jo.get("data"));

		Gson gson = new Gson();
		List<Hero> heros = gson.fromJson(data, heroListTypeToken.getType());
		heroSrv.createHeros(heros);
		if (jo.get("next") != JSONObject.NULL)
			initHeroFromOverWatch(jo.getString("next"));
	}

	public void initAbilities() {
		initAbilityFromOverWatch(ABILITY_ABS_URL);
	}
	public void initAbilityFromOverWatch(String url) {
		String herosJsonStr = httpClientSrv.get(url);
		JSONObject jo = new JSONObject(herosJsonStr);
		String data = JSONObject.valueToString(jo.get("data"));

		Gson gson = new Gson();
		List<Ability> abilities = gson.fromJson(data, abilityListTypeToken.getType());
		abilitySrv.createAbilities(abilities);
		if (jo.get("next") != JSONObject.NULL)
			initAbilityFromOverWatch(jo.getString("next"));
	}
}
