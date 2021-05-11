package com.stefanini.taskmanager.receivers;

import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.TaskServiceImpl;

public class TaskReceiver {

  private String[] args = null;
  private TaskService taskService = null;

  public TaskReceiver(String[] args) {
    super();
    this.args = args;
    taskService = new TaskServiceImpl();
  }

  public void addTask() {
    taskService.addTask(args);
  }

  public void showTasksOfUser() {
    taskService.showTasks(args);
  }

}
