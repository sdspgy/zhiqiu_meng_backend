package com.manage.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.core.common.aop.Log;
import com.core.common.base.AbstractController;
import com.core.common.utils.StringUtils;
import com.core.entity.sys.Result;
import com.core.entity.sys.SysMenu;
import com.core.entity.sys.SysRole;
import com.core.entity.sys.SysRoleMenu;
import com.core.mapper.sys.SysRoleMenuMapper;
import com.manage.sys.service.SysMenuService;
import com.manage.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author:         知秋
 * CreateDate:     2019-08-30 20:25
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	@PostMapping("/queryAllRoles")
	@RequiresPermissions("sys:role:info")
	@Log(value = "查询所有角色")
	public Result queryAllRoles() {
		List<SysRole> sysRoles = sysRoleService.queryAllRoles();
		return Result.ok().put("sysRoles", sysRoles);
	}

	@PostMapping("/insertRole")
	@RequiresPermissions("sys:role:info")
	@Log(value = "添加角色")
	public Result insertRole(@ModelAttribute SysRole sysRole) {
		sysRole.insert();
		return Result.ok();
	}

	@PostMapping("/updateRole")
	@RequiresPermissions("sys:role:info")
	@Log(value = "修改角色")
	public Result updateRole(@RequestBody SysRole sysRole) {
		sysRoleService.updateById(sysRole);
		return Result.ok();
	}

	@PostMapping("/deleteRole/{roleId}")
	@RequiresPermissions("sys:role:info")
	@Log(value = "删除角色")
	public Result deleteRole(@PathVariable("roleId") String[] roleId) {
		for (String roleid : roleId) {
			sysRoleService.deleteRoleByRoleId(roleid);
		}
		return Result.ok();
	}

	@PostMapping("/queryAllMenuIshave/{roleId}")
	@RequiresPermissions("sys:role:info")
	@Log(value = "角色拥有的资源")
	public Result queryAllMenuIshave(@PathVariable Integer roleId) {
		List<SysRoleMenu> isHaveMenus = sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
		Set<Integer> isHaveMenuIds = isHaveMenus.stream().map(info -> info.getMenuId()).collect(Collectors.toSet());
		List<SysMenu> sysMenus = sysMenuService.querySysMenuTree(0);
		return Result.ok().put("sysMenus", sysMenus).put("isHaveMenuIds", isHaveMenuIds);
	}

	@PostMapping("/updateRoleMenus")
	@RequiresPermissions("sys:role:info")
	@Log(value = "修改角色资源")
	public Result updateRoleMenus(@RequestParam Map<String, String> param) {
		if (StringUtils.isNotBlank(param.get("menuIds"))) {
			String[] menus = param.get("menuIds").split(",");
			String roleId = param.get("roleId");
			sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
			for (int i = 0; i < menus.length; i++) {
				SysRoleMenu sysUserRole = new SysRoleMenu();
				sysUserRole.setMenuId(Integer.parseInt(menus[i]));
				sysUserRole.setRoleId(Integer.parseInt(roleId));
				sysRoleMenuMapper.insert(sysUserRole);
			}
		}
		return Result.ok();
	}
}
