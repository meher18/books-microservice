package com.epam.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.dto.BookDto;
import com.epam.entity.Book;
import com.epam.exceptions.BookAlreadyPresent;
import com.epam.exceptions.BookNotFoundException;
import com.epam.repository.BookRepository;
import com.epam.util.Constants;

@Component
public class BookService {

	@Autowired
	BookRepository repository;

	@Autowired
	ModelMapper mapper;

	public List<BookDto> getBooks() {
		return mapper.map(repository.findAll(), new TypeToken<List<BookDto>>() {
		}.getType());
	}

	public BookDto getBook(int bookId) {
		Book book = getBookFromRepo(bookId);
		return mapper.map(book, BookDto.class);
	}

	public BookDto addBook(BookDto bookDto) {
		if (repository.existsById(bookDto.getId())) {
			throw new BookAlreadyPresent(Constants.BOOK_ALREADY_PRESENT);
		} else {
			Book bookToBeSaved = mapper.map(bookDto, Book.class);
			return mapper.map(repository.save(bookToBeSaved), BookDto.class);
		}

	}

	public BookDto updateBook(int bookId, BookDto bookDto) {
		Book bookToBeUpdated = getBookFromRepo(bookId);
		bookToBeUpdated.setName(bookDto.getName());
		bookToBeUpdated.setPublisher(bookDto.getPublisher());
		bookToBeUpdated.setAuthor(bookDto.getAuthor());
		return mapper.map(repository.save(bookToBeUpdated), BookDto.class);

	}

	public void delete(int bookId) {
		
		// to check if the book is present
		getBookFromRepo(bookId);
		repository.deleteById(bookId);

	}

	public Book getBookFromRepo(int bookId) {
		return repository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException(Constants.INVALID_BOOK_ID + bookId));
	}
}
