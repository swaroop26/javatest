package com.altx.javatest.controller;

import com.altx.javatest.data.Actor;
import com.altx.javatest.exception.ActorNotFoundException;
import com.altx.javatest.repo.ActorRepository;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActorController {

    private final ActorRepository repository;

    ActorController(@NonNull ActorRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/actor")
    public Actor newActor(@RequestBody Actor actor) {
        return repository.save(actor);
    }

    @GetMapping("/actor/{id}")
    public Actor getActor(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ActorNotFoundException(id));
    }

    @GetMapping("/actors")
    public List<Actor> getAll() {
        return repository.findAll();
    }

    @DeleteMapping("/actor/{id}")
    void deleteActor(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
