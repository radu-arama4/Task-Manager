package com.stefanini.taskmanager.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Properties {

  private String url;
  private String user;
  private String password;

  private static Logger logger = LogManager.getLogger(Properties.class);

  public Properties() {



  }

  public String getUrl() {
    return url;
  }

  public String getUser() {
    return user;
  }

  public String getPassword() {
    return password;
  }

}
