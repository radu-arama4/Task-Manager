package com.stefanini.taskmanager.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextProvider {
    private static ApplicationContext applicationContext;

    public static void createApplicationContext(){
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
