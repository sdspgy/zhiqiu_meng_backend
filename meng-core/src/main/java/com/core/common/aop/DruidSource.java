package com.core.common.aop;

import java.lang.annotation.*;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DruidSource {

	String druidSource();
}
