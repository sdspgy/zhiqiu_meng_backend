package com.core.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
public class WxUtils {

	public static final String SECRET = "423b9d850ce249f72e62962c22d68f19";
	public static final String APPID = "wx1be02a27b7657448";
	public static final String APPURL = "https://api.weixin.qq.com/sns/jscode2session";

	public WxUtils() {
	}

	public static JSONObject produceWxInfo(String code) {
		String url = APPURL + "?appid=" + APPID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";
		String data = HttpClientUtil.client(url, HttpMethod.GET, new LinkedMultiValueMap<>());
		return JSON.parseObject(data);
	}
}
