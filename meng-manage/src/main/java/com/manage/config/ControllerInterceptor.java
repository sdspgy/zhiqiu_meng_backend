package com.manage.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhiqiu
 * @since 2019-11-26
 */
//@Aspect
//@Component
public class ControllerInterceptor {

	@Pointcut("execution(public * com.manage.*..*Controller.*(..))")
	public void point() {

	}

	@Around("point()")
	public Object aroundByRbac(ProceedingJoinPoint invocation) throws Throwable {
		Map<String, Object> result = new HashMap<>();
		try {
			Object data = invocation.proceed();
			result.put("success", true);
			result.put("message", "success");
			result.put("code", 200);
			result.put("timestamp", System.currentTimeMillis());
			result.put("result", data);
			return result;
		} catch (RuntimeException e) {
			result.put("success", e.getCause());
			result.put("message", e.getMessage());
			result.put("code", e.getCause());
			result.put("timestamp", System.currentTimeMillis());
			return result;
		} catch (Exception e) {
			String msg = e.getMessage();
			result.put("success", false);
			result.put("message", msg == null ? "系统异常" : msg);
			result.put("code", 500);
			result.put("timestamp", System.currentTimeMillis());
			return result;
		} finally {
		}
	}
}
