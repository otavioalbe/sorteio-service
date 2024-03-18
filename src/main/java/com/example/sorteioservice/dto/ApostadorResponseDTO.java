package com.example.sorteioservice.dto;

import com.example.sorteioservice.entity.Apostador;

public record ApostadorResponseDTO(Long id, String cpf, String nome, String numeros_aposta) {
    public ApostadorResponseDTO(Apostador apostador){
        this(apostador.getId(), apostador.getCpf(), apostador.getNome(), apostador.getNumerosAposta());
    }
}
