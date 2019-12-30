package com.manage.light.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.core.common.base.AbstractController;
import com.core.common.constant.SignConstants;
import com.core.entity.sys.Result;
import com.core.entity.light.Light;
import com.manage.light.service.impl.LightServiceImpl;
import com.manage.sys.service.impl.SysUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-20
 */
@Slf4j
@RestController
@RequestMapping("/meng/light")
@EnableScheduling
public class LightController extends AbstractController {

    @Resource
    private SysUserServiceImpl sysUserService;
    @Autowired
    private LightServiceImpl lightService;

    @PostMapping("/sign")
    public Result sign() {
//		Boolean isSign = sysUserService.userIsSign(getUserId());
        Light light = lightService.isSignByUserId(getUserId());
        Boolean isSign = false;
        if (Objects.isNull(light)) {
            isSign = true;
//			sysUserService.updateSign(getUserId(), SignConstants.OKSIGN);
            lightService.addSignByUserId(getUserId());
        }
        return Result.ok()
                .put("sign", isSign);
    }

    @GetMapping("/signNum")
    public Result signNum() {
        int signNum = lightService.count(new QueryWrapper<Light>().lambda().ge(Light::getCreateTime, new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())));
        return Result.ok()
                .put("signNum", signNum);
    }
}
