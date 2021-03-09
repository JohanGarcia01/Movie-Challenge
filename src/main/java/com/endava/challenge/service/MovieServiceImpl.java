package com.endava.challenge.service;

import com.endava.challenge.model.Movie;
import com.endava.challenge.repository.MovieRepository;
import com.endava.challenge.util.CvsReader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;
    int defaultSize = 100;
    List<String> defaultArrayGenres;
    List<Movie> movies;

    @Override
    public Optional<Movie> findById(String id) {
        return movieRepository.findById(id);
    }

    @Override
    public Page<Movie> findAllMovies(int page, Optional<Boolean> adult, Optional<String> genres, Optional<String> title, Optional<Integer> limit, Optional<Boolean> sortByTitle, Optional<Boolean> sortByBudget) {
        limit.ifPresent(integer -> defaultSize = integer);
        genres.ifPresent(string -> defaultArrayGenres = Arrays.stream(string.split(",")).collect(Collectors.toList()));
        movies = movieRepository.findAll().stream()
                .filter(t -> title.isEmpty() || t.getOriginal_title().toLowerCase().contains(title.get().toLowerCase()))
                .filter(a -> adult.isEmpty() || a.isAdult() == adult.get())
                .filter(g -> genres.isEmpty() || g.getGenres().stream().anyMatch(x -> defaultArrayGenres.stream().anyMatch(i -> i.equalsIgnoreCase(x.getName()))))
                .collect(Collectors.toList());
        sortByBudget.ifPresent(string -> movies = movies.stream().sorted(Comparator.comparingDouble(Movie::getBudget)).collect(Collectors.toList()));
        sortByTitle.ifPresent(string -> movies = movies.stream().sorted(Comparator.comparing(Movie::getOriginal_title)).collect(Collectors.toList()));
        return findPaginated(PageRequest.of(page,defaultSize),movies);
    }

    @Override
    public void saveMovie() {
        List<Movie> cvsMovies = CvsReader.convertFileToTargetObject("movies_metadata.csv",Movie.class);
        cvsMovies.forEach(Movie::buildGenres);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        cvsMovies.forEach(x -> executorService.submit(() -> { movieRepository.save(x);}));
    }

    public Page<Movie> findPaginated(Pageable pageable, List<Movie> movies) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Movie> list;
        if (movies.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, movies.size());
            list = movies.subList(startItem, toIndex);
        }
        return new PageImpl<Movie>(list, PageRequest.of(currentPage, pageSize), movies.size());
    }

}
