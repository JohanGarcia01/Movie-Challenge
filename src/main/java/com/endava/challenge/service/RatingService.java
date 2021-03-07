package com.endava.challenge.service;

import org.springframework.stereotype.Service;

@Service
public interface RatingService {
    void saveRatings();

    double findAverageRatingByMovieId(String id);
}
