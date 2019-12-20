package com.manage.light.controller;

import com.core.common.base.AbstractController;
import com.core.common.constant.SignConstants;
import com.core.entity.sys.Result;
import com.manage.light.service.impl.LightServiceImpl;
import com.manage.sys.service.impl.SysUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-20
 */
@Slf4j
@RestController
@RequestMapping("/meng/userInfo")
@EnableScheduling
public class LightController extends AbstractController {

	@Resource
	private SysUserServiceImpl sysUserService;
	@Autowired
	private LightServiceImpl lightService;

	@PostMapping("/sign")
	public Result sign() {
		Boolean isSign = sysUserService.queryUserIsSign(getUserId());
		if (!isSign) {
			sysUserService.updateSign(getUserId(), SignConstants.OKSIGN);
			lightService.addSignByUserId(getUserId());
		}
		return Result.ok()
						.put("sign", isSign);
	}

	@Scheduled(cron = "0 30 5 * * ?")
	public void timeTaskUpdateSign() {
		sysUserService.updateSign(SignConstants.NOSIGN);
	}
}
