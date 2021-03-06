package com.manage.light.service.impl;

import com.core.entity.light.Light;
import com.core.mapper.light.LightMapper;
import com.manage.light.service.ILightService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-20
 */
@Service
public class LightServiceImpl extends ServiceImpl<LightMapper, Light> implements ILightService {

	@Autowired
	private LightMapper lightMapper;

	@Override
	public void addSignByUserId(Integer userId) {
		Light light = new Light();
		light.setSignId(UUID.randomUUID().toString());
		light.setUserId(userId);
		lightMapper.insert(light);
	}

	@Override
	public Light isSignByUserId(Integer userId) {
		lightMapper.isSignByUserId(userId);
		return null;
	}


}
