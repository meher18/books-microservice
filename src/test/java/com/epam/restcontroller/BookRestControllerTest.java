package com.epam.restcontroller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.dto.BookDto;
import com.epam.exceptions.BookNotFoundException;
import com.epam.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(value = BookRestController.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerTest {
	@MockBean
	BookService bookService;

	@Autowired
	MockMvc mvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void booksTest() throws Exception {
		List<BookDto> list = new ArrayList<>();
		when(bookService.getBooks()).thenReturn(list);
		mvc.perform(get("/books")).andExpect(status().isOk());
	}

	@Test
	void oneBookTest() throws Exception {
		when(bookService.getBook(anyInt())).thenReturn(new BookDto());
		mvc.perform(get("/books/1")).andExpect(status().isOk());
	}

	@Test
	void newBookTest() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setId(1);
		bookDto.setName("name");
		bookDto.setPublisher("publisher");
		bookDto.setAuthor("author");
		String bookJson = new Gson().toJson(bookDto);
		when(bookService.addBook(any())).thenReturn(new BookDto());
		mvc.perform(post("/books").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(bookJson)).andExpect(status().isCreated());
	}

	@Test
	void updateBookTest() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setId(1);
		bookDto.setName("name");
		bookDto.setPublisher("publisher");
		bookDto.setAuthor("author");
		String bookJson = new Gson().toJson(bookDto);
		when(bookService.updateBook(anyInt(), any())).thenReturn(new BookDto());
		mvc.perform(put("/books/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(bookJson)).andExpect(status().isCreated());
	}

	@Test
	void deleteBookTest() throws Exception {
		mvc.perform(delete("/books/1")).andExpect(status().isNoContent());
		verify(bookService).delete(anyInt());
	}

	@Test
	void testInsertInvalidBook() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setAuthor("a");

		when(bookService.addBook(bookDto)).thenReturn(bookDto);
		String bookInJson = mapper.writeValueAsString(bookDto);
		mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(bookInJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void getBookByIdTypeMismatch() throws Exception {
		BookDto bookDto = new BookDto();
		when(bookService.getBook(anyInt())).thenReturn(bookDto);
		String bookInJson = mapper.writeValueAsString(bookDto);
		mvc.perform(get("/books/" + bookDto.getAuthor()).contentType(MediaType.APPLICATION_JSON).content(bookInJson))
				.andExpect(status().isBadRequest());
	}
	
	
	@Test
	void booknotfound() throws Exception {
	doThrow(BookNotFoundException.class).when(bookService).getBook(anyInt());
	mvc.perform(get("/books/{book_id}", 56)).andExpect(status().isBadRequest());
	}

}
