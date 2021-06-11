package com.stefanini.taskmanager.service.proxy.email;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class EmailProxy2 { // todo rename
  @After("exe")
  public void sendEmail(JoinPoint joinPoint) {
    System.out.println("TEST");
  }
}
