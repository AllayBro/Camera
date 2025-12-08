package org.example.camera.config;

import com.sun.xml.ws.transport.http.servlet.WSServlet;
import com.sun.xml.ws.transport.http.servlet.WSServletContextListener;
import jakarta.servlet.ServletContext;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaxWsConfig {

    @Bean
    public ServletListenerRegistrationBean<WSServletContextListener> wsServletContextListener() {
        ServletListenerRegistrationBean<WSServletContextListener> registration = 
            new ServletListenerRegistrationBean<>(new WSServletContextListener());
        return registration;
    }

    @Bean
    public ServletRegistrationBean<WSServlet> wsServlet() {
        ServletRegistrationBean<WSServlet> registration = 
            new ServletRegistrationBean<>(new WSServlet(), "/DroneService");
        registration.setLoadOnStartup(2);
        registration.setName("JAXWSServlet");
        return registration;
    }
}

