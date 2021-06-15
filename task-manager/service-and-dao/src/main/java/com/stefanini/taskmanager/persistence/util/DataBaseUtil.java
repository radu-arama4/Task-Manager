package com.stefanini.taskmanager.persistence.util;

import com.stefanini.taskmanager.util.ApplicationProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class which provides methods for connecting(also disconnecting) to a database depending on the
 * properties extracted from the config.properties.
 */
public class DataBaseUtil {
  private static final ApplicationProperties props = ApplicationProperties.getInstance();
  private static final Logger logger = LogManager.getLogger(DataBaseUtil.class);

  private static final String url = props.getUrl();
  private static final String user = props.getUser();
  private static final String password = props.getPassword();

  private static Connection connection = null;
  private static SessionFactory sessionFactory = null;

  public static Connection connectWithJdbc() {
    if (connection == null) {
      try {
        connection = DriverManager.getConnection(url, user, password);
        logger.info("Connected to DataBase " + url);
        return connection;
      } catch (SQLException e) {
        logger.error(e);
        return null;
      }
    }
    return connection;
  }

  public static SessionFactory connectWithHibernate() {
    if (sessionFactory == null) {
      Configuration configuration = ApplicationProperties.getInstance().getHibernateConfiguration();
      sessionFactory = configuration.buildSessionFactory();
    }
    return sessionFactory;
  }

  public static void disconnectJDBC() {
    try {
      connection.close();
      logger.info("Disconnected from DataBase " + url);
    } catch (SQLException e) {
      logger.error(e);
    }
  }

  public static void disconnectHibernate() {
//    if (sessionFactory.getCurrentSession().isOpen()) {
//      sessionFactory.getCurrentSession().close();
//    }
  }
}
