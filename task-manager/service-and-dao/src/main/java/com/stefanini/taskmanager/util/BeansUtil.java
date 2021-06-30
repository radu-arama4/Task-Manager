package com.stefanini.taskmanager.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

/** Class containing common operations performed on objects. */
public abstract class BeansUtil {
  private static final Logger logger = LogManager.getLogger(BeansUtil.class);

  public static void copyObjectProperties(Object obj1, Object obj2) {
    try {
      BeanUtils.copyProperties(obj1, obj2);
    } catch (IllegalAccessException | InvocationTargetException e) {
      logger.error(e);
    }
  }
}
