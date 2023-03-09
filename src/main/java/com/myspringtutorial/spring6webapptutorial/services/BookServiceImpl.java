package com.myspringtutorial.spring6webapptutorial.services;

import com.myspringtutorial.spring6webapptutorial.domain.Book;
import com.myspringtutorial.spring6webapptutorial.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }
}
