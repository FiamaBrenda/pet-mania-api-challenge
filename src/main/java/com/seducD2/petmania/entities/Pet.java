package com.seducD2.petmania.entities;


import com.seducD2.petmania.entities.enums.Porte;
import com.seducD2.petmania.entities.enums.Sexo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private Sexo sexo;
    private Porte porte;
    private String personalidade;
    private String foto;
    private boolean disponivel;

    public Pet(){}
    public Pet(String nome, String especie, String raca, int idade, Sexo sexo, Porte porte, String personalidade, String foto, boolean disponivel) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.sexo = sexo;
        this.porte = porte;
        this.personalidade = personalidade;
        this.foto = foto;
        this.disponivel = disponivel;
    }
    public Pet(int id,String nome, String especie, String raca, int idade, Sexo sexo, Porte porte, String personalidade, String foto, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.sexo = sexo;
        this.porte = porte;
        this.personalidade = personalidade;
        this.foto = foto;
        this.disponivel = disponivel;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }

    public void setPersonalidade(String personalidade) {
        this.personalidade = personalidade;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public int getIdade() {
        return idade;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Porte getPorte() {
        return porte;
    }

    public String getPersonalidade() {
        return personalidade;
    }

    public String getFoto() {
        return foto;
    }

    public boolean getDisponivel() {
        return disponivel;
    }
}
