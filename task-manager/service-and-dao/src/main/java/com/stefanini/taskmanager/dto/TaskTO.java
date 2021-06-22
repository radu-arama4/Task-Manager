package com.stefanini.taskmanager.dto;

import com.stefanini.taskmanager.service.proxy.email.Email;
import com.stefanini.taskmanager.service.proxy.email.EmailField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Email(emailMessage = "Task {taskTitle} {taskDescription} has been assigned to user.")
public class TaskTO {
  private Long id;

  @EmailField(fieldName = "taskTitle")
  private String taskTitle;

  @EmailField(fieldName = "taskDescription")
  private String taskDescription;

  public TaskTO(String taskTitle, String description) {
    this.taskTitle = taskTitle;
    this.taskDescription = description;
  }
}
