package com.stefanini.taskmanager.persistence.util.context;

import com.stefanini.taskmanager.persistence.util.DataBaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTransactionContext implements TransactionContext {
  private final Connection connection = DataBaseUtil.connectToDb();
  private static final Logger logger = LogManager.getLogger(JdbcTransactionContext.class);

  @Override
  public void begin() {
    try {
      if (connection != null) {
        connection.setAutoCommit(false);
      }
    } catch (SQLException e) {
      logger.error(e);
    }
  }

  @Override
  public void commit() {
    try {
      if (connection != null) {
        connection.commit();
      }
    } catch (SQLException e) {
      logger.error(e);
    }
  }

  @Override
  public void rollback() {
    try {
      if (connection != null) {
        connection.rollback();
      }
    } catch (SQLException e) {
      logger.error(e);
    }
  }
}
