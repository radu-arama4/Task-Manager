package com.stefanini.taskmanager.service.factory;

public abstract class ServiceFactoryProvider {
    static public ServiceFactory createServiceFactory(ServiceType serviceType) throws Exception {
        switch (serviceType){
            case STANDARD: return new ServiceFactoryImpl();
            default: throw new Exception("Undefined service type!");
        }
    }
}
