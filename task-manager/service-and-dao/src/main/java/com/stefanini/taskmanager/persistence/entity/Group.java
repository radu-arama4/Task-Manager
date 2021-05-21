package com.stefanini.taskmanager.persistence.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "some_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String grouupName = null;
    @ManyToMany(mappedBy = "groups")
    private List<User> users = new LinkedList<>();
    @ManyToMany(mappedBy = "group")
    private List<Task> tasks = new LinkedList<>();

    public Group(String grouupName) {
        this.grouupName = grouupName;
    }

    public Group(Long id, String grouupName) {
        this.id = id;
        this.grouupName = grouupName;
    }

    public void addTask(Task task){
        this.tasks.add(task);
        task.setGroup(this);
    }

    public void addUser(User user){
        this.users.add(user);
        user.addGroup(this);
    }

    public void setGrouupName(String grouupName) {
        this.grouupName = grouupName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrouupName() {
        return grouupName;
    }

}
