package com.manage.follow.service;

import com.core.entity.follow.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
public interface IFollowService extends IService<Follow> {

    Follow queryFollow(Integer userId);

    void initFollow(Follow follow);
}
