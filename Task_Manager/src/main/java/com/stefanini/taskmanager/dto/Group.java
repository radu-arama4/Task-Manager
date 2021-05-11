package com.stefanini.taskmanager.dto;

public class Group {
  private String groupName = null;

  public Group(String groupName) {
    this.groupName = groupName;
  }

  public String getGroupName() {
    return groupName;
  }
}
