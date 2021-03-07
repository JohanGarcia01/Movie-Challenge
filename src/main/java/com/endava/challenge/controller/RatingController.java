package com.endava.challenge.controller;

import com.endava.challenge.model.Rating;
import com.endava.challenge.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/load/ratings")
    public ResponseEntity<?> addRatings() {
        ratingService.saveRatings();
        return ResponseEntity.status(HttpStatus.CREATED).body("cvs loaded");
    }

    @GetMapping("/movie/{id}/rating")
    public double getRatingByMovie(@PathVariable("id") String id) {
        return ratingService.findAverageRatingByMovieId(id);
    }
}
