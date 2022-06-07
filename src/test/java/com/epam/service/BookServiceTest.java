package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dto.BookDto;
import com.epam.entity.Book;
import com.epam.exceptions.BookNotFoundException;
import com.epam.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BookServiceTest {

	@Autowired
	BookService bookService;

	@Mock
	BookRepository repository;

	@Autowired
	ModelMapper mapper;

	@BeforeEach
	void setUp() {
		bookService.mapper = mapper;
		bookService.repository = repository;
	}

	@Test
	void getBooksTest() {

		List<Book> books = new ArrayList<>();
		books.add(new Book());
		when(repository.findAll()).thenReturn(books);
		assertEquals(1, bookService.getBooks().size());
	}

	@Test
	void getBookTest() {

		Optional<Book> book = Optional.ofNullable(new Book());
		when(repository.findById(anyInt())).thenReturn(book);
		assertNotNull(bookService.getBook(1));
	}

	@Test
	void addBookTest() {

		BookDto bookDto = new BookDto();
		Book book = new Book();
		when(repository.save(any())).thenReturn(book);
		assertNotNull(bookService.addBook(bookDto));
	}

	@Test
	void updateBookTest() {
		BookDto bookDto = new BookDto();

		Optional<Book> book = Optional.ofNullable(new Book());
		when(repository.findById(anyInt())).thenReturn(book);
		when(repository.save(any())).thenReturn(book.get());
		assertNotNull(bookService.updateBook(1, bookDto));
	}

	@Test
	void deleteBookTest() {

		when(repository.existsById(anyInt())).thenReturn(true);
		bookService.delete(1);
		verify(repository, times(1)).deleteById(anyInt());
	}

	@Test
	void deleteBookForInvalidNotIdTest() {

		when(repository.existsById(anyInt())).thenReturn(false);

		BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.delete(1),
				"Book with id 1 not found");
		assertEquals("Book with id 1 not found", exception.getMessage());
	}

	@Test
	void getBookForInvalidIdCheck() {

		when(repository.findById(anyInt())).thenThrow(new BookNotFoundException("Book with id 1 not found"));
		BookNotFoundException exception = assertThrows(BookNotFoundException.class,
				() -> bookService.getBookFromRepo(1), "Book with id 1 not found");
		assertEquals("Book with id 1 not found", exception.getMessage());
	}
}
