package com.manage.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.core.common.aop.Log;
import com.core.common.base.AbstractController;
import com.core.common.exception.ErrorEnum;
import com.core.common.utils.MessageUtils;
import com.core.entity.sys.Result;
import com.core.entity.sys.SysRole;
import com.core.entity.sys.SysUser;
import com.core.entity.sys.SysUserRole;
import com.core.mapper.sys.SysUserRoleMapper;
import com.manage.sys.service.SysRoleService;
import com.manage.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
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
	@RequiresPermissions("sys:user:info")
	@Log(value = "用户修改")
	public Result updateUser(@RequestBody SysUser sysUser) {
		sysUserService.updateById(sysUser);
		return Result.ok();
	}

	@GetMapping("/changeUserHeadUrl")
	@RequiresPermissions("sys:user:info")
	public Result changeUserHeadUrl(String headUrl) {
		SysUser sysUser = new SysUser();
		if (StringUtils.isEmpty(headUrl)) {
			sysUser.setHeadUrl(defaltHeadUrl);
		} else {
			sysUser.setHeadUrl(headUrl);
		}
		sysUserService.update(sysUser, new UpdateWrapper<SysUser>().lambda().eq(SysUser::getUserId, getUserId()));
		return Result.ok();
	}

	@PostMapping("/changePassword")
	public Result changePassword(String oldPassword, String newPassword) {
		SysUser user = sysUserService.getById(getUserId());
		if (user.getPassword().equals(new Sha256Hash(oldPassword, user.getSalt()).toHex())) {
			SysUser sysUser = new SysUser();
			sysUser.setPassword(new Sha256Hash(newPassword, user.getSalt()).toHex());
			sysUserService.update(sysUser, new UpdateWrapper<SysUser>().lambda().eq(SysUser::getUserId, getUserId()));
		} else {
			return Result.error(ErrorEnum.PARAM_ERROR.getCode(), MessageUtils.message("user.password.not.match"));
		}
		return Result.ok();
	}

	@PostMapping("/insertUser")
	@RequiresPermissions("sys:user:info")
	@Log(value = "用户添加")
	public Result insertUser(@RequestParam Map<String, Object> params) {
		int userId = (int) System.currentTimeMillis();
		SysUser sysUser = new SysUser();
		sysUser.setUserId(userId);
		sysUser.setPassword(new Sha256Hash("123456", "123456").toHex());
		sysUser.setSalt("123456");
		sysUser.setUsername(String.valueOf(params.get("username")));
		sysUser.setStatus((String) params.get("status"));
		if (StringUtils.isEmpty(params.get("headUrl"))) {
			sysUser.setHeadUrl(defaltHeadUrl);
		} else {
			sysUser.setHeadUrl(params.get("headUrl").toString());
		}
		sysUserService.insertUser(sysUser);
		return Result.ok();
	}

	@PostMapping("/deletetUser/{userId}")
	@RequiresPermissions("sys:user:info")
	@Log(value = "用户删除")
	public Result deletetUser(@PathVariable("userId") String[] userIds) {
		for (int i = 0; i < userIds.length; i++) {
			sysUserService.deletetUserByUserId(userIds[i]);
		}
		return Result.ok();
	}

	@PostMapping("updateUserRoles")
	@RequiresPermissions("sys:user:info")
	@Log(value = "修改用户角色")
	public Result updateUserRoles(@RequestParam Map<String, String> param) {
		String userId = param.get("userId");
		sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id", userId));
		if (!StringUtils.isEmpty(param.get("roles"))) {
			String[] roles = param.get("roles").split(",");
			for (int i = 0; i < roles.length; i++) {
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setRoleId(Integer.parseInt(roles[i]));
				sysUserRole.setUserId(Integer.parseInt(userId));
				sysUserRoleMapper.insert(sysUserRole);
			}
		}
		return Result.ok();
	}

}
