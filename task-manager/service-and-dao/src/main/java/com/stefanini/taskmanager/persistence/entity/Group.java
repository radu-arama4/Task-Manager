package com.stefanini.taskmanager.persistence.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String groupName = null;
    @ManyToMany(mappedBy = "group")
    private List<Task> tasks = new ArrayList<>();
    @ManyToMany
    private List<User> users = new ArrayList<>();

    public Group() {
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
