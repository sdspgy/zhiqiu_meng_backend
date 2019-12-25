package com.manage.work.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.core.entity.sys.PageVo;
import com.core.entity.work.Work;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
public interface IWorkService extends IService<Work> {

    IPage<Work> queryWorkByUserId(Integer userId, Page initMpPage);

    void addWorkLookByWorkid(String workId);

    String queryWorkSupportUsers(String workId);

    void upWorkSupport(String workSupportUsers);

    void checkWorkStatus(String workId, int checkType);

    IPage<Work> queryWorksByType(int type, Page initMpPage);
}
