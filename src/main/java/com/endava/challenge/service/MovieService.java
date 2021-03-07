package com.endava.challenge.service;

import com.endava.challenge.model.Movie;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Optional<Movie> findById(String id);

    Page<Movie> findAllMovies(int page, Optional<Boolean> adult, Optional<String> genres, Optional<String> title, Optional<Integer> limit, Optional<Boolean> sortByTitle, Optional<Boolean> sortByBudget);

    void saveMovie();

}
