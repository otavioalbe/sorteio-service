package com.example.sorteioservice.entity;

import com.example.sorteioservice.dto.ApostadorRequestDTO;
import jakarta.persistence.*;

import lombok.*;

@Table(name="apostador")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Apostador {
    // Table strategy para inicialização do id em 999
    @TableGenerator(
            name="customID",
            table="custom_id",
            valueColumnName = "GEN_VAL",
            pkColumnValue = "customID",
            initialValue = 999,
            allocationSize = Integer.MAX_VALUE
    )

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "customID")
    private Long id;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "nome")
    private String nome;
    @Column(name = "numeros_aposta")
    private String numerosAposta;

}
