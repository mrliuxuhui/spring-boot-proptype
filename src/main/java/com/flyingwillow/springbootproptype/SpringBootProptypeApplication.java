package com.flyingwillow.springbootproptype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.flyingwillow"})
public class SpringBootProptypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProptypeApplication.class, args);
	}
}
