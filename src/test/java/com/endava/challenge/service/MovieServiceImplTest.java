package com.endava.challenge.service;

import com.endava.challenge.model.Genre;
import com.endava.challenge.model.Movie;
import com.endava.challenge.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieService.class)
class MovieServiceImplTest {
    @Autowired
    MovieService movieService;

    @MockBean
    MovieRepository movieRepository;

    List<Movie> movies;

    @BeforeEach
    void setMovies(){
        movies = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("1","Horror"));
        movies.add(new Movie("1","title1","overview1","es",1.0,false,"",genres));
        movies.add(new Movie("2","title2","overview1","es",0.0,false,"",genres));
        genres.add(new Genre("1","Comedy"));
        movies.add(new Movie("3","title3","overview1","es",7.0,false,"",genres));
        movies.add(new Movie("4","title1","overview1","es",5.0,false,"",genres));
        movies.add(new Movie("5","title2","overview1","es",8.0,true,"",genres));
        movies.add(new Movie("6","title3","overview1","es",0.0,false,"",genres));
        genres.add(new Genre("1","Drama"));
        movies.add(new Movie("7","title4","overview1","es",9.0,false,"",genres));
        movies.add(new Movie("8","title5","overview1","es",0.0,true,"",genres));
        movies.add(new Movie("9","title1","overview1","es",10.0,false,"",genres));
        genres.add(new Genre("1","Romance"));
        movies.add(new Movie("10","title2","overview1","es",1.0,false,"",genres));
    }

    @Test
    void checkIfGivenTitleReturnCorrectNumberMovies() throws Exception{
        Mockito.when(movieRepository.findAll()).thenReturn(movies);
        Page<Movie> page = movieService.findAllMovies(10,Optional.empty(),Optional.empty(),Optional.of("title1"),Optional.empty(),Optional.empty(),Optional.empty());
        assertEquals(3,page.getTotalElements());
    }

    @Test
    void checkIfGivenIdReturnCorrectMovie() throws Exception{
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("1","Horror"));
        Movie movie = new Movie("1","title1","overview1","es",1.0,false,"",genres);
        Mockito.when(movieRepository.findById(movie.get_id())).thenReturn(Optional.of(movie));
        Optional<Movie> result = movieService.findById(movie.get_id());
        assertEquals(result.get().get_id(), movie.get_id());
    }


}