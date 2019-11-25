package com.core.entity.sys;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Data
public class SysLoginForm {

	@NotBlank
	@Length(min = 4, max = 12)
	private String username;
	@NotBlank
	@Length(min = 4, max = 12)
	private String password;
	//	private String uuid;
}