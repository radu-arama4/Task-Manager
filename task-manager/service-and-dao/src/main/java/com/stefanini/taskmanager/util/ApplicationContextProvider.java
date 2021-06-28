package com.stefanini.taskmanager.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextProvider {
    private static ApplicationContext applicationContext;

    public static void createApplicationContext(){
        applicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
