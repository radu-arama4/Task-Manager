package com.stefanini.taskmanager.service;

public interface GroupService {

  boolean createGroup(String[] arguments);

  boolean addUserToGroup(String[] arguments);

  boolean addTaskToGroup(String[] arguments);

}
