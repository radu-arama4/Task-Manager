package com.stefanini.taskmanager.service.factory;

public abstract class ServiceFactoryProduction {
    static public ServiceFactory createServiceFactory(String serviceCategory){
        switch (serviceCategory){
            case "standard" : return new ServiceFactoryImpl();
            default: return null;
        }
    }
}
