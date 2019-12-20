package com.manage.light.entity;

import com.core.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
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
 * @since 2019-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Light对象", description="")
public class Light extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String signId;

    private Integer userId;

}
