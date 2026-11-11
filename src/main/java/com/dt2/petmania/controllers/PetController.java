package com.dt2.petmania.controllers;

import com.dt2.petmania.entities.Pet;
import com.dt2.petmania.repositories.PetRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetRepository repository;

    public PetController(PetRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Optional<Pet> mostrar(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public void criar(@RequestBody Pet p) {
        repository.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
