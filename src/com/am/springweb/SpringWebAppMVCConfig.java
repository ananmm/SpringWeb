package com.am.springweb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.am.springweb"})
public class SpringWebAppMVCConfig extends WebMvcConfigurerAdapter {
	
	private static final Logger logger  = LogManager.getLogger(SpringWebAppMVCConfig.class);

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		logger.info("adding the resource handlers");
		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/static/");
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		logger.info("in the default servlet handling");
		configurer.enable();
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		logger.info("adding the tiles config");
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] {"/WEB-INF/views/tiles/tiles.xml", "/WEB-INF/views/tiles/definitions.xml"});
		return tilesConfigurer;
	}
	
	@Bean
	  public ViewResolver htmlViewResolver() {
	    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	    resolver.setPrefix("/WEB-INF/views/");
	    resolver.setSuffix(".jsp");
	    resolver.setOrder(1);
	    return resolver;
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		logger.info("configuring the view resolver");
		TilesViewResolver viewResolver = new TilesViewResolver();
		viewResolver.setOrder(0);
		registry.viewResolver(viewResolver);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		logger.info("adding the view controllers");
		//registry.addViewController("/").setViewName("springwebapp.homepage");
		//registry.addViewController("/").setViewName("redirect:/WEB-INF/views/index.html");
		registry.addViewController("/").setViewName("index");
	}
	
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getMultipartResolver() {
		logger.info("configuring the multi part resolver");
		return new CommonsMultipartResolver();
	}
	
	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource getMessageSource() {
		logger.info("setting the messageSource");
		ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("classpath:messages");
		resourceBundleMessageSource.setDefaultEncoding("UTF-8");
		return resourceBundleMessageSource;
	}
}
