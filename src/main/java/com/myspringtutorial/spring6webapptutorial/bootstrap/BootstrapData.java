package com.myspringtutorial.spring6webapptutorial.bootstrap;

import com.myspringtutorial.spring6webapptutorial.domain.Author;
import com.myspringtutorial.spring6webapptutorial.domain.Book;
import com.myspringtutorial.spring6webapptutorial.domain.Publisher;
import com.myspringtutorial.spring6webapptutorial.repositories.AuthorRepository;
import com.myspringtutorial.spring6webapptutorial.repositories.BookRepository;
import com.myspringtutorial.spring6webapptutorial.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component//let spring context detect this
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;


    //This constructor will be called by SpringBoot with implementation
    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // run everytime springboot starts up
        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");

        Book ddd = new Book();
        ddd.setTitle("Domain Driven Design");
        ddd.setIsbn("123456");

        Author ericSaved = authorRepository.save(eric);
        Book dddSaved = bookRepository.save(ddd);

        Author rod = new Author();
        rod.setFirstName("rod");
        rod.setLastName("Johnson");

        Book noEJB = new Book();
        noEJB.setTitle("J2EE Development without EJB");
        noEJB.setIsbn("54747585");

        Author rodSaved = authorRepository.save(rod);
        Book noEJBSaved = bookRepository.save(noEJB);


        //do something with association
        //add book to author && add author to book
        ericSaved.getBooks().add(dddSaved);
        rodSaved.getBooks().add(noEJBSaved);
        dddSaved.getAuthors().add(ericSaved);
        noEJBSaved.getAuthors().add(rodSaved);

        //create new publisher
        Publisher publisher = new Publisher();
        publisher.setPublisherName("My Publisher");
        publisher.setAddress("Tokyo");
        Publisher orcSavedPublisher =  publisherRepository.save(publisher);

        dddSaved.setPublisher(orcSavedPublisher);
        noEJBSaved.setPublisher(orcSavedPublisher);

        authorRepository.save(ericSaved);
        authorRepository.save(rodSaved);
        bookRepository.save(dddSaved);
        bookRepository.save(noEJBSaved);



        System.out.println("In bootstrap");
        System.out.println("Author Count: " + authorRepository.count());
        System.out.println("Book Count: " + bookRepository.count());
        System.out.println("Publisher Count : " + publisherRepository.count());

    }
}
