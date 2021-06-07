package com.stefanini.taskmanager.service.proxy.transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.stefanini.taskmanager.persistence.util.DataBaseUtil.*;
import static java.lang.reflect.Proxy.newProxyInstance;

public class TransactionProxy implements InvocationHandler {
  private static final Logger logger = LogManager.getLogger(TransactionProxy.class);
  private final Object obj;

  private TransactionProxy(Object obj) {
    this.obj = obj;
  }

  public static Object newInstance(Object obj) {
    return newProxyInstance(
        obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new TransactionProxy(obj));
  }

  public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
    Object result;
    try {
      Class<?> clazz = m.getDeclaringClass();
      if (clazz.isAnnotationPresent(Transactional.class)) {
        connectToDataBase();

        logger.info("Entered proxy");
        result = m.invoke(obj, args);

        commitTransaction();
      } else {
        result = m.invoke(obj, args);
      }
    } catch (InvocationTargetException e) {
      safeRollback();
      throw e.getTargetException();
    } catch (Exception e) {
      safeRollback();
      throw new RuntimeException("Unexpected invocation exception: " + e.getMessage());
    }
    return result;
  }
}
