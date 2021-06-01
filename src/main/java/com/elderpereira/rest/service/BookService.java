package com.elderpereira.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elderpereira.rest.converter.DozerConverter;
import com.elderpereira.rest.data.domain.Book;
import com.elderpereira.rest.data.vo.BookVO;
import com.elderpereira.rest.exception.BadRequestException;
import com.elderpereira.rest.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	
	public List<BookVO> findAll(){	
		return DozerConverter.parseListObjects(bookRepository.findAll(), BookVO.class);
	}
	
	public BookVO findById(long id){
		Book book = bookRepository.findById(id).orElseThrow(() -> new BadRequestException("Book Not Found"));
		return DozerConverter.parseObject(book, BookVO.class);
	}
	
	public void delete(long id) {
		bookRepository.delete(DozerConverter.parseObject(findById(id), Book.class));
	}
	
	public BookVO save(BookVO bookVO) {
		Book book = bookRepository.save(DozerConverter.parseObject(bookVO, Book.class));
		return DozerConverter.parseObject(book, BookVO.class);
	}
	
	public BookVO update(BookVO bookVO) {
		BookVO updateBook = findById(bookVO.getId());
		updateBook(updateBook, bookVO);
		Book book = bookRepository.save(DozerConverter.parseObject(updateBook, Book.class));
		return DozerConverter.parseObject(book, BookVO.class);
	}

	private void updateBook(BookVO updateBook, BookVO book) {
		updateBook.setAuthor(book.getAuthor());
		updateBook.setLaunch_date(book.getLaunch_date());
		updateBook.setPrice(book.getPrice());
		updateBook.setTitle(book.getTitle());	
	}
}
