package com.stefanini.taskmanager.service.factory;

import com.stefanini.taskmanager.util.ApplicationProperties;

/**
 * Abstract class containing static method for generating a {@link ServiceFactory} depending on the given
 * input.
 */
public abstract class ServiceFactoryProvider {
    private static final ServiceType serviceType = ApplicationProperties.getInstance().getServiceType();

    public static ServiceFactory createServiceFactory(){
        switch (serviceType){
            case STANDARD: return new ServiceFactoryImpl();
            case PROXY: return new ServiceFactoryImpl2(); //new proxy impl
            default: throw new ServiceTypeException("Undefined service type!");
        }
    }

    static class ServiceTypeException extends RuntimeException{
        public ServiceTypeException(String s) {
            super(s);
        }
    }
}
