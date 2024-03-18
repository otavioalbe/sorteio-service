package com.example.sorteioservice.dto;

import com.example.sorteioservice.entity.Apostador;
import lombok.Builder;

@Builder
public record ApostadorResponseDTO(Long id, String cpf, String nome, String numerosAposta) {
    public ApostadorResponseDTO(Apostador apostador){
        this(apostador.getId(), apostador.getCpf(), apostador.getNome(), apostador.getNumerosAposta());
    }
}
