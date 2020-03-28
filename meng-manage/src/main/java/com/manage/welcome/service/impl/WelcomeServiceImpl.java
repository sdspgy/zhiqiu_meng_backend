package com.manage.welcome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.core.entity.welcome.Welcome;
import com.core.mapper.welcome.WelcomeMapper;
import com.manage.welcome.service.IWelcomeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhiqiu
 * @since 2020-03-27
 */
@Service
public class WelcomeServiceImpl extends ServiceImpl<WelcomeMapper, Welcome> implements IWelcomeService {

	@Autowired
	private WelcomeMapper welcomeMapper;

	@Override
	public List queryInfos() {
		QueryWrapper<Welcome> queryWrapper = new QueryWrapper<>();
		List<Welcome> welcomes = welcomeMapper.selectList(queryWrapper);
		return welcomes;
	}
}
