package com.altx.javatest;

import com.altx.javatest.data.Movie;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    private final MovieRepository repository;

    MovieController(@NonNull MovieRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/movie")
    public Movie newMovie(@RequestBody Movie movie) {
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
