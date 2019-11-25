package com.core.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Data
@ApiModel(value = "SysRoleMenu对象", description = "角色与菜单对应关系")
public class SysRoleMenu extends Model<SysRoleMenu> implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "角色ID")
	private Integer roleId;

	@ApiModelProperty(value = "菜单ID")
	private Integer menuId;

	@Override
	protected Serializable pkVal() {
		return null;
	}
}
