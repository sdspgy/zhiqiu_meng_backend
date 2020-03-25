package com.core.common.kafak;

import com.core.entity.sys.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhiqiu
 * @since 2019-11-26
 */
@RequestMapping(value = "/kafka")
@RestController
@Api(value = "KafkaController", tags = "KafkaController")
public class KafkaController {

	@Autowired
	private KafkaSender kafkaSender;

	@ApiOperation(value = "测试")
	@GetMapping(value = "/test")
	public Result test() {

		kafkaSender.sendChannelMess("testTopic", "caonima");

		//		SysConfig sysConfig = new SysConfig();
		//		sysConfig.setParamKey("11111");
		//
		//		kafkaSender.sendChannelMap("test2", sysConfig);
		return Result.ok();

	}
}
