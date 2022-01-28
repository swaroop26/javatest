package com.altx.javatest.controller;

import com.altx.javatest.data.Actor;
import com.altx.javatest.data.Movie;
import com.altx.javatest.exception.ActorNotFoundException;
import com.altx.javatest.exception.MovieNotFoundException;
import com.altx.javatest.model.MovieModel;
import com.altx.javatest.repo.ActorRepository;
import com.altx.javatest.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieController {
    @Autowired
    private MovieRepository repository;
    @Autowired
    private ActorRepository actorRepository;


    @PostMapping("/movie")
    public Movie newMovie(@RequestBody MovieModel movieModel) {
        List<Actor> stars = movieModel.getStars().stream()
                .map(id -> actorRepository.findById(id).orElseThrow(() -> new ActorNotFoundException(id)))
                .collect(Collectors.toList());
        Movie movie = new Movie()
                .setId(movieModel.getId())
                .setRunningTimeMins(movieModel.getRunningTimeMins())
                .setTitle(movieModel.getTitle())
                .setStars(stars);

        return repository.save(movie);
    }

    @GetMapping("/movie/{id}")
    public Movie getMovie(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    @GetMapping("/movies")
    public List<Movie> all() {
        return repository.findAll();
    }

    @DeleteMapping("/movie/{id}")
    void deleteMovie(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
