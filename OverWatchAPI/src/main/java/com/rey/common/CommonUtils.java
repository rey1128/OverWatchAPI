package com.rey.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.reflect.TypeToken;
import com.rey.model.Ability;
import com.rey.model.Hero;


public class CommonUtils {
	public static TypeToken<ArrayList<Hero>> heroListTypeToken = new TypeToken<ArrayList<Hero>>() {
		private static final long serialVersionUID = -988983875251362696L;
	};
	public static TypeToken<ArrayList<Ability>> abilityListTypeToken = new TypeToken<ArrayList<Ability>>() {
		private static final long serialVersionUID = -4513457445785645574L;
	};
	public static TypeToken<HashMap<String, Object>> hashMapStringObjectTypeToken = new TypeToken<HashMap<String, Object>>() {
		private static final long serialVersionUID = -2985374605659932196L;
	};
	
}
