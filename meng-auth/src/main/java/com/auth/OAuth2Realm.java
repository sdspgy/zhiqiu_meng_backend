package com.auth;

import com.auth.service.ShiroService;
import com.core.common.constant.SysConstants;
import com.core.common.exception.ErrorEnum;
import com.core.entity.sys.SysUser;
import com.core.entity.sys.SysUserToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

	@Autowired
	private ShiroService shiroService;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof OAuth2Token;
	}

	/*授权(验证权限时调用)*/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUser user = (SysUser) principals.getPrimaryPrincipal();
		Integer userId = user.getUserId();
		//用户权限列表
		Set<String> permsSet = shiroService.getUserPermissions(userId);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (!permsSet.isEmpty()) {
			info.setStringPermissions(permsSet);
		}
		return info;
	}

	/*认证(登录时调用)*/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
			String accessToken = (String) token.getPrincipal();
		//根据accessToken，查询用户信息
		SysUserToken tokenEntity = shiroService.queryByToken(accessToken);
		//token失效
		if (tokenEntity == null) {
			throw new IncorrectCredentialsException(ErrorEnum.TOKEN_INVALID.getMsg());
		}
		//查询用户信息
		SysUser user = shiroService.queryUser(tokenEntity.getUserId());
		//账号锁定
		if (user.getStatus().equals(SysConstants.ACCOUNT_LOCKING)) {
			throw new LockedAccountException(ErrorEnum.ACCOUNT_LOCKING.getMsg());
		}
		//续期
		shiroService.refreshToken(tokenEntity.getUserId(), accessToken);
		return new SimpleAuthenticationInfo(user, accessToken, getName());
	}

}
