package com.stefanini.taskmanager.service.proxy.email;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation used for determining the fields which have to be included in the email message. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EmailField {
  String fieldName() default "";
}
