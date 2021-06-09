package com.stefanini.taskmanager.service.proxy.transaction;

import com.stefanini.taskmanager.persistence.util.context.ContextProvider;
import com.stefanini.taskmanager.persistence.util.context.TransactionContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    TransactionContext transactionContext = ContextProvider.createTransactionContext();
    if (transactionContext == null) {
      throw new NotSupportedTypeException("Not supported type!");
    }
    try {
      Class<?> clazz = m.getDeclaringClass();
      if (clazz.isAnnotationPresent(Transactional.class)) {
        transactionContext.begin();
        logger.info("Entered proxy");
        result = m.invoke(obj, args);
        transactionContext.commit();
      } else {
        result = m.invoke(obj, args);
      }
    } catch (InvocationTargetException e) {
      transactionContext.rollback();
      throw e.getTargetException();
    } catch (Exception e) {
      transactionContext.rollback();
      throw new RuntimeException("Unexpected invocation exception: " + e.getMessage());
    }
    return result;
  }

  public static class NotSupportedTypeException extends Exception{
    public NotSupportedTypeException(String errorMessage) {
      super(errorMessage);
    }
  }
}
