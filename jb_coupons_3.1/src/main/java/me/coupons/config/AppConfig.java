package me.coupons.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
	  
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/**")
//	           .addResourceLocations("classpath:/static/")
//	           .resourceChain(true)
//	           .addResolver(new PathResourceResolver() {
//	               @Override
//	               protected Resource getResource(String resourcePath, Resource location) throws IOException {
//	                   Resource requestedResource = location.createRelative(resourcePath);
//	                   return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
//	                           : new ClassPathResource("/static/index.html");
//	               }
//	           });
//	}
}
