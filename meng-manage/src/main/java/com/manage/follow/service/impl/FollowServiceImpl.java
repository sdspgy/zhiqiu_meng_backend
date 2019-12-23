package com.manage.follow.service.impl;

import com.core.entity.follow.Follow;
import com.core.mapper.follow.FollowMapper;
import com.manage.follow.service.IFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public Follow queryFollow(Integer userId) {
        Follow follow = followMapper.selectById(userId);
        return follow;
    }
}
