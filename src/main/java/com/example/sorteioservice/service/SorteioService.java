package com.example.sorteioservice.service;

import com.example.sorteioservice.entity.Apostador;
import com.example.sorteioservice.repository.ApostadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SorteioService {

    private boolean apostaAtiva = true;

    @Autowired
    private ApostadorRepository apostadorRepository;

    public List<Apostador> getAll(){
        return apostadorRepository.findAll();
    }

    public ResponseEntity<Apostador> salvarAposta(Apostador dto){
        Set<Integer>numerosSorteados = dto.getNumeros();
        if(validaNumerosSorteados(numerosSorteados)){
            apostadorRepository.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
        //var savedApostador = apostadorRepository.save(ApostadorConverter.fromDTOtoEntity(dto));
        //var successResponse = ApostadorConverter.fromEntityToDTO(savedApostador);
    }

    public boolean verficarApostaAberta(){
        return apostaAtiva;
    }

    public Set<Integer> realizarSorteio(){
        apostaAtiva = false;
        return null;
    }

    public boolean validaNumerosSorteados(Set<Integer>numerosSorteados){
        for(int numeroEscolhido: numerosSorteados){
            if(!(numeroEscolhido >= 1 && numeroEscolhido <= 50) || numerosSorteados.size()!=5){
                return false;
            }
        }
        return true;
    }

//    public ApostadorResponse validaAposta(ApostadorResponse dto){
//        if(dto.)
//    }
}
