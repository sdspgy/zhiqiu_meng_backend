package com.auth.service.impl;

import com.auth.service.ShiroService;
import com.auth.service.SysUserTokenService;
import com.core.common.constant.RedisKeyConstants;
import com.core.common.constant.SysConstants;
import com.core.entity.sys.SysMenu;
import com.core.entity.sys.SysUser;
import com.core.entity.sys.SysUserToken;
import com.core.mapper.sys.SysMenuMapper;
import com.core.mapper.sys.SysUserMapper;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhiqiu
 * @since 2019-11-25
 */
@Service
public class ShiroServiceImpl implements ShiroService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Autowired
	private SysUserTokenService sysUserTokenService;

	/**
	 * 获取用户的所有权限
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Set<String> getUserPermissions(Integer userId) {
		List<String> permsList;

		//系统管理员，拥有最高权限
		if (SysConstants.SUPER_ADMIN.equals(userId)) {
			List<SysMenu> menuList = sysMenuMapper.selectList(null);
			permsList = new ArrayList<>(menuList.size());
			menuList.forEach(menu -> permsList.add(menu.getPerms()));
		} else {
			permsList = sysUserMapper.queryAllPerms(userId);
		}
		Set<String> permsSet = Sets.newHashSet();
		if (!permsList.isEmpty()) {
			permsSet = permsList.stream()
							// 过滤空置的字符串
							.filter(perms -> !StringUtils.isEmpty(perms))
							// 把小的list合并成大的list
							.flatMap(perms -> Arrays.stream(perms.split(",")))
							// 转换成set集合
							.collect(Collectors.toSet());
		}
		//返回用户权限列表
		return permsSet;
	}

	/**
	 * 查询token
	 *
	 * @param token
	 * @return
	 */
	@Override
	public SysUserToken queryByToken(String token) {
		return sysUserTokenService.queryByToken(RedisKeyConstants.MANAGE_SYS_USER_TOKEN + token);
	}

	/**
	 * 查询用户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public SysUser queryUser(Integer userId) {
		return sysUserMapper.selectById(userId);
	}

	/**
	 * 续期
	 *
	 * @param userId
	 * @param accessToken
	 */
	@Override
	public void refreshToken(Integer userId, String accessToken) {
		sysUserTokenService.refreshToken(userId, accessToken);
	}
}
