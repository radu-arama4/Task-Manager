package com.stefanini.taskmanager.operations.task;

import com.stefanini.taskmanager.Operation;
import com.stefanini.taskmanager.receivers.TaskReceiver;

public class AddTaskOperation implements Operation{

	TaskReceiver task;
	
	public AddTaskOperation(TaskReceiver task) {
		this.task = task;
	}

	@Override
	public void execute() {
		task.addTask();
	}
}
