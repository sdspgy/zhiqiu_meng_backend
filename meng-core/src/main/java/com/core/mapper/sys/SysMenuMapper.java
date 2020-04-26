package com.core.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.core.entity.sys.SysMenu;

import java.util.List;

/**
 * Author:   taoyuzhu(taoyuzhu@hulai.com)
 * Date:     2019-07-10 10:42
 * Description:
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	List<SysMenu> queryMenuRouterByUserId(Integer parentId);

	/*查询完整权限树*/
	List<SysMenu> querySysMenuTree(Integer parentId);

}
