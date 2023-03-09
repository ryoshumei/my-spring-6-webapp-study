package com.myspringtutorial.spring6webapptutorial.repositories;

import com.myspringtutorial.spring6webapptutorial.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
