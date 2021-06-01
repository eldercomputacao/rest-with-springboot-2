package com.elderpereira.rest.serialization.converter;



import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public final class YamlJackson2HttpMessage extends AbstractJackson2HttpMessageConverter {

	public YamlJackson2HttpMessage() {
		super(new YAMLMapper(), MediaType.parseMediaType("application/x-yaml"));
	}
	
	//.setSerializationInclusion(JsonInclude.Include.NON_NULL)

}
