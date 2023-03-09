package com.myspringtutorial.spring6webapptutorial.repositories;

import com.myspringtutorial.spring6webapptutorial.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
//JPA will provide the implementation