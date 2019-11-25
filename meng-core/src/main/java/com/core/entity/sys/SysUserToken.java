package com.core.entity.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Data
public class SysUserToken implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userId;

	private String token;

}
