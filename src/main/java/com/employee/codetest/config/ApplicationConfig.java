package com.employee.codetest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import jakarta.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

	@Autowired
	private Environment env;

	private static Logger _logger = LoggerFactory.getLogger(ApplicationConfig.class);

	public static String DB_FILEPATH;
	
	@PostConstruct
	private void init(){
		_logger.info("db filepath to save employees: {}", env.getProperty("db.filepath"));
		DB_FILEPATH = env.getProperty("db.filepath");
	}
}
