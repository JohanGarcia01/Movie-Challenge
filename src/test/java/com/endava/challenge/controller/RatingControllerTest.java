package com.endava.challenge.controller;

import com.endava.challenge.model.Rating;
import com.endava.challenge.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RatingController.class)
class RatingControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    RatingService ratingService;

    List<Rating> ratings;

    @BeforeEach
    void setMovies(){
        ratings = new ArrayList<>();
        ratings.add(new Rating("1","2",2.0));
        ratings.add(new Rating("2","1",3.0));
        ratings.add(new Rating("3","2",5.0));
        ratings.add(new Rating("4","1",0.0));
        ratings.add(new Rating("5","3",1.0));
        ratings.add(new Rating("6","4",1.0));
        ratings.add(new Rating("7","2",2.0));
        ratings.add(new Rating("8","5",3.0));
        ratings.add(new Rating("9","1",3.0));
        ratings.add(new Rating("10","4",1.0));
        ratings.add(new Rating("11","5",4.0));
        ratings.add(new Rating("12","3",4.0));
        ratings.add(new Rating("13","1",5.0));
        ratings.add(new Rating("14","1",0.0));
        ratings.add(new Rating("15","3",1.0));
    }

    @Test
    void checkIfGivenIdReturnCorrectRatingMovie() throws Exception{
        String id ="1";
        BDDMockito.given(ratingService.findAverageRatingByMovieId(id)).willReturn(
                ratings.stream().filter(x -> x.getMovie_id().equalsIgnoreCase(id))
                .mapToDouble(Rating::getRating)
                .average()
                .orElse(Double.NaN));
        MvcResult result = mockMvc.perform(get("/api/movie/"+id+"/rating")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String message =  result.getResponse().getContentAsString();
        assertEquals(message,"2.2");
    }


    @Test
    void checkIfLoadCvsFile() throws Exception{
        MvcResult result = mockMvc.perform(post("/api/load/ratings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String message =  result.getResponse().getContentAsString();
        assertEquals(message,"cvs loaded");
    }

}