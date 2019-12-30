package com.manage.fans.service.impl;

import com.core.entity.fans.Fans;
import com.core.mapper.fans.FansMapper;
import com.manage.fans.service.IFansService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
@Service
public class FansServiceImpl extends ServiceImpl<FansMapper, Fans> implements IFansService {

    @Resource
    private FansMapper fansMapper;

    @Override
    public Fans queryFans(Integer userId) {
        Fans fans = fansMapper.selectById(userId);
        return fans;
    }

    @Override
    public void initFans(Fans fans) {
        fansMapper.insert(fans);
    }
}
