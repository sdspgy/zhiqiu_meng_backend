package com.core.common.utils;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
public class HttpClientUtil {

	public static String client(String url, HttpMethod method, MultiValueMap<String, String> params) {
		RestTemplate template = new RestTemplate();
		ResponseEntity<String> response = template.getForEntity(url, String.class);
		return response.getBody();
	}
}
