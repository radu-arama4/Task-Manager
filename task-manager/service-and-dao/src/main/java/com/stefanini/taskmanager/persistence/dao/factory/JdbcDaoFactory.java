package com.stefanini.taskmanager.persistence.dao.factory;

import com.stefanini.taskmanager.persistence.dao.*;
import com.stefanini.taskmanager.persistence.util.DButil;

import java.sql.Connection;

public class JdbcDaoFactory implements DaoFactory {
  private final Connection connection = DButil.connectToDb();

  public UserDao createUserDao() {
    return UserDaoImpl.getInstance(connection);
  }

  public TaskDao createTaskDao() {
    return TaskDaoImpl.getInstance(connection);
  }

  public GroupDao createGroupDao() {
    return GroupDaoImpl.getInstance(connection);
  }
}
