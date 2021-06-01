package com.elderpereira.rest.data.vo;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter 
@Setter
//@EqualsAndHashCode(of = {"id"})
public class BookVO extends RepresentationModel<BookVO> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String author;
	private String title;
	private Double price;
	private LocalDate launch_date;
	
}
