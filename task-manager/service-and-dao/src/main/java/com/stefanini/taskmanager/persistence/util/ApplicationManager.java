package com.stefanini.taskmanager.persistence.util;


import com.stefanini.taskmanager.persistence.util.exceptions.UnsupportedDaoTypeException;
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
    try {
      ConnectionManager.connectToDataBase();
    } catch (UnsupportedDaoTypeException e) {
      logger.error(e);
    }
    logger.info("-------------APPLICATION INITIALIZED!-------------");
  }

  /** Method for terminating the app. */
  public static void closeApp() {
    try {
      ConnectionManager.disconnectFromDataBase();
    } catch (UnsupportedDaoTypeException e) {
      logger.error(e);
    }
    logger.info("-------------APPLICATION CLOSED!-------------");
  }
}
