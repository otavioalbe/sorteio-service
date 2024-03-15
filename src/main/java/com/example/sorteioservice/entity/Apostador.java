package com.example.sorteioservice.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.example.sorteioservice.entity.Aposta;
import lombok.*;

@Table(name="apostador")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "cpf")
public class Apostador {

    @Id
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "nome")
    private String nome;
}
