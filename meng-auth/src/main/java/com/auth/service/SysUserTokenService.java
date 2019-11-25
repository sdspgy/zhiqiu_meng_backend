package com.auth.service;

import com.core.entity.sys.Result;
import com.core.entity.sys.SysUserToken;

/**
 * @author zhiqiu
 * @since 2019-11-25
 */
public interface SysUserTokenService {

	/**
	 * 生成Token
	 *
	 * @param userId
	 * @return
	 */
	Result createToken(Integer userId);

	/**
	 * 查询token
	 *
	 * @param token
	 * @return
	 */
	SysUserToken queryByToken(String token);

	/**
	 * 退出登录
	 *
	 * @param userId
	 */
	void logout(Integer userId);

	/**
	 * 续期
	 *
	 * @param userId
	 * @param token
	 */
	void refreshToken(Integer userId, String token);
}
