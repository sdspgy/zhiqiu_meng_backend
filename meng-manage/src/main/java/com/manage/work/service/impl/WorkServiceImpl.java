package com.manage.work.service.impl;

import com.core.entity.work.Work;
import com.core.mapper.work.WorkMapper;
import com.manage.work.service.IWorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements IWorkService {

}
