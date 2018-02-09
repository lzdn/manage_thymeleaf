package com.lzdn.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.lzdn.manage.conf.WebProperties;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableConfigurationProperties(WebProperties.class)
//@EnableAutoConfiguration
@ComponentScan(value = "com.lzdn.manage")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
