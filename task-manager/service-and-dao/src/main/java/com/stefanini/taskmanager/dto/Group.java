package com.stefanini.taskmanager.dto;

public class Group {
  private Long id;
  private String groupName = null;

  public Group(String groupName) {
    this.groupName = groupName;
  }

  public Group(Long id, String groupName) {
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
