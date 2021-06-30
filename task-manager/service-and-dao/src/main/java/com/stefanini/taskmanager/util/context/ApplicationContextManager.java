package com.stefanini.taskmanager.util.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextManager {
  private static ApplicationContext applicationContext;

  public static void createApplicationContext() {
    applicationContext =
        new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public static void closeApplicationContext() {
    ((ConfigurableApplicationContext) applicationContext).close();
  }
}
