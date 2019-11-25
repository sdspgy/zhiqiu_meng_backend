package com.core.common.task;

import com.core.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Slf4j
@DisallowConcurrentExecution//大概意思是阻止并发执行
public class JobTask implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//具体业务
		log.info(ToolUtils.simpleDateFormat(System.currentTimeMillis()) + "hello!");
	}
}