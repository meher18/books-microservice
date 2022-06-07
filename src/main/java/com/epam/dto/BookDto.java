package com.epam.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

	int id;

	@NotEmpty(message = "Name cannot be empty")
	String name;

	@NotEmpty(message = "Publisher cannot be empty")
	String publisher;

	@NotEmpty(message = "Author cannot be empty")
	String author;
}
