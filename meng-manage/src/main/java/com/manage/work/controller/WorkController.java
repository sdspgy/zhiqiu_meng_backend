package com.manage.work.controller;


import com.core.common.base.AbstractController;
import com.core.common.utils.QiniuUploadUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/work")
public class WorkController extends AbstractController {

    /*上传图片测试*/
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile[] file) {
        if (file.length > 0) {
            for (MultipartFile f : file) {
                String fileName = UUID.randomUUID().toString() + f.getOriginalFilename();

                try {
                    QiniuUploadUtils.updateFile(f, fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
