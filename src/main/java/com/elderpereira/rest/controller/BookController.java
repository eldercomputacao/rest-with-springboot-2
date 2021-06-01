package com.elderpereira.rest.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elderpereira.rest.data.vo.BookVO;
import com.elderpereira.rest.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


//@CrossOrigin(origins = "http://localhost")
@Tag(name = "BooksEndpoints", description = "Todos os endpoints de books")
@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<List<BookVO>> findAll() {
		
		List<BookVO> booksVS = bookService.findAll();
		
		// HATEOAS
		//booksVS.forEach((p) -> p.add(linkTo(methodOn(BookController.class).findById(p.getId())).withSelfRel()));
		booksVS.forEach((p) -> p.add(linkTo(BookController.class).slash(p.getId()).withSelfRel()));
		// HATEOAS - fim
		
		return new ResponseEntity<>(booksVS, HttpStatus.OK);
	}

	// APPLICATION_JSON
	// @SuppressWarnings("deprecation")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	@Operation(summary = "Permite fazer o GET de uma pessoa", 
				tags = {"Métodos GET"})	
	public ResponseEntity<BookVO> findById(@PathVariable long id) {

		BookVO bookVO = bookService.findById(id);

		// HATEOAS
		// Link link = linkTo(methodOn(BookController.class).findById(id)).withSelfRel();
		Link link = linkTo(BookController.class).slash(bookVO.getId()).withSelfRel();
		bookVO.add(link);
		// HATEOAS - fim

		return new ResponseEntity<>(bookVO, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Quando deletado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Quando NÃO deletado com sucesso")
	})
	public ResponseEntity<Void> delete(@PathVariable int id) {
		bookService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<BookVO> save(@RequestBody BookVO book) {

		BookVO bookVO = bookService.save(book);

		// HATEOAS
		//Link link = linkTo(methodOn(BookController.class).findById(bookVO.getId())).withSelfRel();
		Link link = linkTo(BookController.class).slash(bookVO.getId()).withSelfRel();
		bookVO.add(link);
		// HATEOAS - fim

		return new ResponseEntity<>(bookVO, HttpStatus.CREATED);
	}

	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<BookVO> update(@RequestBody BookVO book) {

		BookVO bookVO = bookService.update(book);
		
		// HATEOAS - inicio
		//Link link = linkTo(methodOn(BookController.class).findById(bookVO.getId())).withSelfRel();
		Link link = linkTo(BookController.class).slash(bookVO.getId()).withSelfRel();
		bookVO.add(link);
		// HATEOAS - fim
		
		return new ResponseEntity<>(bookVO, HttpStatus.OK);
	}

}
