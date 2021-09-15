package com.altx.javatest;

import com.altx.javatest.data.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

interface MovieRepository extends JpaRepository<Movie, Long> {
}
