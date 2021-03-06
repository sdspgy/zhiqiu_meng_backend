package com.manage.task.service;

import com.core.common.enums.TaskState;
import com.core.entity.task.TaskDefine;
import com.core.entity.task.TaskShow;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@Slf4j
@Service
public class QuartzJobService {

	//Quartz定时任务核心的功能实现类
	private Scheduler scheduler;

	public QuartzJobService(@Autowired SchedulerFactoryBean schedulerFactoryBean) {
		scheduler = schedulerFactoryBean.getScheduler();
	}

	/**
	 * 查询所有的任务
	 *
	 * @return
	 */
	public List<TaskShow> quaryAllTask() {
		List<TaskShow> taskShowList = Lists.newArrayList();
		try {
			//再获取Scheduler下的所有group
			List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
			for (String groupName : triggerGroupNames) {
				//组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
				GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
				//获取所有的triggerKey
				Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
				for (TriggerKey triggerKey : triggerKeySet) {
					String taskStateEnName = TaskState.getEnName(String.valueOf(scheduler.getTriggerState(triggerKey)));
					//通过triggerKey在scheduler中获取trigger对象
					CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
					//获取trigger拥有的Job
					JobKey jobKey = trigger.getJobKey();
					JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
					TaskShow taskShow = TaskShow.builder().name(jobKey.getName()).group(jobKey.getGroup()).cronExpression(trigger.getCronExpression()).state(taskStateEnName)
									.description(jobDetail.getDescription()).jobTask(jobDetail.getJobClass().getName()).build();
					taskShowList.add(taskShow);
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return taskShowList;
	}

	/**
	 * 创建和启动 定时任务
	 * {@link Scheduler#scheduleJob(JobDetail, Trigger)}
	 *
	 * @param define 定时任务
	 */
	public void scheduleJob(TaskDefine define) throws SchedulerException {
		//1.定时任务 的 名字和组名
		JobKey jobKey = define.getJobKey();
		//2.定时任务 的 元数据
		JobDataMap jobDataMap = getJobDataMap(define.getJobDataMap());
		//3.定时任务 的 描述
		String description = define.getDescription();
		//4.定时任务 的 逻辑实现类
		Class<? extends Job> jobClass = define.getJobClass();
		//5.定时任务 的 cron表达式
		String cron = define.getCronExpression();
		JobDetail jobDetail = getJobDetail(jobKey, description, jobDataMap, jobClass);
		Trigger trigger = getTrigger(jobKey, description, jobDataMap, cron);
		scheduler.scheduleJob(jobDetail, trigger);
	}

	/**
	 * 暂停Job
	 * {@link Scheduler#pauseJob(JobKey)}
	 */
	public void pauseJob(JobKey jobKey) throws SchedulerException {
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复Job
	 * {@link Scheduler#resumeJob(JobKey)}
	 */
	public void resumeJob(JobKey jobKey) throws SchedulerException {
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除Job
	 * {@link Scheduler#deleteJob(JobKey)}
	 */
	public void deleteJob(JobKey jobKey) throws SchedulerException {
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 修改Job 的cron表达式
	 */
	public boolean modifyJobCron(TaskDefine define) {
		String cronExpression = define.getCronExpression();
		//1.如果cron表达式的格式不正确,则返回修改失败
		if (!CronExpression.isValidExpression(cronExpression)) {
			return false;
		}
		JobKey jobKey = define.getJobKey();
		TriggerKey triggerKey = new TriggerKey(jobKey.getName(), jobKey.getGroup());
		try {
			CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			JobDataMap jobDataMap = getJobDataMap(define.getJobDataMap());
			//2.如果cron发生变化了,则按新cron触发 进行重新启动定时任务
			if (!cronTrigger.getCronExpression().equalsIgnoreCase(cronExpression)) {
				CronTrigger trigger = TriggerBuilder.newTrigger()
								.withIdentity(triggerKey)
								.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
								.usingJobData(jobDataMap)
								.build();
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		} catch (SchedulerException e) {
			log.error("printStackTrace", e);
			return false;
		}
		return true;
	}

	/**
	 * 获取定时任务的定义
	 * JobDetail是任务的定义,Job是任务的执行逻辑
	 *
	 * @param jobKey      定时任务的名称 组名
	 * @param description 定时任务的 描述
	 * @param jobDataMap  定时任务的 元数据
	 * @param jobClass    {@link Job} 定时任务的 真正执行逻辑定义类
	 */
	public JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap jobDataMap, Class<? extends Job> jobClass) {
		return JobBuilder.newJob(jobClass)
						.withIdentity(jobKey)
						.withDescription(description)
						.setJobData(jobDataMap)
						.usingJobData(jobDataMap)
						.requestRecovery()
						.storeDurably()
						.build();
	}

	/**
	 * 获取Trigger (Job的触发器,执行规则)
	 *
	 * @param jobKey         定时任务的名称 组名
	 * @param description    定时任务的 描述
	 * @param jobDataMap     定时任务的 元数据
	 * @param cronExpression 定时任务的 执行cron表达式
	 */
	public Trigger getTrigger(JobKey jobKey, String description, JobDataMap jobDataMap, String cronExpression) {
		return TriggerBuilder.newTrigger()
						.withIdentity(jobKey.getName(), jobKey.getGroup())
						.withDescription(description)
						.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
						.usingJobData(jobDataMap)
						.build();
	}

	public JobDataMap getJobDataMap(Map<?, ?> map) {
		return map == null ? new JobDataMap() : new JobDataMap(map);
	}

}