package com.manage.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.core.common.aop.Log;
import com.core.common.base.AbstractController;
import com.core.entity.sys.Result;
import com.core.entity.sys.SysRole;
import com.core.entity.sys.SysUser;
import com.core.entity.sys.SysUserRole;
import com.core.mapper.sys.SysUserRoleMapper;
import com.manage.sys.service.SysRoleService;
import com.manage.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author:         知秋
 * CreateDate:     2019-08-30 20:25
 */
@Slf4j
@RestController
@RequestMapping("/sys/user")
@EnableScheduling
public class SysUserController extends AbstractController {

	@Value(value = "${user.defaltHeadUrl}")
	private String defaltHeadUrl;

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	//	@Scheduled(fixedRate = 5000)
	@Scheduled(cron = "0 30 5 * * ?")
	public void timeTask() {
		log.info("--------" + "定时任务" + "--------");
	}

	/**
	 * 用户信息
	 */
	@GetMapping("/info")
	@RequiresPermissions("sys:user:info")
	@Log(value = "用户信息")
	public Result info(Integer userId) {
		if (StringUtils.isEmpty(userId)) {
			userId = getUserId();
		}
		SysUser user = sysUserService.getById(userId);
		/*获取用户所属的角色列表*/
		List<SysRole> roleList = sysRoleService.queryUserRoles(userId);
		user.setRoleList(roleList);
		return Result.ok().put("user", user);
	}

	@PostMapping("/allUser")
	@RequiresPermissions("sys:user:info")
	@Log(value = "所有用户信息(角色）")
	public Result queryAllUser() {
		List<SysUser> sysUsers = sysUserService.queryAllUser();
		return Result.ok().put("sysUsers", sysUsers);
	}

	@PostMapping("/updateUser")
	@RequiresPermissions("sys:user:update")
	@Log(value = "用户修改")
	public Result updateUser(@RequestBody SysUser sysUser) {
		sysUserService.updateById(sysUser);
		return Result.ok();
	}

	@PostMapping("/insertUser")
	@RequiresPermissions("sys:user:info")
	@Log(value = "用户添加")
	public Result insertUser(@RequestParam Map<String, Object> params) {
		int userId = (int) System.currentTimeMillis();
		SysUser sysUser = new SysUser();
		sysUser.setUserId(userId);
		sysUser.setPassword("123456");
		sysUser.setSalt("123456");
		sysUser.setUsername(String.valueOf(params.get("username")));
		sysUser.setStatus((String) params.get("status"));
		if (StringUtils.isEmpty(params.get("headUrl"))) {
			sysUser.setHeadUrl(defaltHeadUrl);
		} else {
			sysUser.setHeadUrl(params.get("headUrl").toString());
		}
		sysUserService.insertUser(sysUser);

		/*角色处理*/
		if (!StringUtils.isEmpty((String) params.get("roleIdList"))) {
			String[] roleIdLists = ((String) params.get("roleIdList")).split(",");
			for (String info : roleIdLists) {
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(userId);
				sysUserRole.setRoleId(Integer.parseInt(info));
				sysUserRoleMapper.insert(sysUserRole);
			}
		}
		return Result.ok();
	}

	@PostMapping("/deletetUser/{userId}")
	@RequiresPermissions("sys:user:deletet")
	@Log(value = "用户删除")
	public Result deletetUser(@PathVariable("userId") String userId) {
		sysUserService.deletetUserByUserId(userId);
		return Result.ok();
	}

	@GetMapping("/queryAllRoleIshave/{userId}")
	@RequiresPermissions("sys:user:queryallRoleIshave")
	@Log(value = "用户拥有的角色")
	public Result queryAllRoleIshave(@PathVariable Integer userId) {
		List<SysRole> isSysRoles = sysRoleService.queryUserRoles(userId);
		List<SysRole> sysRoles = sysRoleService.queryAllRoles();
		sysRoles.forEach(info -> {
			if (isSysRoles.contains(info)) {
				info.setIsHave(true);
			}
		});
		return Result.ok().put("sysRoles", sysRoles);
	}

	@PostMapping("updateUserRoles")
	@RequiresPermissions("sys:user:updateRoles")
	@Log(value = "修改用户角色")
	public Result updateUserRoles(@RequestParam Map<String, String> param) {
		String[] roles = param.get("roles").split(",");
		String userId = param.get("userId");
		sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id", userId));
		for (int i = 0; i < roles.length; i++) {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setRoleId(Integer.parseInt(roles[i]));
			sysUserRole.setUserId(Integer.parseInt(userId));
			sysUserRoleMapper.insert(sysUserRole);
		}
		return Result.ok();
	}

}
