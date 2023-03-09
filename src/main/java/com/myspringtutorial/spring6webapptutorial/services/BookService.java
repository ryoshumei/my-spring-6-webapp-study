package com.myspringtutorial.spring6webapptutorial.services;

import com.myspringtutorial.spring6webapptutorial.domain.Book;

import java.util.Iterator;

public interface BookService {

    Iterable<Book> findAll();

}
