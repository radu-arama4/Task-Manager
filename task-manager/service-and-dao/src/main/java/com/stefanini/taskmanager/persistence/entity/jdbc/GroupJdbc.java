package com.stefanini.taskmanager.persistence.entity.jdbc;

import com.stefanini.taskmanager.persistence.entity.Group;
import com.stefanini.taskmanager.service.proxy.email.Email;
import com.stefanini.taskmanager.service.proxy.email.EmailField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Email(emailMessage = "Group with name {groupName} created")
public class GroupJdbc implements Group {
  private Long groupId;

  @EmailField(fieldName = "groupName")
  private String groupName;

  public GroupJdbc(String groupName) {
    this.groupName = groupName;
  }
}
