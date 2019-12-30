package com.manage.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.entity.sys.SysUser;

import java.util.List;

/**
 * Author:   taoyuzhu(taoyuzhu@hulai.com)
 * Date:     2019-07-10 17:15
 * Description:
 */
public interface SysUserService extends IService<SysUser> {

    /*查询所以用户信息（角色）*/
    List<SysUser> queryAllUser();

    void insertUser(SysUser sysUser);

    void deletetUserByUserId(String userId);

    Boolean queryUserIsSign(Integer userId);

    void updateSign(Integer userId, int oksign);

    void updateSign(int nosign);

    SysUser queryUserIdAndHeadImg(Integer userid);

    String queryUserSearchHistory(Integer userId);

    void updateUserSearchHistory(String userSearchHistory, Integer userId);
}
