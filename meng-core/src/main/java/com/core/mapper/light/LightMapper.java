package com.core.mapper.light;

import com.core.entity.light.Light;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-20
 */
public interface LightMapper extends BaseMapper<Light> {

    Light isSignByUserId(Integer userId);
}
