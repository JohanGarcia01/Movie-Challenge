package com.endava.challenge.controller;

import com.endava.challenge.model.Genre;
import com.endava.challenge.service.MovieService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.endava.challenge.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieService movieService;

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
    void checkIfGivenIdReturnCorrectMovie() throws Exception{
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("1","Horror"));
        Movie movie = new Movie("1","title1","overview1","es",1.0,false,"",genres);
        BDDMockito.given(movieService.findById(movie.get_id())).willReturn(Optional.of(movie));
        MvcResult result = mockMvc.perform(get("/api/movie/"+movie.get_id())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String id = JsonPath.read(result.getResponse().getContentAsString(), "$._id");
        assertEquals(id,movie.get_id());
    }

    @Test
    void checkIfLoadCvsFile() throws Exception{
        MvcResult result = mockMvc.perform(post("/api/load/movies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String message =  result.getResponse().getContentAsString();
        assertEquals(message,"cvs loaded");
    }

    @Test
    void checkIfGivenNumberPageReturnCorrectNumberMovies() throws Exception{
        Page<Movie> page =  new PageImpl<Movie>(movies, PageRequest.of(1, 10), movies.size());
        Mockito.when(movieService.findAllMovies(10, Optional.empty(),Optional.empty(),Optional.of("Title1"),Optional.empty(),Optional.empty(),Optional.empty())).thenReturn(page);
        String url = "/api/movies/1?title=title1";
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }
}