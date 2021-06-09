package com.stefanini.taskmanager.persistence.entity;

public interface Task {
    Long getTaskId();
    String getTaskTitle();
    String getTaskDescription();
    void setTaskId(Long taskId);
    void setTaskTitle(String taskTitle);
    void setTaskDescription(String taskDescription);
}
