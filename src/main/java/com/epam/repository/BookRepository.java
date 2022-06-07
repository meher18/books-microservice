package com.epam.repository;

import org.springframework.data.repository.CrudRepository;

import com.epam.entity.Book;

public interface BookRepository extends CrudRepository<Book, Integer>{

}
