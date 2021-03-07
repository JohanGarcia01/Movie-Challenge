package com.endava.challenge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.endava.challenge.model.Movie;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
}
