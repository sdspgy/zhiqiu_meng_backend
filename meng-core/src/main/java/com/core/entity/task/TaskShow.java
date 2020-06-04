package com.core.entity.task;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhiqiu
 * @since 2020-06-02
 */
@Data
@Builder
public class TaskShow {

	private String name;
	private String group;
	private String cronExpression;
	private String description;
	private String state;
	private String jobTask;
}
