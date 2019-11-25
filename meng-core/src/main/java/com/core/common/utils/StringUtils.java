package com.core.common.utils;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
public class StringUtils extends org.springframework.util.StringUtils {

	public static boolean isBlank(final CharSequence cs) {
		return !StringUtils.isNotBlank(cs);
	}

	public static boolean isNotBlank(final CharSequence cs) {
		return StringUtils.hasText(cs);
	}

}
