package me.coupons.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableWebMvc
@ComponentScan("me.coupons")
@EnableSwagger2
public class AppConfig implements WebMvcConfigurer{
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }	
	
	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				// paths based configuration - URL paths whose API to be exposed
				.paths(PathSelectors.ant("/api/**"))			
				// package based configuration - base package "me.coupons"
				.apis(RequestHandlerSelectors.basePackage("me.coupons"))
				.build()
				.apiInfo(apiDetails());
	}
	
	private ApiInfo apiDetails() {
		return new ApiInfo(
				"Coupons management system API",
				"API for coupons management system project for John Bryce training",
				"1.0",
				"Free to use",
				new springfox.documentation.service.Contact("Eugeny Korobka", "https://...", "kashey801@gmail.com"),
				"API License",
				"http://coupons.cool-app.co",
				Collections.emptyList());
	}
}
