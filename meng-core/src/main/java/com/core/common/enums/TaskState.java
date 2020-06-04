package com.core.common.enums;

/**
 * @author zhiqiu
 * @since 2020-06-02
 */
public enum TaskState {

	BLOCKED("BLOCKED", "阻塞"), COMPLETE("COMPLETE", "完成"), PAUSED("PAUSED", "暂停"),
	NONE("NONE", "不存在"), ERROR("ERROR", "错误"), NORMAL("NORMAL", "正常");

	private String name;
	private String EnName;

	TaskState(String name, String enName) {
		this.name = name;
		EnName = enName;
	}

	public static String getEnName(String name) {
		for (TaskState taskState : TaskState.values()) {
			if (taskState.name.equals(name)) {
				return taskState.EnName;
			}
		}
		return null;
	}
}
