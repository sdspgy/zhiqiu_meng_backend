package com.core.common.base;

import com.core.entity.sys.SysUser;
import org.apache.shiro.SecurityUtils;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
public abstract class AbstractController {

	protected SysUser getUser() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}

	protected Integer getUserId() {
		return getUser().getUserId();
	}

	protected String getUserName() {
		return getUser().getUsername();
	}

}