package com.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
public class OAuth2Token implements AuthenticationToken {

	private String token;

	public OAuth2Token(String token) {
		this.token = token;
	}

	@Override
	public String getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}
}
