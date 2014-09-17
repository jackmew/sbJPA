package org.jack.sbJpa;

import javax.servlet.MultipartConfigElement;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Hello world!
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan({"org.jack.sbJpa" , "org.jack.sbJpa.controller"})
public class App extends WebMvcConfigurerAdapter
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
    }
    // file upload
    @Bean
    public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("128KB");
        factory.setMaxRequestSize("128KB");
        return factory.createMultipartConfig();
    }
    // static resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/views/**").addResourceLocations("/WEB-INF/views/");
    }
    // h2 console
    @Bean
    public ServletRegistrationBean h2servletRegistration() {
    	WebServlet h2Servlet = new WebServlet();
        ServletRegistrationBean registration = new ServletRegistrationBean(h2Servlet);
        registration.addUrlMappings("/console/*");
        return registration;
    }

}
