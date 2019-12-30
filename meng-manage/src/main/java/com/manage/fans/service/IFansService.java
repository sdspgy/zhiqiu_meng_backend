package com.manage.fans.service;

import com.core.entity.fans.Fans;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
public interface IFansService extends IService<Fans> {

    Fans queryFans(Integer userId);

    void initFans(Fans fans);
}
