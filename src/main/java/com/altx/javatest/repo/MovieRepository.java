package com.altx.javatest.repo;

import com.altx.javatest.data.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
