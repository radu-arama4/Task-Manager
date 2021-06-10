package com.stefanini.taskmanager.persistence.dao.factory;

import com.stefanini.taskmanager.persistence.dao.GroupDao;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.dao.jdbc.GroupDaoJdbc;
import com.stefanini.taskmanager.persistence.dao.jdbc.TaskDaoJdbc;
import com.stefanini.taskmanager.persistence.dao.jdbc.UserDaoJdbc;
import com.stefanini.taskmanager.persistence.util.DataBaseUtil;

import java.sql.Connection;

/**
 * Implementation of {@link DaoFactory} for providing methods for producing single instances of
 * {@link UserDaoJdbc}, {@link TaskDaoJdbc}, {@link GroupDaoJdbc}.
 */
public class JdbcDaoFactory implements DaoFactory {
  private final Connection connection = DataBaseUtil.connectToDb();

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
