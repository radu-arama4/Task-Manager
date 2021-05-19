package com.stefanini.taskmanager.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertyFile {

  private final String user;
  private final String password;
  private final String url;

  private static final Logger logger = LogManager.getLogger(ReadPropertyFile.class);

  public ReadPropertyFile() {

    Properties properties = extractProperties();

    user = properties.getProperty("user");
    password = properties.getProperty("password");
    url = properties.getProperty("url");

    logger.debug("Properties extracted successfully");
  }

  private Properties extractProperties() {
    Properties prop = new Properties();
    InputStream ip =
        ReadPropertyFile.class.getClassLoader().getResourceAsStream("config.properties");

    try {
      prop.load(ip);
    } catch (IOException e) {
      logger.error(e.getMessage());
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
}
