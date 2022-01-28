package com.altx.javatest.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

/**
 * Database representation of a Movie
 */

@Data
@Accessors(chain = true)
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Movie {

    private @Id @GeneratedValue Long id;
    private String title;
    private Integer runningTimeMins;
    @ManyToMany
    private List<Actor> stars;
}
