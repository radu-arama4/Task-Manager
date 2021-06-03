package com.stefanini.taskmanager.util.transaction;

import com.stefanini.taskmanager.persistence.util.DataBaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransactionProxy implements InvocationHandler {
  private static final Logger logger = LogManager.getLogger(TransactionProxy.class);
  private final Object obj;

  private TransactionProxy(Object obj) {
    this.obj = obj;
  }

  public static Object newInstance(Object obj) {
    return java.lang.reflect.Proxy.newProxyInstance(
        obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new TransactionProxy(obj));
  }

  public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
    Object result = null;
    try {
      Class<?> clazz = m.getClass();
      if(clazz.isAnnotationPresent(Transactional.class)){
        Session session = DataBaseUtil.connectToHibernate();
        Transaction transaction = session.beginTransaction();
        logger.info("Entered proxy");
        result = m.invoke(obj, args);
        transaction.commit();
      }
    } catch (InvocationTargetException e) {
      throw e.getTargetException();
    } catch (Exception e) {
      throw new RuntimeException("Unexpected invocation exception: " + e.getMessage());
    }
    return result;
  }
}
