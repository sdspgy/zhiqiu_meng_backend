package com.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth.service.SysUserTokenService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.core.common.async.AsyncFactory;
import com.core.common.async.AsyncManager;
import com.core.common.base.AbstractController;
import com.core.common.constant.SysConstants;
import com.core.common.exception.ErrorEnum;
import com.core.common.exception.MyException;
import com.core.common.utils.AesCbcUtil;
import com.core.common.utils.MessageUtils;
import com.core.common.utils.QiniuUploadUtils;
import com.core.common.utils.WxUtils;
import com.core.entity.sys.Result;
import com.core.entity.sys.SysLoginForm;
import com.core.entity.sys.SysUser;
import com.core.mapper.sys.SysUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Author:         知秋
 * CreateDate:     2019-08-30 20:24
 */
@Slf4j
@Api(tags = "sysLoginController", value = "sys-login-controller")
@RestController
public class SysLoginController extends AbstractController {

	@Autowired SysUserMapper sysUserMapper;

	@Autowired SysUserTokenService sysUserTokenService;

	@Value(value = "${user.password.maxRetryCount}")
	private String maxRetryCount;

	@ApiOperation(value = "/admin/sys/login", notes = "login")
	@ApiImplicitParams({
					@ApiImplicitParam(name = "SysLoginForm", value = "form", required = true, dataTypeClass = SysLoginForm.class)
	})
	@PostMapping("/admin/sys/login")
	public Result login(@RequestBody @Valid SysLoginForm form, BindingResult errorResult) {
		if (errorResult.hasErrors()) {
			List<ObjectError> errors = errorResult.getAllErrors();
			List<String> messAges = errors.stream().map(info -> info.getDefaultMessage()).collect(Collectors.toList());
			String msg = StringUtils.collectionToDelimitedString(messAges, ",");
			return Result.error(msg);
		}
		//用户信息
		SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
						.lambda()
						.eq(SysUser::getUsername, form.getUsername()));
		if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			//用户名或密码错误
			AsyncManager.me().execute(AsyncFactory.recordLogininfo(user.getUsername(), "fail", MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
			return Result.error(ErrorEnum.USERNAME_OR_PASSWORD_WRONG);
		}
		if (user.getStatus().equals(SysConstants.ACCOUNT_LOCKING)) {
			return Result.error(MessageUtils.message("user.blocked"));
		}

		//生成token，并保存到redis
		return sysUserTokenService.createToken(user.getUserId());
	}

	@ApiOperation(value = "/wx/sys/isAuthor", notes = "微信判断授权")
	@ApiImplicitParams({
					@ApiImplicitParam(name = "code", value = "code", required = true, dataTypeClass = String.class)
	})
	@PostMapping("/wx/sys/isAuthor")
	public Result isAuthor(String code) {
		if (StringUtils.isEmpty(code)) {
			throw new MyException(ErrorEnum.PARAM_ERROR);
		}
		JSONObject wxInfo = WxUtils.produceWxInfo(code);
		String openId = wxInfo.getString("openid");
		SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
						.lambda().eq(SysUser::getOpenId, openId));
		boolean isAuthor = true;
		if (Objects.isNull(sysUser)) {
			isAuthor = false;
		}
		return Result.ok().put("isAuthor", isAuthor);
	}

	@ApiOperation(value = "/wx/sys/login", notes = "微信登录")
	@ApiImplicitParams({
					@ApiImplicitParam(name = "encryptedData", value = "encryptedData", required = true, dataTypeClass = String.class),
					@ApiImplicitParam(name = "iv", value = "iv", required = true, dataTypeClass = String.class),
					@ApiImplicitParam(name = "code", value = "code", required = true, dataTypeClass = String.class)
	})
	@PostMapping("/wx/sys/login")
	public Result wxLogin(String encryptedData, String iv, String code) {
		log.debug("----------------------执行------------------/wx/sys/login");
		if (StringUtils.isEmpty(encryptedData) || StringUtils.isEmpty(iv) || StringUtils.isEmpty(code)) {
			throw new MyException(ErrorEnum.PARAM_ERROR);
		}
		//解析出openid
		JSONObject wxInfo = WxUtils.produceWxInfo(code);
		String openId = wxInfo.getString("openid");
		SysUser sysUser;
		try {
			//解析出详细信息
			String result = AesCbcUtil.decrypt(encryptedData, wxInfo.getString("session_key"), iv, "UTF-8");
			JSONObject detailedWxInfo = JSON.parseObject(result);
			sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
							.lambda().eq(SysUser::getOpenId, openId));
			if (StringUtils.isEmpty(sysUser)) {
				SysUser user = new SysUser();
				user.setUserId(new Random().nextInt(Integer.MAX_VALUE));
				user.setOpenId(openId);
				user.setUsername(detailedWxInfo.getString("nickName"));
				user.setHeadUrl(detailedWxInfo.getString("avatarUrl"));
				sysUserMapper.insert(user);
			}
			//重新查一次
			sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
							.lambda().eq(SysUser::getOpenId, openId));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		//生成token，并保存到redis
		Result token = sysUserTokenService.createToken(sysUser.getUserId());
		log.debug("----------------------结束------------------/wx/sys/login");
		return token;
	}


	/*excel测试*/
/*	@PutMapping("/expor")
	public void exporExcel(HttpServletResponse response) throws IOException {
		ExcelWriter writer = null;
		OutputStream outputStream = response.getOutputStream();
		try {
			//添加响应头信息
			head(response);
			//实例化 ExcelWriter
			writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);
			//实例化表单
			Sheet sheet = new Sheet(1, 0, SysUser.class);
			sheet.setSheetName("用户信息");
			//获取数据
			List<SysUser> userList = sysUserMapper.queryAllUser();
			//输出
			writer.write(userList, sheet);
			writer.finish();
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.getOutputStream().close();
		}
	}*/

	private void head(HttpServletResponse response) {
		response.setHeader("Content-disposition", "attachment; filename=" + "catagory.xls");
		response.setContentType("application/msexcel;charset=UTF-8");//设置类型
		response.setHeader("Pragma", "No-cache");//设置头
		response.setHeader("Cache-Control", "no-cache");//设置头
		response.setDateHeader("Expires", 0);//设置日期头
	}
}
