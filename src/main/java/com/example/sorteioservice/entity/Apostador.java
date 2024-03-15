package com.example.sorteioservice.entity;

import jakarta.persistence.*;

import java.util.Set;

import lombok.*;

@Table(name="apostador")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Apostador {

    @TableGenerator(
            name="customID",
            table="ID_GEN",
            pkColumnName = "GEN_NAME",
            valueColumnName = "GEN_VAL",
            pkColumnValue = "customID",
            initialValue = 999,
            allocationSize = 9999
    )

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "customID")
    private Long id;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "nome")
    private String nome;
    @ElementCollection
    @CollectionTable(name = "numeros_sorteio", joinColumns = @JoinColumn(name="apostador_id"))
    private Set<Integer> numeros;

}
