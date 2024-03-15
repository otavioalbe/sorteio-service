package com.example.sorteioservice.dto;

import com.example.sorteioservice.entity.Apostador;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApostadorDTO{
    private Long id;
    private String cpf;
    private String nome;
}
