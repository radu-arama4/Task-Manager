package com.stefanini.taskmanager.service.proxy.email;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AnnotatedEmailProxy { // todo rename
  @After("execution (public * com.stefanini.taskmanager.service.impl.UserServiceImpl.getAllUsers(..))")
  public void sendEmail(JoinPoint joinPoint) {
    System.out.println("TEST");
  }
}
