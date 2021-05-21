package com.stefanini.taskmanager.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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

    public Task(Long id, String taskTitle, String description) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDescription = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
