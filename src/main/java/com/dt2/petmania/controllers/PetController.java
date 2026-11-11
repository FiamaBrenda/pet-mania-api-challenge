package com.dt2.petmania.controllers;

import com.dt2.petmania.entities.Pet;
import com.dt2.petmania.repositories.PetRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pets")
public class PetController {

    private final PetRepository repository;
    public PetController(PetRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Pet> mostrar(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Object buscarPorId(@PathVariable Integer id) {
        try {
            Pet pet = repository.findById(id).orElse(null);
            if (pet == null) {
                return "Pet não encontrado!";
            }
            return pet;
        } catch (Exception e) {
            return "Erro ao buscar o pet";
        }
    }

    @PostMapping
    public String criar(@RequestBody Pet p) {
        try {
            repository.save(p);
            return "Animal salvo!" + p.getNome();
        } catch (Exception e) {
            return "Nao foi possivel salvar";
        }
    }

    //CRIE O DELETE
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Integer id){
        repository.deleteById(id);
        return "Excluido com sucesso";
    }
}
