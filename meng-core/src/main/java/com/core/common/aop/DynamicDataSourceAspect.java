package com.core.common.aop;

import com.core.common.druid.DynamicDataSourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

	@Pointcut("@annotation(com.core.common.aop.DruidSource)")
	public void point() {
	}

	@Around(value = "point()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method targetMethod = methodSignature.getMethod();
		if (targetMethod.isAnnotationPresent(DruidSource.class)) {
			String targetDataSource = targetMethod.getAnnotation(DruidSource.class).druidSource();
			DynamicDataSourceHolder.setDataSource(targetDataSource);
		}
		/*执行方法*/
		Object result = pjp.proceed();
		DynamicDataSourceHolder.clearDataSource();
		return result;
	}
}
