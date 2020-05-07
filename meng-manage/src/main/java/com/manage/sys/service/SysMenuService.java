package com.manage.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.entity.sys.SysMenu;

import java.util.List;

/**
 * Author:   yuzhu·tao
 * Date:     2019/7/24 23:19
 * Description:
 */
public interface SysMenuService extends IService<SysMenu> {

	List<SysMenu> queryMenuRouterByUserId(Integer userId);

	/*查询完整权限树*/
	List<SysMenu> querySysMenuTree(Integer parentId);

	void insertMenu(SysMenu sysMenu);

	void deletetMenuByMenuId(Integer menuId);
}
