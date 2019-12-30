package com.core.entity.comments;

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
@ApiModel(value = "Comments对象", description = "")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    private String commentId;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "作品id")
    private String workId;

    @ApiModelProperty(value = "评论内容")
    private String commentText;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "获赞")
    private int supportNum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
