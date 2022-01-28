package com.altx.javatest.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Database representation of a Actor
 */

@Data
@Accessors(chain = true)
@Entity
public class Actor {

    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
}
