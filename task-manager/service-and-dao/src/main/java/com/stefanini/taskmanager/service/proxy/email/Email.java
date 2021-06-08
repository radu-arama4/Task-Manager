package com.stefanini.taskmanager.service.proxy.email;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation used for determining the class which can generate emails. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Email {
  /** The template for the message to be sent. */
  String emailMessage() default "Default body.";
}
