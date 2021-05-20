package com.stefanini.taskmanager.persistence.dao.factory;

import com.stefanini.taskmanager.persistence.dao.*;
import com.stefanini.taskmanager.persistence.dao.jdbc.GroupDaoJdbc;
import com.stefanini.taskmanager.persistence.dao.jdbc.TaskDaoJdbc;
import com.stefanini.taskmanager.persistence.dao.jdbc.UserDaoJdbc;
import com.stefanini.taskmanager.persistence.util.DButil;

import java.sql.Connection;

public class JdbcDaoFactory implements DaoFactory {
  private final Connection connection = DButil.connectToDb();

  public UserDao createUserDao() {
    return UserDaoJdbc.getInstance(connection);
  }

  public TaskDao createTaskDao() {
    return TaskDaoJdbc.getInstance(connection);
  }

  public GroupDao createGroupDao() {
    return GroupDaoJdbc.getInstance(connection);
  }
}
