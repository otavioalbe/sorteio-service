package com.example.sorteioservice.mapper;

import com.example.sorteioservice.dto.ApostadorDTO;
import com.example.sorteioservice.entity.Apostador;

public class ApostadorConverter {

    public static Apostador fromDTOtoEntity(ApostadorDTO dto){
        return Apostador.builder().
                cpf(dto.getCpf()).
                nome(dto.getNome())
                .build();
    }

    public static ApostadorDTO fromEntityToDTO(Apostador apostador){
        return ApostadorDTO.builder().
                cpf(apostador.getCpf()).
                nome(apostador.getNome())
                .build();
    }
}
