package com.manage.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.core.entity.sys.SysUser;
import com.core.mapper.sys.SysUserMapper;
import com.manage.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:   taoyuzhu(taoyuzhu@hulai.com)
 * Date:     2019-07-10 17:34
 * Description:
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    /*查询所以用户信息（角色）*/
    @Override
    public List<SysUser> queryAllUser() {
        return sysUserMapper.queryAllUser();
    }

    @Override
    public void insertUser(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }

    @Override
    public void deletetUserByUserId(String userId) {
        sysUserMapper.delete(new QueryWrapper<SysUser>().eq("user_id", userId));
    }

    @Override
    public Boolean queryUserIsSign(Integer userId) {
        SysUser sysUser = sysUserMapper.queryUserIsSign(userId);
//		return sysUser.isSign();
        return null;
    }

    @Override
    public void updateSign(Integer userId, int oksign) {
        sysUserMapper.updateSign(userId, oksign);
    }

    @Override
    public void updateSign(int nosign) {
        sysUserMapper.updateSignTimeTask(nosign);
    }

    @Override
    public SysUser queryUserIdAndHeadImg(Integer userid) {
        SysUser sysUser = sysUserMapper.queryUserIdAndHeadImg(userid);
        return sysUser;
    }

    @Override
    public String queryUserSearchHistory(Integer userId) {
        String userSearchHistory = sysUserMapper.queryUserSearchHistory(userId);
        return userSearchHistory;
    }

    @Override
    public void updateUserSearchHistory(String userSearchHistory, Integer userId) {
        sysUserMapper.updateUserSearchHistory(userSearchHistory, userId);
    }
}
