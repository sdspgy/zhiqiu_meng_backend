package com.manage.welcome.controller;

import com.core.entity.sys.Result;
import com.manage.welcome.service.impl.WelcomeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhiqiu
 * @since 2020-03-27
 */
@Slf4j
@RestController
@RequestMapping("/open/welcome")
public class WelcomeController {

	@Autowired
	private WelcomeServiceImpl welcomeService;

	@PostMapping("/info")
	public Result queryInfo() {
		List datas = welcomeService.queryInfos();
		return Result.ok()
						.put("datas", datas);
	}
}
