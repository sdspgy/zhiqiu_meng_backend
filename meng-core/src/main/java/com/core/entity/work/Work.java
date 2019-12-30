package com.core.entity.work;

import com.core.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @since 2019-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Work对象", description = "")
public class Work extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "作品id")
    private String workId;

    @ApiModelProperty(value = "发布者id")
    private Integer userId;

    @ApiModelProperty(value = "作品名")
    private String workName;

    @ApiModelProperty(value = "作品图片链接地址")
    private String workImgs;

    @ApiModelProperty(value = "作品状态")
    private Integer status;

    @ApiModelProperty(value = "作品赞数")
    private Integer workSupport;

    @ApiModelProperty(value = "点赞人userId,';'作分隔符")
    private String workSupportUsers;

    @ApiModelProperty(value = "作品浏览数")
    private Integer workLook;

    @ApiModelProperty(value = "作品描述")
    private String workText;

    @ApiModelProperty(value = "分组作家")
    private String groupWriter;

    @ApiModelProperty(value = "分组作品集")
    private String groupWork;

}
