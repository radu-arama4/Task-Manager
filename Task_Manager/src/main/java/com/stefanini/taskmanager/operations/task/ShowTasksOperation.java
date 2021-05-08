package com.stefanini.taskmanager.operations.task;

import com.stefanini.taskmanager.Operation;
import com.stefanini.taskmanager.receivers.TaskReceiver;

public class ShowTasksOperation implements Operation{

	TaskReceiver task;
	
	public ShowTasksOperation(TaskReceiver task) {
		this.task = task;
	}

	@Override
	public void execute() {
		task.showTasksOfUser();
	}	
}
