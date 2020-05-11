package com.manage.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.core.common.base.AbstractController;
import com.core.common.utils.PageUtil;
import com.core.common.utils.StringUtils;
import com.core.entity.sys.PageVo;
import com.core.entity.sys.Result;
import com.core.entity.work.Work;
import com.manage.sys.service.SysUserService;
import com.manage.work.service.impl.WorkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhiqiu
 * @since 2019-12-20
 */

@RestController
@RequestMapping("/small")
public class UserInfoController extends AbstractController {

	@Autowired
	private SysUserService sysUserService;
	@Resource
	private WorkServiceImpl workService;

	@GetMapping("/queryUserSearchHistory")
	public Result queryUserSearchHistory() {
		String userSearchHistory = sysUserService.queryUserSearchHistory(getUserId());
		String[] splitUserSearchHistory = userSearchHistory.split(";");
		return Result.ok()
						.put("userSearchHistory", splitUserSearchHistory);
	}

	@PostMapping("workSearch")
	public Result workSearch(String searchText, @ModelAttribute PageVo pageVo) {
		IPage<Work> workIPage = workService.queryWorkSearch(searchText, PageUtil.initMpPage(pageVo));
		String userSearchHistory = sysUserService.queryUserSearchHistory(getUserId());
		if (StringUtils.isNotBlank(userSearchHistory)) {
			String[] splitUserSearchHistory = userSearchHistory.split(";");
			List<String> userSearchHistorys = Arrays.asList(splitUserSearchHistory);
			if (userSearchHistorys.size() >= 4) {
				userSearchHistorys.remove(userSearchHistorys.size() - 1);
			}
			userSearchHistorys.add(searchText);
			userSearchHistory = "";
			for (String ush : userSearchHistorys) {
				userSearchHistory = userSearchHistory + ush + ";";
			}
			userSearchHistory.substring(0, userSearchHistory.length() - 1);
		} else {
			userSearchHistory = searchText;
		}
		sysUserService.updateUserSearchHistory(userSearchHistory, getUserId());

		return Result.ok()
						.put("workIPage", workIPage);
	}
}
