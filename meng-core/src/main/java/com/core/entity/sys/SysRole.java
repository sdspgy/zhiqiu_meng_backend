package com.core.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Data
@ApiModel(value = "SysRole对象", description = "角色管理")
public class SysRole extends Model<SysRole> implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "role_id")
	private Long roleId;
	private String roleName;
	private String remark;
	@TableField(exist = false)
	private List<SysMenu> sysMenus;
	@TableField(exist = false)
	private Boolean isHave;

	@Override
	protected Serializable pkVal() {
		return null;
	}
}
