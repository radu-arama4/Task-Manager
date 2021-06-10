package com.stefanini.taskmanager.dto;

import com.stefanini.taskmanager.service.proxy.email.EmailField;
import com.stefanini.taskmanager.service.proxy.email.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Email(emailMessage = "Group with name {groupName} created")
public class GroupTO {
  private Long id;
  @EmailField(fieldName = "groupName")
  private String groupName;

  public GroupTO(String groupName) {
    this.groupName = groupName;
  }
}
