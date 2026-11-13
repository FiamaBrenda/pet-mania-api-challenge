package com.seducD2.petmania.controllers;

import com.seducD2.petmania.entities.Pet;
import com.seducD2.petmania.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping ("api/pets")
@RestController
public class PetController {

    @Autowired
    private PetService service;
    public PetController(PetService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pet> consultarPets(){
        return service.consultarPets();
    }

    public void buscarId(int id){
        service.buscarId(id);
    }

    @PostMapping
    public void criar(@RequestBody Pet p){
        service.criar(p);
    }

}
