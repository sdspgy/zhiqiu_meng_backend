package com.core.entity.piture;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhiqiu
 * @since 2020-06-04
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Picture对象", description="")
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "picture_id", type = IdType.AUTO)
    private Integer pictureId;

    @ApiModelProperty(value = "地址")
    private String pictureAddress;


}
