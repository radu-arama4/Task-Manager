package com.stefanini.taskmanager.util.email;

import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EmailUtil implements InvocationHandler {
  private static final org.apache.logging.log4j.Logger logger =
      LogManager.getLogger(EmailUtil.class);
  private final Object obj;

  private boolean isAnnotated(Object object) {
    if (object == null) {
      return false;
    }

    return object.getClass().isAnnotationPresent(Email.class);
  }

  private String wrap(String field) {
    return "{" + field + "}";
  }

  private void sendEmail(Object object) {
    if (!isAnnotated(object)) {
      return;
    }

    String message = object.getClass().getAnnotation(Email.class).emailMessage();
    Field[] fields = object.getClass().getDeclaredFields();

    // TODO optimize with streams

    for (Field field : fields) {
      if (!field.isAnnotationPresent(EmailField.class)) {
        continue;
      }
      field.setAccessible(true);
      try {
        message =
            message.replace(
                wrap(field.getAnnotation(EmailField.class).fieldName()),
                (String) field.get(object));
      } catch (IllegalAccessException e) {
        field.setAccessible(false);
        logger.error(e);
      }
      field.setAccessible(false);
    }
    logger.info("Sent email: " + message);
  }

  public static Object newInstance(Object obj) {
    return java.lang.reflect.Proxy.newProxyInstance(
        obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new EmailUtil(obj));
  }

  private EmailUtil(Object obj) {
    this.obj = obj;
  }

  public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
    Object result;
    try {
      logger.info("Entered proxy");
      result = m.invoke(obj, args);

      if (m.isAnnotationPresent(EmailGenerator.class)) {
        if (result.getClass().isAnnotationPresent(Email.class)) {
          sendEmail(result);
        }
      }

    } catch (InvocationTargetException e) {
      throw e.getTargetException();
    } catch (Exception e) {
      throw new RuntimeException("Unexpected invocation exception: " + e.getMessage());
    }
    return result;
  }
}
