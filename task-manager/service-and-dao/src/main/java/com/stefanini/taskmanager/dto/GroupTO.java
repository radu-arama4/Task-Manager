package com.stefanini.taskmanager.dto;

import com.stefanini.taskmanager.util.email.EmailField;
import com.stefanini.taskmanager.util.email.Email;

@Email(emailMessage = "Group with name {groupName} created")
public class GroupTO {
  private Long id;
  @EmailField(fieldName = "groupName")
  private String groupName;

  public GroupTO() {
  }

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
