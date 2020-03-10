package com.manage.work.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.core.common.base.AbstractController;
import com.core.common.constant.WorkConstans;
import com.core.common.utils.PageUtil;
import com.core.common.utils.QiniuUploadUtils;
import com.core.common.utils.StringUtils;
import com.core.entity.sys.PageVo;
import com.core.entity.sys.Result;
import com.core.entity.work.Work;
import com.manage.work.service.impl.WorkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
@RestController
@RequestMapping("/small/work")
public class WorkController extends AbstractController {

    @Autowired
    private WorkServiceImpl workService;

    /*上传图片到七牛云*/
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile[] file) {
        if (file.length > 0) {
            for (MultipartFile f : file) {
                String fileName = getUserId() + f.getOriginalFilename();
                try {
                    QiniuUploadUtils.updateFile(f, fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @PostMapping("/uploadWork")
    public Result uploadWork(String workName, String workText, String[] files) {
        if (files.length > 0) {
            StringBuffer worksPath = new StringBuffer();
            for (String filePath : files) {
                String[] fileSplit = filePath.split("/");
                worksPath.append("http://cdn.mvptyz.cn/").append(fileSplit[fileSplit.length - 1]).append(";");
            }
            String finalyWorksPath = worksPath.toString().substring(0, worksPath.length() - 1);
            Work work = new Work();
            work.setWorkId(UUID.randomUUID().toString());
            work.setUserId(getUserId());
            work.setStatus(WorkConstans.STAY_CHECK);
            work.setWorkName(workName);
            work.setWorkText(workText);
            work.setWorkImgs(finalyWorksPath);
            workService.save(work);
        }
        return Result.ok();
    }

    @PostMapping("queryWorksByType")
    public Result queryWorksByType(int type, @ModelAttribute PageVo pageVo) {
        IPage<Work> workIPage = workService.queryWorksByType(type, PageUtil.initMpPage(pageVo));
        return Result.ok()
                .put("works", workIPage);
    }

    @PostMapping("/queryWorkByUserId")
    public Result queryWorkByUserId(Integer userId, @ModelAttribute PageVo pageVo) {
        if (null == userId) {
            userId = getUserId();
        }
        IPage<Work> workIPage = workService.queryWorkByUserId(userId, PageUtil.initMpPage(pageVo));
        return Result.ok()
                .put("works", workIPage);
    }

    @PostMapping("/addWorkLookByWorkid")
    public Result addWorkLookByWorkid(String workId) {
        workService.addWorkLookByWorkid(workId);
        return Result.ok();
    }

    @PostMapping("/upWorkSupport")
    public Result upWorkSupport(String workId) {
        String workSupportUsers = workService.queryWorkSupportUsers(workId);
        if (StringUtils.isBlank(workSupportUsers)) {
            workSupportUsers = getUserId().toString();
            workService.upWorkSupport(workSupportUsers);
        }
        String[] splitWorkSupportUsers = workSupportUsers.split(";");
        if (!Arrays.asList(splitWorkSupportUsers).contains(getUserId().toString())) {
            workSupportUsers = workSupportUsers + ";" + getUserId().toString();
            workService.upWorkSupport(workSupportUsers);
        }
        return Result.ok();
    }

    @PostMapping("/checkWorkStatus")
    public Result checkWorkStatus(String[] workIds, int checkType) {
        if (WorkConstans.CHECK_SUCCESS == checkType) {
            for (String workId : workIds) {
                workService.checkWorkStatus(workId, checkType);
            }
        }
        if (WorkConstans.CHECK_FAIL == checkType) {
            for (String workId : workIds) {
                workService.checkWorkStatus(workId, checkType);
            }
        }
        return Result.ok();
    }

    @PostMapping("/rank")
    public Result rank() {
        List<Work> worksSupportRank = workService.querySupportRank();
        workService.queryLookRank();
        return Result.ok();
    }
}
