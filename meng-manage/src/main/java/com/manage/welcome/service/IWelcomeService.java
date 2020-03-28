package com.manage.welcome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.entity.welcome.Welcome;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhiqiu
 * @since 2020-03-27
 */
public interface IWelcomeService extends IService<Welcome> {

	List queryInfos();
}
