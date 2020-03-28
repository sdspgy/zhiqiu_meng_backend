package com.core.entity.welcome;

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
 * @since 2020-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Welcome对象", description="")
public class Welcome extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String imgId;

    private String imgUrl;

    private String text;

}
