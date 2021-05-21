package com.stefanini.taskmanager.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;
    @Column
    private String taskTitle;
    @Column
    private String taskDescription;
    @OneToOne
    private User user;
    @OneToOne
    private Group group;

    public Task() {
    }

    public Task(String taskTitle, String description) {
        this.taskTitle = taskTitle;
        this.taskDescription = description;
    }

    public Task(Long task_id, String taskTitle, String description) {
        this.task_id = task_id;
        this.taskTitle = taskTitle;
        this.taskDescription = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getDescription() {
        return taskDescription;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
