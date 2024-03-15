package com.example.sorteioservice.service;

import com.example.sorteioservice.entity.Apostador;
import com.example.sorteioservice.repository.ApostadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SorteioService {

    @Autowired
    private ApostadorRepository apostadorRepository;

    public List<Apostador> getAll(){
        return apostadorRepository.findAll();
    }

    public ResponseEntity<Apostador> salvarAposta(Apostador dto){

        //var savedApostador = apostadorRepository.save(ApostadorConverter.fromDTOtoEntity(dto));
        //var successResponse = ApostadorConverter.fromEntityToDTO(savedApostador);
        apostadorRepository.save(dto);
        //Aposta aposta = new Aposta(dto.getCpf(),)

       // apostasRepository.save(dto.getCpf())
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

//    public ApostadorResponse validaAposta(ApostadorResponse dto){
//        if(dto.)
//    }
}
