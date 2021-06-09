package com.stefanini.taskmanager.persistence.entity.jdbc;

import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.service.proxy.email.Email;
import com.stefanini.taskmanager.service.proxy.email.EmailField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Email(emailMessage = "Task {task title} {task description} has been assigned to {username}")
public class TaskJdbc implements Task {
  private Long taskId;

  @EmailField(fieldName = "taskTitle")
  private String taskTitle;

  @EmailField(fieldName = "taskDescription")
  private String taskDescription;

  public TaskJdbc(String taskTitle, String description) {
    this.taskTitle = taskTitle;
    this.taskDescription = description;
  }
}
