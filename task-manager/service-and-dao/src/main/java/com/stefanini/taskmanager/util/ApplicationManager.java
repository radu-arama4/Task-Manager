package com.stefanini.taskmanager.util;


import com.stefanini.taskmanager.server.ProgrammaticTomcat;
import com.stefanini.taskmanager.util.context.ApplicationContextManager;
import org.apache.catalina.LifecycleException;
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
    ApplicationContextManager.createApplicationContext();

    ProgrammaticTomcat tomcat = new ProgrammaticTomcat();
    try {
      tomcat.startTomcat();
    } catch (LifecycleException e) {
      logger.error(e);
    }

    logger.info("-------------APPLICATION INITIALIZED!-------------");
  }

  /** Method for terminating the app. */
  public static void closeApp() {
    ApplicationContextManager.closeApplicationContext();
    logger.info("-------------APPLICATION CLOSED!-------------");
  }
}