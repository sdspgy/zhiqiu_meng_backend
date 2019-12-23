package com.core.entity.work;

import java.time.LocalDateTime;
import java.io.Serializable;
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
@ApiModel(value="Work对象", description="")
public class Work implements Serializable {

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

    @ApiModelProperty(value = "作品浏览数")
    private Integer workLook;

    @ApiModelProperty(value = "作品描述")
    private String workText;

    @ApiModelProperty(value = "分组作家")
    private String groupWriter;

    @ApiModelProperty(value = "分组作品集")
    private String groupWork;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
