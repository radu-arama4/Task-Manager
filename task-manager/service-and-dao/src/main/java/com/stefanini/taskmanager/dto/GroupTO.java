package com.stefanini.taskmanager.dto;

public class GroupTO {
  private Long id;
  private final String groupName;

  public GroupTO(String groupName) {
    this.groupName = groupName;
  }

  public GroupTO(Long id, String groupName) {
    this.id = id;
    this.groupName = groupName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getGroupName() {
    return groupName;
  }
}
