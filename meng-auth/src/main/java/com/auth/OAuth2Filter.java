package com.auth;

import com.core.common.exception.ErrorEnum;
import com.core.common.utils.HttpContextUtils;
import com.core.common.utils.JsonUtils;
import com.core.entity.sys.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
public class OAuth2Filter extends AuthenticatingFilter {

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		/*获取请求token*/
		String token = getRequestToken((HttpServletRequest) request);
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		return new OAuth2Token(token);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		/*获取请求token，如果token不存在，直接返回401*/
		String token = getRequestToken((HttpServletRequest) request);
		if (StringUtils.isEmpty(token)) {
			HttpServletResponse httpResponse = getHttpServletResponse((HttpServletResponse) response);
			String json = JsonUtils.toJson(Result.error(ErrorEnum.INVALID_TOKEN));
			httpResponse.getWriter().print(json);
			return false;
		}
		return executeLogin(request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		HttpServletResponse httpResponse = getHttpServletResponse((HttpServletResponse) response);
		try {
			/*处理登录失败的异常*/
			Throwable throwable = e.getCause() == null ? e : e.getCause();
			Result r = Result.error(ErrorEnum.TOKEN_INVALID.getCode(), throwable.getMessage());
			String json = JsonUtils.toJson(r);
			httpResponse.getWriter().print(json);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}

	private HttpServletResponse getHttpServletResponse(HttpServletResponse response) {
		HttpServletResponse httpResponse = response;
		httpResponse.setContentType("application/json;charset=utf-8");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
		return httpResponse;
	}

	/*获取请求的token*/
	private String getRequestToken(HttpServletRequest httpRequest) {
		/*从header中获取token*/
		String token = httpRequest.getHeader("token");
		/*如果header中不存在token，则从参数中获取token*/
		if (StringUtils.isEmpty(token)) {
			token = httpRequest.getParameter("token");
		}
		return token;
	}

}