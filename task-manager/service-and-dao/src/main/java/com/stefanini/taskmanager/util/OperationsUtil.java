package com.stefanini.taskmanager.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/** Class containing common operations performed on objects. */
public abstract class OperationsUtil { //todo rename
  public static void copyObjectProperties(Object obj1, Object obj2) {
    try {
      BeanUtils.copyProperties(obj1, obj2);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace(); //todo logging
    }
  }
}
