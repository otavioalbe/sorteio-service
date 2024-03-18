package com.example.sorteioservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApostadorRequestDTO {

    private String cpf;
    private String nome;
    private String numerosAposta;
}
