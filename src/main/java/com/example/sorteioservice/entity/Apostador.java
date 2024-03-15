package com.example.sorteioservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Apostador {

    @Id
    private String cpf;
    private String nome;

}
