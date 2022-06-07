package com.epam.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.BookDto;
import com.epam.service.BookService;

@RestController
public class BookRestController {

	@Autowired
	BookService bookService;

	@GetMapping("/books")
	public ResponseEntity<List<BookDto>> books() {
		return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
	}

	@GetMapping("/books/{book_id}")
	public ResponseEntity<BookDto> oneBook(@PathVariable(value = "book_id") int bookId) {
		return new ResponseEntity<>(bookService.getBook(bookId), HttpStatus.OK);
	}

	@PostMapping("/books")
	public ResponseEntity<BookDto> newBook(@RequestBody @Valid BookDto bookDto) {
		return new ResponseEntity<>(bookService.addBook(bookDto), HttpStatus.CREATED);
	}

	@PutMapping("/books/{book_id}")
	public ResponseEntity<BookDto> updateBook(@PathVariable(value = "book_id") int bookId,
			@RequestBody @Valid BookDto bookDto) {
		return new ResponseEntity<>(bookService.updateBook(bookId, bookDto), HttpStatus.CREATED);
	}

	@DeleteMapping("/books/{book_id}")
	public ResponseEntity<String> delete(@PathVariable(value = "book_id") int bookId) {
		bookService.delete(bookId);
		return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
	}
}
