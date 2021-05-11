package com.stefanini.taskmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadPropertyFile {

  private String user = null;
  private String password = null;
  private String url = null;

  private static Logger logger = LogManager.getLogger(ReadPropertyFile.class);

  public ReadPropertyFile() {
    Properties prop = new Properties();
    InputStream ip = null;
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      File file = new File(classLoader.getResource("config.properties").getFile());
      ip = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      logger.error(e.getMessage());
    }

    try {
      prop.load(ip);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }

    user = prop.getProperty("user");
    password = prop.getProperty("password");
    url = prop.getProperty("url");

    logger.info("Properties extracted successfully");

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
