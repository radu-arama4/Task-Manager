package com.stefanini.taskmanager.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Abstract class which contains methods for calling necessary processes when starting the
 * application and when closing the application.
 */
public abstract class ApplicationManager {
  private static final Logger logger = LogManager.getLogger(ApplicationManager.class);

  /** Method for initializing the app. */
  public static void initApp() {
    ApplicationContextProvider.createApplicationContext();
    logger.info("-------------APPLICATION INITIALIZED!-------------");
  }

  /** Method for terminating the app. */
  public static void closeApp() {
    //TODO try to close spring app
    logger.info("-------------APPLICATION CLOSED!-------------");
  }
}
