package com.example.sorteioservice.dto;

import com.example.sorteioservice.entity.Apostador;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class ApostadorDTO{
    private String cpf;
    private String nome;
}
