package com.core.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Getter
@AllArgsConstructor
public enum ErrorEnum {

	/*系统错误*/
	UNKNOWN(500, "系统内部错误，请联系管理员"),
	PATH_NOT_FOUND(404, "路径不存在，请检查路径"),
	NO_AUTH(403, "没有权限，请联系管理员"),
	DUPLICATE_KEY(501, "数据库中已存在该记录"),
	TOKEN_GENERATOR_ERROR(502, "token生成失败"),
	NO_UUID(503, "uuid为空"),
	SQL_ILLEGAL(504, "sql非法"),
	/*普通错误*/
	TOKEN_INVALID(601, "token失效"),
	/*用户权限错误*/
	INVALID_TOKEN(1001, "token不合法"),
	/*登录模块错误*/
	LOGIN_FAIL(10001, "登录失败"),
	CAPTCHA_WRONG(10002, "验证码错误"),
	USERNAME_OR_PASSWORD_WRONG(10003, "用户名或密码错误"),
	ACCOUNT_LOCKING(10004, "账号已锁定"),
	/*七牛OSS错误*/
	OSS_CONFIG_ERROR(10050, "七牛配置信息错误"),
	OSS_UPLOAD_ERROR(10051, "OSSBookNote上传失败"),

	PARAM_ERROR(7000, "参数有误");
	private int code;
	private String msg;
}
