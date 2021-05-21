package com.stefanini.taskmanager.service.factory;

public abstract class ServiceFactoryProvider {
    static public ServiceFactory createServiceFactory(ServiceType serviceType){
        switch (serviceType){
            case STANDARD: return new ServiceFactoryImpl();
            default: return null;
        }
    }
}
