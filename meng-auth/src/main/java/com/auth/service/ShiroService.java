package com.auth.service;

import com.core.entity.sys.SysUser;
import com.core.entity.sys.SysUserToken;

import java.util.Set;

/**
 * @author zhiqiu
 * @since 2019-11-25
 */
public interface ShiroService {

	/**
	 * 获取用户的所有权限
	 *
	 * @param userId
	 * @return
	 */
	Set<String> getUserPermissions(Integer userId);

	/**
	 * 查询token
	 *
	 * @param token
	 * @return
	 */
	SysUserToken queryByToken(String token);

	/**
	 * 查询用户信息
	 *
	 * @param userId
	 * @return
	 */
	SysUser queryUser(Integer userId);

	/**
	 * 续期
	 *
	 * @param userId
	 * @param accessToken
	 */
	void refreshToken(Integer userId, String accessToken);
}
