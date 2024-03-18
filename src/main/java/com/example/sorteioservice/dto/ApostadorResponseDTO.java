package com.example.sorteioservice.dto;

import com.example.sorteioservice.entity.Apostador;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApostadorResponseDTO {

    private Long id;
    private String cpf;
    private String nome;
    private String numerosAposta;

    public ApostadorResponseDTO(Apostador apostador){
        this.id = apostador.getId();
        this.cpf = apostador.getCpf();
        this.nome = apostador.getNome();
        this.numerosAposta = apostador.getNumerosAposta();
    }

    public ApostadorResponseDTO(Long id, String cpf, String nome, String numerosAposta) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.numerosAposta = numerosAposta;
    }
}

