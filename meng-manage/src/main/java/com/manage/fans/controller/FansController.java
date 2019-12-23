package com.manage.fans.controller;


import com.core.common.base.AbstractController;
import com.core.common.utils.ToolUtils;
import com.core.entity.fans.Fans;
import com.core.entity.follow.Follow;
import com.core.entity.sys.Result;
import com.core.entity.sys.SysUser;
import com.manage.fans.service.impl.FansServiceImpl;
import com.manage.follow.service.impl.FollowServiceImpl;
import com.manage.sys.service.impl.SysUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
@Slf4j
@RestController
@RequestMapping("/meng/fans")
public class FansController extends AbstractController {

    @Resource
    private SysUserServiceImpl sysUserService;
    @Resource
    private FollowServiceImpl followService;
    @Autowired
    private FansServiceImpl fansService;

    public Result addFans() {

        return Result.ok();
    }

    //查看自己的粉丝
    @PostMapping("/queryMyFans")
    public Result queryMyFans() {
        Fans fans = fansService.queryFans(getUserId());
        String[] fanss = fans.getFans().split(";");
        List<SysUser> sysUsers = Lists.newArrayList();
        for (String userid : fanss) {
            SysUser sysUser = sysUserService.queryUserIdAndHeadImg(Integer.valueOf(userid));
            sysUsers.add(sysUser);
        }
        return Result.ok()
                .put("fans", sysUsers);
    }

    //关注别人
    @PostMapping("/addFans")
    public Result addFans(Integer iUserId) {
        //被关注的人粉丝加自己
        Fans fans = fansService.queryFans(iUserId);
        //是否已经关注
        String[] fanss = fans.getFans().split(";");
        if (!Arrays.asList(fanss).contains(iUserId.toString())) {
            String addNewFans = fans.getFans() + ";" + getUserId().toString();
            fans.setFans(addNewFans);
            fansService.updateById(fans);
            //自己的关注加别人
            Follow follow = followService.queryFollow(getUserId());
            String addNewFollow = follow.getFollows() + ";" + iUserId.toString();
            follow.setFollows(addNewFollow);
            followService.updateById(follow);
        }
        return Result.ok();
    }
}
