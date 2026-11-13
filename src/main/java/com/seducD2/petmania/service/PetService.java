package com.seducD2.petmania.service;

import com.seducD2.petmania.controllers.PetController;
import com.seducD2.petmania.entities.Pet;
import com.seducD2.petmania.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    private final PetRepository repository;
    public PetService(PetRepository repository) {
        this.repository = repository;
    }
    public List<Pet> consultarPets(){
        return repository.findAll();
    }
    public void criar(Pet p){
        Pet pet = new Pet(
                p.getId(),
                p.getNome(),
                p.getEspecie(),
                p.getRaca(),
                p.getIdade(),
                p.getSexo(),
                p.getPorte(),
                p.getPersonalidade(),
                p.getFoto(),
                p.getDisponivel());
        repository.save(pet);
    }

    public Pet buscarId(int id) {
        for (Pet p : consultarPets()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
