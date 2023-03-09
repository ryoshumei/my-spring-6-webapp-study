package com.myspringtutorial.spring6webapptutorial.repositories;

import com.myspringtutorial.spring6webapptutorial.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}

//JPA will provide the implementation
