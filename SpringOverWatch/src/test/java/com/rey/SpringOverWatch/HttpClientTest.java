package com.rey.SpringOverWatch;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.query.Jpa21Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rey.overwatch.dao.Hero;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClientTest {
	private final String BASE_HOST = "http://overwatch-api.net";

	@Test
	public void testHttpClient() throws Exception {
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder().url(BASE_HOST + "/api/v1/hero/").build();
		try (Response response = client.newCall(request).execute()) {
			String rt = response.body().string();
			
			JSONObject jo = new JSONObject(rt);
			
			Object next=jo.get("next");
			System.out.println(next);
//			String data = JSONObject.valueToString(jo.get("data"));
//			Gson gson = new Gson();
//			TypeToken<ArrayList<Hero>> heroListToken=new TypeToken<ArrayList<Hero>>() {};
//			List<Hero> heros=gson.fromJson(data, heroListToken.getType());
//			System.out.println(heros);
		}
		
	}

}
