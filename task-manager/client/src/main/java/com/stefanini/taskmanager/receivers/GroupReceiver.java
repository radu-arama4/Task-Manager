package com.stefanini.taskmanager.receivers;

import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.GroupServiceImpl;

public class GroupReceiver {

  private String[] args = null;
  private GroupService groupService = null;

  public GroupReceiver(String[] args) {
    this.args = args;
    groupService = new GroupServiceImpl();
  }

  public void addGroup() {
    groupService.createGroup(args);
  }

  public void addTaskToGroup() {
    groupService.addTaskToGroup(args);
  }

  public void addUserToGroup() {
    groupService.addUserToGroup(args);
  }

}
