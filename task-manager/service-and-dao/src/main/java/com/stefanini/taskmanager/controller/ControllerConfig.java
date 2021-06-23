package com.stefanini.taskmanager.controller;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ControllerConfig implements WebApplicationInitializer {
    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext root =
                new AnnotationConfigWebApplicationContext();
        root.register(WebConfig.class);

        root.refresh();
        root.setServletContext(sc);

        sc.addListener(new ContextLoaderListener(root));

        DispatcherServlet dv =
                new DispatcherServlet(new GenericWebApplicationContext());

        ServletRegistration.Dynamic appServlet = sc.addServlet("test-mvc", dv);
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/test/*");
    }
}
