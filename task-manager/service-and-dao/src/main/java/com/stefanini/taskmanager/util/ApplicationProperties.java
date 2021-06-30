package com.stefanini.taskmanager.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class(singleton) which provides methods for extracting properties from config.properties and
 * encapsulates them.
 */
public class ApplicationProperties {
  private final String user;
  private final String password;
  private final String url;
  private final String driverClass;
  private final String dialect;
  private final String hbm2ddl;
  private final String current_session_context_class;
  private final String executor_type;

  private static final Logger logger = LogManager.getLogger(ApplicationProperties.class);

  private static ApplicationProperties instance = null;

  private ApplicationProperties() {
    Properties properties = extractProperties();

    user = properties.getProperty("connection.username");
    password = properties.getProperty("connection.password");
    url = properties.getProperty("connection.url");
    driverClass = properties.getProperty("connection.driver_class");
    dialect = properties.getProperty("dialect");
    hbm2ddl = properties.getProperty("hbm2ddl.auto");
    current_session_context_class = properties.getProperty("current_session_context_class");

    executor_type = properties.getProperty("executor_type");

    logger.debug("Properties extracted successfully");
  }

  public static ApplicationProperties getInstance() {
    if (instance == null) {
      instance = new ApplicationProperties();
    }
    return instance;
  }

  public Configuration getHibernateConfiguration() {
    Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
    return configuration
        .setProperty("hibernate.hbm2ddl.auto", hbm2ddl)
        .setProperty("hibernate.dialect", dialect);
  }

  private Properties extractProperties() {
    Properties prop = new Properties();
    InputStream ip =
        ApplicationProperties.class.getClassLoader().getResourceAsStream("config.properties");

    try {
      prop.load(ip);
    } catch (IOException e) {
      logger.error(e);
    }

    return prop;
  }

  public String getUser() {
    return user;
  }

  public String getPassword() {
    return password;
  }

  public String getUrl() {
    return url;
  }

  public String getExecutorType() {
    return executor_type;
  }

  public String getDriverClass() {
    return driverClass;
  }
}
