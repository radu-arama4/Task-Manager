package com.stefanini.taskmanager.util;

import com.stefanini.taskmanager.persistence.dao.factory.DaoType;
import com.stefanini.taskmanager.service.factory.ServiceType;
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
  private final String daoType;
  private final String serviceType;
  private final String driverClass;
  private final String dialect;
  private final String hbm2ddl;

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

    daoType = properties.getProperty("dao_type");
    serviceType = properties.getProperty("service_type");

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
        .setProperty("hibernate.connection.url", url)
        .setProperty("hibernate.connection.username", user)
        .setProperty("hibernate.connection.password", password)
        .setProperty("hibernate.hbm2ddl.auto", hbm2ddl)
        .setProperty("hibernate.dialect", dialect)
        .setProperty("hibernate.connection.driver_class", driverClass);
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

  public DaoType getDaoType() {
    return DaoType.valueOf(daoType);
  }

  public ServiceType getServiceType() {
    return ServiceType.valueOf(serviceType);
  }
}
