package com.stefanini.taskmanager.server;

import com.stefanini.taskmanager.server.filters.UserFilter;
import com.stefanini.taskmanager.server.servlets.UserServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class ProgrammaticTomcat {
    private static boolean isFree(int port) {
        try {
            new ServerSocket(port).close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private Tomcat tomcat = null;

    private final int port;

    public ProgrammaticTomcat() {
        this.port = 7725;
    }

    public int getPort() {
        return port;
    }

    public void startTomcat() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setHostname("localhost");
        String appBase = ".";
        tomcat.getHost().setAppBase(appBase);

        File docBase = new File(System.getProperty("java.io.tmpdir"));
        Context context = tomcat.addContext("", docBase.getAbsolutePath());

        Class<UserServlet> servletClass = UserServlet.class;
        Tomcat.addServlet(context, servletClass.getSimpleName(), servletClass.getName());
        context.addServletMappingDecoded("/user/*", servletClass.getSimpleName());

        Class<UserFilter> filterClass = UserFilter.class;
        FilterDef myFilterDef = new FilterDef();
        myFilterDef.setFilterClass(filterClass.getName());
        myFilterDef.setFilterName(filterClass.getSimpleName());
        context.addFilterDef(myFilterDef);

        FilterMap myFilterMap = new FilterMap();
        myFilterMap.setFilterName(filterClass.getSimpleName());
        myFilterMap.addURLPattern("/user/*");
        context.addFilterMap(myFilterMap);

        tomcat.start();
        tomcat.getServer().await();
    }

    public void stopTomcat() throws LifecycleException {
        tomcat.stop();
        tomcat.destroy();
    }
}
