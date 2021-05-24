package com.stefanini.taskmanager.persistence.dao.factory;

import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.UserDao;

/**
 * Interface which provides methods for generating {@link UserDao}, {@link TaskDao} and {@link
 * GroupDao} implementations/
 */
public interface DaoFactory {
  UserDao createUserDao();

  TaskDao createTaskDao();

  GroupDao createGroupDao();
}
