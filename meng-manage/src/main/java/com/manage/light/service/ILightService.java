package com.manage.light.service;

import com.core.entity.light.Light;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-20
 */
public interface ILightService extends IService<Light> {

	void addSignByUserId(Integer userId);

    Light isSignByUserId(Integer userId);
}
