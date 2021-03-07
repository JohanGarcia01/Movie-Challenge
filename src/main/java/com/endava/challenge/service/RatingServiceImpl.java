package com.endava.challenge.service;

import com.endava.challenge.model.Rating;
import com.endava.challenge.repository.RatingRepository;
import com.endava.challenge.util.CvsReader;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class RatingServiceImpl implements RatingService{

    @Autowired
    private RatingRepository ratingRepository;
    @Override
    public void saveRatings() {
        List<Rating> ratings = CvsReader.convertFileToTargetObject("ratings_small.csv", Rating.class);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ratings.forEach(x -> executorService.submit(() -> { ratingRepository.save(x);}));
    }

    @Override
    public double findAverageRatingByMovieId(String id) {
        return ratingRepository.findAll().stream()
                .filter(x -> x.getMovie_id().equalsIgnoreCase(id))
                .mapToDouble(Rating::getRating)
                .average()
                .orElse(Double.NaN);
    }
}
