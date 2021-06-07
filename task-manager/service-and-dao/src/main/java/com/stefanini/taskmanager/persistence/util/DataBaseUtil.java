package com.stefanini.taskmanager.persistence.util;

import com.stefanini.taskmanager.persistence.dao.factory.DaoType;
import com.stefanini.taskmanager.util.ApplicationProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class which provides methods for connecting(also disconnecting) to a database depending on the
 * properties extracted from the config.properties.
 */
// TODO another database util
public class DataBaseUtil {
  static ApplicationProperties props = ApplicationProperties.getInstance();
  private static final Logger logger = LogManager.getLogger(DataBaseUtil.class);

  private static final String url = props.getUrl();
  private static final String user = props.getUser();
  private static final String password = props.getPassword();
  private static final DaoType daoType = ApplicationProperties.getInstance().getDaoType();

  private static Transaction transaction = null;
  private static Connection connection = null;
  private static Session session = null;

  public static void connectToDataBase() {
    switch (daoType) {
      case JDBC:
        {
          try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            logger.info("Connected to DataBase " + url);
          } catch (SQLException e) {
            logger.error(e);
          }
        }
      case HIBERNATE:
        {
          Configuration configuration =
              ApplicationProperties.getInstance().setHibernateProperties();
          session = configuration.buildSessionFactory().openSession();
          transaction = session.beginTransaction();
        }
    }
  }

  public static void commitTransaction() {
    switch (daoType) {
      case JDBC:
        {
          try {
            connection.commit();
          } catch (SQLException e) {
            logger.error(e);
          }
        }
      case HIBERNATE:
        {
          if (transaction.getStatus().equals(TransactionStatus.ACTIVE)) {
            transaction.commit();
          }
        }
    }
  }

  public static Connection getConnection() {
    return connection;
  }

  public static Session getSession() {
    return session;
  }

  public static void safeRollback() {
    if (transaction != null) {
      transaction.rollback();
    }
  }

  // Method for connecting to the database
  public static Connection connectToDb() {
    try {
      // TODO singletone
      connection = DriverManager.getConnection(url, user, password);
      logger.info("Connected to DataBase " + url);
      return connection;
    } catch (SQLException e) {
      logger.error(e);
      return null;
    }
  }

  public static Session connectToHibernate() {
    if (session == null) {
      Configuration configuration = ApplicationProperties.getInstance().setHibernateProperties();
      session = configuration.buildSessionFactory().openSession();
    }
    return session;
  }

  // Method for disconnecting from the database
  public static boolean disconnectFromDb() {
    try {
      connection.close();
      logger.info("Disconnected from DataBase " + url);
      return true;
    } catch (SQLException e) {
      logger.error(e);
      return false;
    }
  }
}
