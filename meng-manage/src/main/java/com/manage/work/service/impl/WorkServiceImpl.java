package com.manage.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.core.common.constant.PushWorkConstants;
import com.core.common.constant.WorkConstans;
import com.core.common.utils.ToolUtils;
import com.core.entity.sys.PageVo;
import com.core.entity.work.Work;
import com.core.mapper.work.WorkMapper;
import com.manage.work.service.IWorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements IWorkService {

    @Resource
    WorkMapper workMapper;

    @Override
    public IPage<Work> queryWorkByUserId(Integer userId, Page initMpPage) {
        QueryWrapper<Work> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId)
                .eq("status", WorkConstans.CHECK_SUCCESS)
                .orderByDesc("create_time");
        return this.page(initMpPage, queryWrapper);
    }

    @Override
    public void addWorkLookByWorkid(String workId) {
        workMapper.addWorkLookByWorkid(workId);
    }

    @Override
    public String queryWorkSupportUsers(String workId) {
        String workSupportUsers = workMapper.queryWorkSupportUsers(workId);
        return workSupportUsers;
    }

    @Override
    public void upWorkSupport(String workSupportUsers) {
        workMapper.upWorkSupport(workSupportUsers);
    }

    @Override
    public void checkWorkStatus(String workId, int checkType) {
        workMapper.checkWorkStatus(workId, checkType);
    }

    @Override
    public IPage<Work> queryWorksByType(int type, Page initMpPage) {
        QueryWrapper<Work> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", WorkConstans.CHECK_SUCCESS);
        if (type == PushWorkConstants.HOT) {
            queryWrapper.orderByDesc("work_look");
        }
        if (type == PushWorkConstants.NEW) {
            queryWrapper.orderByDesc("create_time");
        }
        return this.page(initMpPage, queryWrapper);
    }

    @Override
    public IPage<Work> queryWorkSearch(String searchText, Page initMpPage) {
        QueryWrapper<Work> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", WorkConstans.CHECK_SUCCESS);
        queryWrapper.like("work_name", searchText);
        return this.page(initMpPage, queryWrapper);
    }

    @Override
    public List<Work> querySupportRank() {
        QueryWrapper<Work> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", WorkConstans.CHECK_SUCCESS);
        queryWrapper.lambda().ge(Work::getCreateTime, ToolUtils.lastWeek());
        queryWrapper.lambda().orderByDesc(Work::getWorkSupport);
        queryWrapper.apply("limit 10");
        return workMapper.selectList(queryWrapper);
    }

    @Override
    public List<Work> queryLookRank() {
        QueryWrapper<Work> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", WorkConstans.CHECK_SUCCESS);
        queryWrapper.lambda().ge(Work::getCreateTime, ToolUtils.lastWeek());
        queryWrapper.lambda().orderByDesc(Work::getWorkLook);
        queryWrapper.apply("limit 10");
        return workMapper.selectList(queryWrapper);
    }
}
