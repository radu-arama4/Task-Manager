package com.stefanini.taskmanager.service.proxy.email;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO documentation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Email {
  String emailMessage() default "Default body.";
}
