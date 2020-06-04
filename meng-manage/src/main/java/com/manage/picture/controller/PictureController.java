package com.manage.picture.controller;

import com.core.entity.piture.Picture;
import com.core.entity.sys.Result;
import com.manage.picture.service.impl.PictureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhiqiu
 * @since 2020-06-04
 */
@RestController
@RequestMapping("/sys/picture")
public class PictureController {

	@Autowired
	private PictureServiceImpl pictureService;

	@GetMapping("/quaryAllPicture")
	public Result quaryAllPicture() {
		List<Picture> pictureList = pictureService.list();
		Collections.reverse(pictureList);
		return Result.ok().put("pictureList", pictureList);
	}
}
