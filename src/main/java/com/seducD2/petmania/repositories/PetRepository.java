package com.seducD2.petmania.repositories;


import com.seducD2.petmania.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Integer> {
}
