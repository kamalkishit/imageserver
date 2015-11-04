package com.humanize.imageserver;

import java.net.URL;
import java.net.URLClassLoader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;


@SpringBootApplication
public class App extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

	public static void main(String[] args) {
		ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();
        System.out.println("i m here");

        for(URL url: urls){
        	System.out.println(url.getFile());
        }
		SpringApplication.run(App.class, args);
	}
	
	public static void run(String... args) throws Exception {
		


	}
}
