package com.rey.overwatch.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class HTTPClientService {
	
	public String get(String absUrl) {
		OkHttpClient client=new OkHttpClient();
		Request getRequest = new Request.Builder().url(absUrl).get().build();
		try (Response response = client.newCall(getRequest).execute()) {
			return response.body().string();
		} catch (IOException e) {
			throw new ResourceAccessException("cannot access resource with url: " + absUrl, e);
		}
	}

}
