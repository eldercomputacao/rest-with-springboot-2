package com.elderpereira.rest.data.vo;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter 
@Setter
//@EqualsAndHashCode
//@JsonPropertyOrder({"id", "email", "firstName", "lastName", "grander"})
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//@Mapping("id")
	@Schema(description = "Permite passar um ID da pessoa", 
			nullable = false, 
			deprecated = false, 
			required = true, 
			type = "Meu Long",
			example = "1")
	private Long id;
	//@JsonProperty("first_name")
	@Schema(description = "Permite passar o primeiro nome da pessoa", 
			nullable = true, 
			deprecated = false, 
			required = true, 
			requiredProperties = {"pro1", "pro2"},
			example = "Elder")
	private String firstName;
	//@JsonProperty("last_name")
	private String lastName;
	private String grander;
	private String email;
	//@JsonIgnore
	private String address;
	private Boolean enable;

	
}
