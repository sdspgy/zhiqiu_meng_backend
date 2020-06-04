package com.manage.task;

import com.core.entity.sys.Result;
import com.core.entity.task.TaskDefine;
import com.core.entity.task.TaskShow;
import com.manage.task.service.QuartzJobService;
import io.swagger.annotations.Api;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
@RestController
@RequestMapping("/sys/task")
@Api(tags = "JobTaskController", value = "JobTaskController")
public class JobTaskController {

	@Resource
	private QuartzJobService quartzJobService;

	/**
	 * 查询所有任务
	 */
	@GetMapping("/quaryAllTask")
	public Result quaryAllTask() {
		List<TaskShow> taskShowList = quartzJobService.quaryAllTask();
		return Result.ok().put("taskShowList", taskShowList);
	}

	/**
	 * 启动
	 */
	@PostMapping("/startTask")
	public Result startTask(@RequestBody TaskShow taskShow) throws ClassNotFoundException {
		JobKey jobKey = JobKey.jobKey(taskShow.getName(), taskShow.getGroup());
		Class<JobTask> jobTaskClass = (Class<JobTask>) Class.forName(taskShow.getJobTask());
		//创建一个定时任务
		TaskDefine task = TaskDefine.builder()
						.jobKey(jobKey)
						.cronExpression(taskShow.getCronExpression())   //定时任务 的cron表达式
						.jobClass(jobTaskClass)   //定时任务 的具体执行逻辑
						.description(taskShow.getDescription())    //定时任务 的描述
						.build();

		try {
			quartzJobService.scheduleJob(task);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}

	/**
	 * 暂停
	 */
	@PostMapping("/pauseTask")
	public Result pauseTask(String name, String group) {
		JobKey jobKey = JobKey.jobKey(name, group);
		try {
			quartzJobService.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}

	/**
	 * 恢复
	 */
	@PostMapping("/resumeTask")
	public Result resumeTask(String name, String group) {
		JobKey jobKey = JobKey.jobKey(name, group);
		try {
			quartzJobService.resumeJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/deleteTask")
	public Result deleteTask(String name, String group) {
		JobKey jobKey = JobKey.jobKey(name, group);
		try {
			quartzJobService.deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}

	/**
	 * 修改
	 */
	@PostMapping("/modifyTask")
	public Result modifyTask(@RequestBody TaskShow taskShow) throws ClassNotFoundException {
		JobKey jobKey = JobKey.jobKey(taskShow.getName(), taskShow.getGroup());
		Class<JobTask> jobTaskClass = (Class<JobTask>) Class.forName(taskShow.getJobTask());
		//这是即将要修改cron的定时任务
		TaskDefine modifyCronTask = TaskDefine.builder()
						.jobKey(jobKey)
						.cronExpression(taskShow.getCronExpression())   //定时任务 的cron表达式
						.jobClass(jobTaskClass)   //定时任务 的具体执行逻辑
						.description(taskShow.getDescription())    //定时任务 的描述
						.build();
		Result result = Result.ok();
		if (quartzJobService.modifyJobCron(modifyCronTask)) {
			return result;
		} else {
			return result;
		}
	}
}