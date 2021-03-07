package com.endava.challenge.service;

public interface RatingService {
    void saveRatings();

    double findAverageRatingByMovieId(String id);
}
