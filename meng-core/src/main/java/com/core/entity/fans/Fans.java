package com.core.entity.fans;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Fans对象", description="")
public class Fans extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "userId", index = 0)
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ExcelProperty(value = "fans", index = 0)
    @ApiModelProperty(value = "粉丝id,';'做分隔符")
    private String fans;


}
