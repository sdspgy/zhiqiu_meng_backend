package com.manage.work.controller;


import com.core.common.base.AbstractController;
import com.core.common.utils.QiniuUploadUtils;
import com.core.entity.work.Work;
import com.manage.work.service.impl.WorkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    /*上传图片测试*/
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile[] file) {
        if (file.length > 0) {
            for (MultipartFile f : file) {
                String fileName = getUserId() + f.getOriginalFilename();
                try {
//                    QiniuUploadUtils.updateFile(f, fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @PostMapping("/uploadWork")
    public void uploadWork(String workName, String workText, String[] files) {
        StringBuffer worksPath = new StringBuffer();
        for (String filePath : files) {
            String[] fileSplit = filePath.split("/");
            worksPath.append(fileSplit[fileSplit.length - 1]).append(";");
        }
        String finalyWorksPath = worksPath.toString().substring(0, worksPath.length() - 1);
        Work work = new Work();
        work.setWorkId(UUID.randomUUID().toString());
        work.setUserId(getUserId());
        work.setStatus(0);
        work.setWorkName(workName);
        work.setWorkText(workText);
        work.setWorkImgs(finalyWorksPath);
        workService.save(work);
    }
}
