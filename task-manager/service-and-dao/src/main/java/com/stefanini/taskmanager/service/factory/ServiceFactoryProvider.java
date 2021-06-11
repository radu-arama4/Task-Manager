package com.stefanini.taskmanager.service.factory;

import com.stefanini.taskmanager.service.exceptions.UnsupportedServiceTypeException;
import com.stefanini.taskmanager.util.ApplicationProperties;

/**
 * Abstract class containing static method for generating a {@link ServiceFactory} depending on the
 * given input.
 */
public abstract class ServiceFactoryProvider {
  private static final ServiceType serviceType =
      ApplicationProperties.getInstance().getServiceType();

  public static ServiceFactory createServiceFactory() {
    switch (serviceType) {
      case STANDARD:
        return new ServiceFactoryImpl(); //@Transactional forces us to use proxy
      case PROXY:
        return new ServiceFactoryImplProxy();
      default:
        throw new UnsupportedServiceTypeException();
    }
  }
}
