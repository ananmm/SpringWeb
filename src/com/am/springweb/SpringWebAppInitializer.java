package com.am.springweb;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tiles.extras.complete.CompleteAutoloadTilesListener;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class SpringWebAppInitializer  implements WebApplicationInitializer {

	public static final Logger logger = LogManager.getLogger(SpringWebAppInitializer.class);
	
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		
		logger.info("Start of initializer");
		
		AnnotationConfigWebApplicationContext springWebAppRootContext = new AnnotationConfigWebApplicationContext();
		//register the other service classes
		//springWebAppRootContext.register(ServiceConfig.class, JPAConfig.class, SecurityConfig.class);
		
		//adding the listener and filter
		container.addListener(CompleteAutoloadTilesListener.class);
		
		// Manage the lifecycle of the root application context.
		container.addListener(new ContextLoaderListener(springWebAppRootContext));
		
		//create the dispatcher servlet's spring application context
		AnnotationConfigWebApplicationContext springWebAppDispatcherServletContext = new AnnotationConfigWebApplicationContext();
		springWebAppDispatcherServletContext.register(SpringWebAppMVCConfig.class);
		
		ServletRegistration.Dynamic servletRegistration = container.addServlet("dispatcher", new DispatcherServlet(springWebAppDispatcherServletContext)); 
		servletRegistration.setLoadOnStartup(1);
		servletRegistration.addMapping("/");

		logger.info("End of initializer");
	}
	
}
