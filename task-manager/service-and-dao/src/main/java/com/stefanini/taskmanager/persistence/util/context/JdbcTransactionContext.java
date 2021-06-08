package com.stefanini.taskmanager.persistence.util.context;

import com.stefanini.taskmanager.persistence.util.DataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTransactionContext implements TransactionContext {
  private final Connection connection = DataBaseUtil.connectToDb();

  @Override
  public void begin() {
    try {
      if (connection != null) {
        connection.setAutoCommit(false);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void commit() {
    try {
      if (connection != null) {
        connection.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void rollback() {
    try {
      if (connection != null) {
        connection.rollback();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
