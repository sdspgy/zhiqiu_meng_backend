package com.manage.sys.controller;

import com.core.common.constant.VariableName;
import com.core.common.utils.QiniuUploadUtils;
import com.core.entity.piture.Picture;
import com.manage.picture.service.impl.PictureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author zhiqiu
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/sys/qiniu")
public class QiniuController {

	@Autowired
	private PictureServiceImpl pictureService;

	/*上传图片到七牛云*/
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestBody MultipartFile[] file) {
		if (file.length > 0) {
			for (MultipartFile f : file) {
				String heardRandom = UUID.randomUUID().toString();
				String fileName = heardRandom + f.getOriginalFilename();
				String fileNameAddress = VariableName.domain + heardRandom + f.getOriginalFilename();
				Picture picture = Picture.builder().pictureAddress(fileNameAddress).build();
				pictureService.save(picture);
				try {
					QiniuUploadUtils.updateFile(f, fileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
