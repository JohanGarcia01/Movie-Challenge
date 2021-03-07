package com.endava.challenge.controller;

import com.endava.challenge.model.Movie;
import com.endava.challenge.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movie/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable("id") String id) {
        Optional<Movie> movie = movieService.findById(id);
        if(movie.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/movies/{page}")
    public Page<Movie> getMovies(@PathVariable("page") int page,
                                 @RequestParam(name = "adult", required = false) Optional<Boolean> adult,
                                 @RequestParam(name = "genres", required = false) Optional<String> genres,
                                 @RequestParam(name = "title", required = false) Optional<String> title,
                                 @RequestParam(name = "limit", required = false) Optional<Integer> limit,
                                 @RequestParam(name = "sortByTitle", required = false) Optional<Boolean> sortByTitle,
                                 @RequestParam(name = "sortByBudget", required = false) Optional<Boolean> sortByBudget) {
        return movieService.findAllMovies(page,adult,genres,title,limit,sortByTitle,sortByBudget);
    }

    @PostMapping("/load/movies")
    public ResponseEntity<?>  addMovies() {
        movieService.saveMovie();
        return ResponseEntity.status(HttpStatus.CREATED).body("cvs loaded");
    }
}
