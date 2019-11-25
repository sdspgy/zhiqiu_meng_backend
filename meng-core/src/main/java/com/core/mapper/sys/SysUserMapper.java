package com.core.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.core.entity.sys.SysUser;

import java.util.List;

/**
 * Author:   taoyuzhu(taoyuzhu@hulai.com)
 * Date:     2019-07-10 09:40
 * Description:
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

	/**
	 * 查询用户所有权限
	 *
	 * @param userId
	 * @return
	 */
	List<String> queryAllPerms(Integer userId);

	/**
	 * 查询所以用户信息（角色）
	 *
	 * @return
	 */
	List<SysUser> queryAllUser();

	/**
	 * 批量重置密码
	 *
	 * @param userIdList
	 * @param toHex
	 * @param salt
	 */
	void resetPassword(List<Integer> userIdList, String toHex, String salt);
}
