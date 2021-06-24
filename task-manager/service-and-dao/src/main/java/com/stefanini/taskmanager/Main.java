package com.stefanini.taskmanager;

import com.stefanini.taskmanager.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
  public static void main(String[] args) {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

    applicationContext.getBean("ceva", UserServiceImpl.class);

  }
}
