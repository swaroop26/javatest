package com.altx.javatest.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class MovieModel {

    private Long id;
    private String title;
    private Integer runningTimeMins;
    private List<Long> stars;
}
