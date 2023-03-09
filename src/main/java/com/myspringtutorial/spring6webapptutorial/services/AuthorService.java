package com.myspringtutorial.spring6webapptutorial.services;

import com.myspringtutorial.spring6webapptutorial.domain.Author;

public interface AuthorService {

    Iterable<Author> findAll();

}
