package com.core.common.aop;


import com.core.common.base.AbstractController;
import com.core.common.utils.StringUtils;
import com.core.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Aspect
@Component
@Slf4j
public class LogAspect extends AbstractController {

	//	@Pointcut("within(@org.springframework.web.bind.annotation.RequestMapping *)")
	@Pointcut("@annotation(com.core.common.aop.Log)")
	public void logPointCut() {

	}

	@AfterReturning(pointcut = "logPointCut()")
	public void doBefore(JoinPoint joinPoint) {
		handleLog(joinPoint, null);
	}

	@AfterThrowing(value = "logPointCut()", throwing = "e")
	public void doAfter(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e);
	}

	protected void handleLog(final JoinPoint joinPoint, final Exception e) {
		try {
			//获得注解
			Log controllerLog = getAnnotationLog(joinPoint);
			if (controllerLog == null) {
				return;
			}
			String name = controllerLog.value();
			String LoginName = getUserName();
			String ip = SecurityUtils.getSubject().getSession().getHost();
			String nowTime = ToolUtils.simpleDateFormat(System.currentTimeMillis());
			//设置类名-方法名
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			//请求参数
			Object[] paeams = joinPoint.getArgs();
			Enumeration<String> paraNames = null;
			HttpServletRequest request = null;
			String url = null;
			StringBuffer bfParams = new StringBuffer();
			if (paeams != null && paeams.length > 0) {
				request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				paraNames = request.getParameterNames();
				url = request.getRequestURL().toString();
				String key;
				String value;
				while (paraNames.hasMoreElements()) {
					key = paraNames.nextElement();
					value = request.getParameter(key);
					bfParams.append(key)
									.append("=")
									.append(value)
									.append("&");
				}
				if (StringUtils.isBlank(bfParams)) {
					bfParams.append(request.getQueryString());
				}
			}
			String message = String.format("[url]：%s,[类名]：%s,[方法]：%s,[参数]：%s", url, className, methodName, bfParams.toString());
			log.info(message);
			//写入数据库

		} catch (Exception exp) {
			log.error("AopLog异常信息" + exp.getMessage());
			exp.printStackTrace();
		}
	}

	private Log getAnnotationLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		if (method != null) {
			return method.getAnnotation(Log.class);
		}
		return null;
	}
}