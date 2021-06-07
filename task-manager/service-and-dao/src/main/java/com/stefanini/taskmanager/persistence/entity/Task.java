package com.stefanini.taskmanager.persistence.entity;

public interface Task {
    String getTaskTitle();
    String getTaskDescription();
    void setTaskTitle(String taskTitle);
    void setTaskDescription(String taskDescription);
}
