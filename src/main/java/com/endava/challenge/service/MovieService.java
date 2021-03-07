package com.endava.challenge.service;

import com.endava.challenge.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MovieService {

    Optional<Movie> findById(String id);

    Page<Movie> findAllMovies(int page, Optional<Boolean> adult, Optional<String> genres, Optional<String> title, Optional<Integer> limit, Optional<Boolean> sortByTitle, Optional<Boolean> sortByBudget);

    void saveMovie();

}
