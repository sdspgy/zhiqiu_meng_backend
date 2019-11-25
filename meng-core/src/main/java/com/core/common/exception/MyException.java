package com.core.common.exception;

import lombok.Data;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Data
public class MyException extends RuntimeException {

	private String msg;
	private int code = 500;

	public MyException() {
		super(ErrorEnum.UNKNOWN.getMsg());
		this.msg = ErrorEnum.UNKNOWN.getMsg();
	}

	public MyException(ErrorEnum eEnum, Throwable e) {
		super(eEnum.getMsg(), e);
		this.msg = eEnum.getMsg();
		this.code = eEnum.getCode();
	}

	public MyException(ErrorEnum eEnum) {
		this.msg = eEnum.getMsg();
		this.code = eEnum.getCode();
	}

	public MyException(String exception) {
		this.msg = exception;
	}
}
