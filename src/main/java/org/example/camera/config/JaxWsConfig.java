package org.example.camera.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.boot.web.servlet.ServletContextInitializer;
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
                } else {
                    servletContext.log("ERROR: sun-jaxws.xml not found! SOAP services will not work!");
                    servletContext.log("Tried path: " + configPath);
                }
            }
        };
    }

}

