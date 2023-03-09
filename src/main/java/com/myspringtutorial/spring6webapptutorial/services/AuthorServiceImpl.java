package com.myspringtutorial.spring6webapptutorial.services;

import com.myspringtutorial.spring6webapptutorial.domain.Author;
import com.myspringtutorial.spring6webapptutorial.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }
}
