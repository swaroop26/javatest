package com.altx.javatest.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
    private String stars;
}
