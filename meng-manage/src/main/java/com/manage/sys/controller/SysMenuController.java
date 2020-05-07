package com.manage.sys.controller;

import com.core.common.aop.Log;
import com.core.common.base.AbstractController;
import com.core.entity.sys.Result;
import com.core.entity.sys.SysMenu;
import com.manage.sys.service.impl.SysMenuServiceImpl;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:         知秋
 * CreateDate:     2019-08-30 20:25
 */
@RestController
@Api(tags = "menu", value = "menu")
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

	@Autowired
	private SysMenuServiceImpl sysMenuService;

	@GetMapping("/menuRouter")
	public Result queryMenuRouter() {
		List<SysMenu> sysMenus = sysMenuService.queryMenuRouterByUserId(0);
		return Result.ok().put("sysMenus", sysMenus);
	}

	@GetMapping("/menuTree")
	@RequiresPermissions(value = { "sys:menu:list", "sys:menu:info" }, logical = Logical.OR)
	@Log(value = "权限树")
	public Result queryMenuTree() {
		List<SysMenu> sysMenus = sysMenuService.querySysMenuTree(0);
		return Result.ok().put("sysMenus", sysMenus);
	}

	@PostMapping("/insertMenu")
	@RequiresPermissions("sys:menu:info")
	@Log(value = "资源添加")
	public Result insertMenu(@RequestBody SysMenu sysMenu) {
		sysMenuService.insertMenu(sysMenu);
		return Result.ok();
	}

	@PostMapping("/updateMenu")
	@RequiresPermissions("sys:menu:info")
	@Log(value = "资源修改")
	public Result updateMenu(@RequestBody SysMenu sysMenu) {
		sysMenuService.updateById(sysMenu);
		return Result.ok();
	}

	@PostMapping("/deleteMenu")
	@RequiresPermissions("sys:menu:info")
	@Log(value = "资源删除")
	public Result deletetUser(Integer menuId) {
		sysMenuService.deletetMenuByMenuId(menuId);
		return Result.ok();
	}
}
