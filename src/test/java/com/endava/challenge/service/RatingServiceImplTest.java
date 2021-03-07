package com.endava.challenge.service;

import com.endava.challenge.model.Genre;
import com.endava.challenge.model.Rating;
import com.endava.challenge.repository.RatingRepository;
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
@WebMvcTest(RatingService.class)
class RatingServiceImplTest {
    @Autowired
    RatingService ratingService;

    @MockBean
    RatingRepository ratingRepository;

    List<Rating> ratings;

    @BeforeEach
    void setRatings(){
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
        Mockito.when(ratingRepository.findAll()).thenReturn(ratings);
        double result = ratingService.findAverageRatingByMovieId("1");
        assertEquals(2.2,result);
    }


}