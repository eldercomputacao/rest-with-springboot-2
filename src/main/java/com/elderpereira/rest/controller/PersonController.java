package com.elderpereira.rest.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elderpereira.rest.data.vo.PersonVO;
import com.elderpereira.rest.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


//@CrossOrigin(origins = "http://localhost")
@Tag(name = "PersonsEndpoints", description = "Todos os endpoints de persons")
@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonService personService;
	

	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	@Operation(summary = "Permite fazer o GET das pessoas", 
			   description = "O default de returna 10 pessoas", 
			   tags = {"Métodos GET"})
	public ResponseEntity<List<PersonVO>> findAll() {
		
		List<PersonVO> personsVS = personService.findAll();
		
		// HATEOAS
		//personsVS.forEach((p) -> p.add(linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel()));
		personsVS.forEach((p) -> p.add(linkTo(PersonController.class).slash(p.getId()).withSelfRel()));
		// HATEOAS - fim
		
		return new ResponseEntity<>(personsVS, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pageable", produces = { "application/json", "application/xml", "application/x-yaml" })
	@Operation(summary = "Permite fazer o GET das pessoas", 
			   description = "O default de returna 10 pessoas", 
			   tags = {"Métodos GET"})
	public ResponseEntity<List<PersonVO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page, 
			@RequestParam(value = "limit", defaultValue = "12") int limit, 
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		
		Direction directionDescAsc = direction.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(directionDescAsc, "firstName"));
		
		List<PersonVO> personsVS = personService.findAll(pageable);
		
		// HATEOAS
		//personsVS.forEach((p) -> p.add(linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel()));
		personsVS.forEach((p) -> p.add(linkTo(PersonController.class).slash(p.getId()).withSelfRel()));
		// HATEOAS - fim
		
		return new ResponseEntity<>(personsVS, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findPersonByName/{firstName}", produces = { "application/json", "application/xml", "application/x-yaml" })
	@Operation(summary = "Permite fazer o GET das pessoas", 
			   description = "O default de returna 10 pessoas", 
			   tags = {"Métodos GET"})
	public ResponseEntity<Page<PersonVO>> findPersonByName(
			@PathVariable(name = "firstName") String firstName,
			@RequestParam(name = "page", defaultValue = "0") int page, 
			@RequestParam(name = "limit", defaultValue = "12") int limit, 
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		
		
		Direction directionDescAsc = direction.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(directionDescAsc, "firstName"));
		
		Page<PersonVO> pagePersonsVS = personService.findPersonByName(firstName, pageable);
		
		// HATEOAS
		//personsVS.forEach((p) -> p.add(linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel()));
		pagePersonsVS.forEach((p) -> p.add(linkTo(PersonController.class).slash(p.getId()).withSelfRel()));
		// HATEOAS - fim
		
		return new ResponseEntity<>(pagePersonsVS, HttpStatus.OK);
	}
	
	
	
	// APPLICATION_JSON
	// @SuppressWarnings("deprecation")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	@Operation(summary = "Permite fazer o GET de uma pessoa", 
				tags = {"Métodos GET"})	
	public ResponseEntity<PersonVO> findById(@PathVariable long id) {

		PersonVO personVO = personService.findById(id);

		// HATEOAS
		// Link link = linkTo(methodOn(PersonController.class).findById(id)).withSelfRel();
		Link link = linkTo(PersonController.class).slash(personVO.getId()).withSelfRel();
		personVO.add(link);
		// HATEOAS - fim

		return new ResponseEntity<>(personVO, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Quando deletado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Quando NÃO deletado com sucesso")
	})
	public ResponseEntity<Void> delete(@PathVariable int id) {
		personService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<PersonVO> save(@RequestBody PersonVO person) {

		PersonVO personVO = personService.save(person);

		// HATEOAS
		//Link link = linkTo(methodOn(PersonController.class).findById(personVO.getId())).withSelfRel();
		Link link = linkTo(PersonController.class).slash(personVO.getId()).withSelfRel();
		personVO.add(link);
		// HATEOAS - fim

		return new ResponseEntity<>(personVO, HttpStatus.CREATED);
	}

	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<PersonVO> update(@RequestBody PersonVO person) {

		PersonVO personVO = personService.update(person);
		
		// HATEOAS - inicio
		//Link link = linkTo(methodOn(PersonController.class).findById(personVO.getId())).withSelfRel();
		Link link = linkTo(PersonController.class).slash(personVO.getId()).withSelfRel();
		personVO.add(link);
		// HATEOAS - fim
		
		return new ResponseEntity<>(personVO, HttpStatus.OK);
	}
	
	@PatchMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	@Operation(summary = "Permite desabilitar uma pessoa", 
				tags = {"Métodos GET"})	
	public ResponseEntity<PersonVO> disablePerson(@PathVariable long id) {

		PersonVO personVO = personService.disablePerson(id);

		// HATEOAS
		// Link link = linkTo(methodOn(PersonController.class).findById(id)).withSelfRel();
		Link link = linkTo(PersonController.class).slash(personVO.getId()).withSelfRel();
		personVO.add(link);
		// HATEOAS - fim

		return new ResponseEntity<>(personVO, HttpStatus.OK);
	}

}
