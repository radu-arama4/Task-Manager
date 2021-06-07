package com.stefanini.taskmanager.service.proxy.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.*;

public class EmailProxy implements InvocationHandler {
  private static final Logger logger = LogManager.getLogger(EmailProxy.class);
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

    Class<?> objectClass = object.getClass();

    String message = objectClass.getAnnotation(Email.class).emailMessage();
    Field[] fields = objectClass.getDeclaredFields();

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
        // String.valueOf()
      } catch (IllegalAccessException e) {
        logger.error(e);
      } finally {
        field.setAccessible(false);
      }
    }
    logger.info("Sent email: " + message);
  }

  public static Object newInstance(Object obj) {
    return Proxy.newProxyInstance(
        obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new EmailProxy(obj));
  }

  private EmailProxy(Object obj) {
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
