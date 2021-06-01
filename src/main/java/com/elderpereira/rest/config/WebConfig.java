package com.elderpereira.rest.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.elderpereira.rest.serialization.converter.YamlJackson2HttpMessage;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	private final static MediaType APPLICATION_YAML = MediaType.valueOf("application/x-yaml");
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		// VIA EXTENCION (http://localhost:8080/persons.json ou .xml)
//		configurer.
//	    favorParameter(false).	   
//	    ignoreAcceptHeader(true).
//	    useRegisteredExtensionsOnly(true).
//	    defaultContentType(MediaType.APPLICATION_JSON).
//	    mediaType("xml", MediaType.APPLICATION_XML). 
//	    mediaType("json", MediaType.APPLICATION_JSON); 
		
		// VIA QUERY PARAM (http://localhost:8080/persons?mediaType=json ou xml)
//		configurer.
//	    favorParameter(true).
//	    parameterName("mediaType").
//	    ignoreAcceptHeader(true).
//	    useRegisteredExtensionsOnly(false).
//	    defaultContentType(MediaType.APPLICATION_JSON).
//	    mediaType("xml", MediaType.APPLICATION_XML). 
//	    mediaType("json", MediaType.APPLICATION_JSON); 
		
		// VIA HEADER (Accept = application/json)
		configurer.
	    favorParameter(true).
	    parameterName("mediaType").
	    ignoreAcceptHeader(false).
	    useRegisteredExtensionsOnly(true).
	    defaultContentType(MediaType.APPLICATION_JSON).
	    mediaType("xml", MediaType.APPLICATION_XML). 
	    mediaType("json", MediaType.APPLICATION_JSON).
		mediaType("x-yaml", APPLICATION_YAML); 
		
	}
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMessage());
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOrigins("http://localhost")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
	}
	
}
