package com.example.sorteioservice.mapper;

import com.example.sorteioservice.dto.ApostadorRequestDTO;
import com.example.sorteioservice.dto.ApostadorResponseDTO;
import com.example.sorteioservice.entity.Apostador;

import org.springframework.stereotype.Service;

@Service
public class ApostadorMapper {

    public ApostadorResponseDTO fromEntityToResponseDTO(Apostador apostador) {
        return ApostadorResponseDTO.builder()
                .id(apostador.getId())
                .cpf(apostador.getCpf())
                .nome(apostador.getNome())
                .numerosAposta(apostador.getNumerosAposta())
                .build();
    }

    public Apostador fromRequestToEntity(ApostadorRequestDTO dto){
        return Apostador.builder()
                .cpf(dto.getCpf())
                .nome(dto.getNome())
                .numerosAposta(dto.getNumerosAposta())
                .build();
    }

    public ApostadorResponseDTO fromRequestToResponse(ApostadorRequestDTO dto){
        return ApostadorResponseDTO.builder()
                .cpf(dto.getCpf())
                .nome(dto.getNome())
                .numerosAposta(dto.getNumerosAposta())
                .build();
    }
}
