package com.dt2.petmania.repositories;

import com.dt2.petmania.entities.Pet;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer>{
}
