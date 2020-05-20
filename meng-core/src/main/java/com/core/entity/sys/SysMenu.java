package com.core.entity.sys;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Data
public class SysMenu extends BaseRowModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "menu_id", type = IdType.AUTO)
	private Integer menuId;

	@ApiModelProperty(value = "父菜单ID，一级菜单为0")
	private Integer parentId;

	@ApiModelProperty(value = "路由路径")
	private String path;

	@ApiModelProperty(value = "路由名")
	private String name;

	@ExcelProperty(value = "id", index = 0)
	@ApiModelProperty(value = "路由Title")
	private String title;

	@ExcelProperty(value = "name", index = 1)
	@ApiModelProperty(value = "菜单名称")
	private String notes;

	@ExcelProperty(value = "perms", index = 2)
	@ApiModelProperty(value = "授权(多个用逗号分隔，如：user:list,user:create)")
	@TableField(strategy = FieldStrategy.IGNORED)
	private String perms;

	@ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮")
	private Integer type;

	@ApiModelProperty(value = "菜单图标")
	private String icon;

	@ApiModelProperty(value = "排序")
	private Integer orderNum;

	@TableField(exist = false)
	private List<SysMenu> children;

}