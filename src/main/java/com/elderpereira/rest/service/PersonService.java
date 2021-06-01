package com.elderpereira.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elderpereira.rest.converter.DozerConverter;
import com.elderpereira.rest.data.domain.Person;
import com.elderpereira.rest.data.vo.PersonVO;
import com.elderpereira.rest.exception.BadRequestException;
import com.elderpereira.rest.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	
	public List<PersonVO> findAll(){	
		return DozerConverter.parseListObjects(personRepository.findAll(), PersonVO.class);
	}
	
	public List<PersonVO> findAll(Pageable pageable){	
		return DozerConverter.parseListObjects(personRepository.findAll(pageable).getContent(), PersonVO.class);
	}
	
	public Page<PersonVO> findAllPage(Pageable pageable){	
		Page<Person> page = personRepository.findAll(pageable);
		return page.map((person) -> personToPersonVO(person));
	}
	
	public Page<PersonVO> findPersonByName(String firstName, Pageable pageable){	
		Page<Person> page = personRepository.findPersonByName(firstName, pageable);
		return page.map((person) -> personToPersonVO(person));
	}
	
	private PersonVO personToPersonVO(Person person) {
		return DozerConverter.parseObject(person, PersonVO.class);
	}
	
//	public Page<PersonVO> findAll2(Pageable pageable){	
//		PageImpl<PersonVO> personPage = new PageImpl<>(DozerConverter.parseListObjects(personRepository.findAll(pageable).getContent(), PersonVO.class));
//		return personPage;
//	}
	
	
	public PersonVO findById(long id){
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("Person Not Found"));
		return DozerConverter.parseObject(person, PersonVO.class);
	}
	
	public void delete(long id) {
		personRepository.delete(DozerConverter.parseObject(findById(id), Person.class));
	}
	
	public PersonVO save(PersonVO personVO) {
		Person person = personRepository.save(DozerConverter.parseObject(personVO, Person.class));
		return DozerConverter.parseObject(person, PersonVO.class);
	}
	
	public PersonVO update(PersonVO personVO) {
		PersonVO updatePerson = findById(personVO.getId());
		updatePerson(updatePerson, personVO);
		Person person = personRepository.save(DozerConverter.parseObject(updatePerson, Person.class));
		return DozerConverter.parseObject(person, PersonVO.class);
	}
	
	@Transactional
	public PersonVO disablePerson(long id){
		
		personRepository.disablePerson(id);
		
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("Person Not Found"));
		return DozerConverter.parseObject(person, PersonVO.class);
	}

	private void updatePerson(PersonVO updatePerson, PersonVO person) {
		updatePerson.setFirstName(person.getFirstName());
		updatePerson.setLastName(person.getLastName());
		updatePerson.setEmail(person.getEmail());
		updatePerson.setGrander(person.getGrander());	
	}

	
}
