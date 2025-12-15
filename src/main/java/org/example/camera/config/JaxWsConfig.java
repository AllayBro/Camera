package org.example.camera.config;

import com.sun.xml.ws.transport.http.servlet.WSServlet;
import com.sun.xml.ws.transport.http.servlet.WSServletContextListener;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletException;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Configuration
public class JaxWsConfig {

    @Bean
    public ServletContextInitializer jaxWsInitializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                // Пробуем найти sun-jaxws.xml
                String configPath = servletContext.getRealPath("/WEB-INF/sun-jaxws.xml");
                
                if (configPath == null || !new File(configPath).exists()) {
                    // В Spring Boot embedded Tomcat getRealPath может вернуть null
                    // Копируем файл из classpath во временную директорию
                    try {
                        InputStream is = Thread.currentThread().getContextClassLoader()
                                .getResourceAsStream("sun-jaxws.xml");
                        if (is != null) {
                            File tempDir = new File(System.getProperty("java.io.tmpdir"));
                            File tempFile = new File(tempDir, "sun-jaxws.xml");
                            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                                is.transferTo(fos);
                            }
                            configPath = tempFile.getAbsolutePath();
                            servletContext.log("JAX-WS config copied to: " + configPath);
                        }
                    } catch (Exception e) {
                        servletContext.log("ERROR copying sun-jaxws.xml: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                
                if (configPath != null && new File(configPath).exists()) {
                    servletContext.setAttribute("com.sun.xml.ws.config.file", configPath);
                    servletContext.log("JAX-WS config file set to: " + configPath);
                    
                    // Инициализируем WSServletContextListener вручную после установки конфига
                    try {
                        WSServletContextListener listener = new WSServletContextListener();
                        ServletContextEvent event = new ServletContextEvent(servletContext);
                        listener.contextInitialized(event);
                        servletContext.log("WSServletContextListener initialized successfully");
                    } catch (Exception e) {
                        servletContext.log("ERROR initializing WSServletContextListener: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    servletContext.log("ERROR: sun-jaxws.xml not found! SOAP services will not work!");
                    servletContext.log("Tried path: " + configPath);
                }
            }
        };
    }

    @Bean
    public ServletRegistrationBean<WSServlet> wsServlet() {
        ServletRegistrationBean<WSServlet> registration =
                new ServletRegistrationBean<>(
                        new WSServlet(),
                        "/DroneService",
                        "/FinesService",
                        "/JournalService"
                );

        registration.setLoadOnStartup(2);
        registration.setName("JAXWSServlet");

        return registration;
    }

}
