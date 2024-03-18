package com.example.sorteioservice.service;

import com.example.sorteioservice.dto.ApostadorRequestDTO;
import com.example.sorteioservice.dto.ApostadorResponseDTO;
import com.example.sorteioservice.mapper.ApostaConverter;
import com.example.sorteioservice.mapper.ApostadorMapper;
import com.example.sorteioservice.repository.ApostadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SorteioService {

    private boolean apostaAtiva = true;

    @Autowired
    private ApostadorRepository apostadorRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ApostaConverter apostaConverter;
    @Autowired
    private ApostadorMapper apostadorMapper;

    public List<ApostadorResponseDTO> listarApostas(){
        return apostadorRepository.findAll().stream().map(ApostadorResponseDTO::new).toList();
    }

    public ResponseEntity<ApostadorResponseDTO> salvarAposta(ApostadorRequestDTO dto){
        if(verficarApostaAberta()) {

            Set<Integer> numerosSorteados = apostaConverter.fromStringToSet(dto);

            if (validaNumerosSorteados(numerosSorteados)) {
                var response = apostadorRepository.save(apostadorMapper.fromRequestToEntity(dto));
                return ResponseEntity.status(HttpStatus.CREATED).body(apostadorMapper.fromEntityToResponseDTO(response));
            }
        }

        var badResponse = apostadorMapper.fromRequestToResponse(dto);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badResponse);
    }

    public boolean verficarApostaAberta(){
        return apostaAtiva;
    }

    public String realizarSorteio(){
        apostaAtiva = false;
        Set<Integer> numerosSorteados = new HashSet<>();
        Random random = new Random();
        while (numerosSorteados.size() < 5) {
            int numeroSorteado = random.nextInt(50) + 1;
            numerosSorteados.add(numeroSorteado);
        }
        return apostaConverter.fromSetToString(numerosSorteados);
    }

    public boolean validaNumerosSorteados(Set<Integer>numerosSorteados){
        for(int numeroEscolhido: numerosSorteados){
            if(!(numeroEscolhido >= 1 && numeroEscolhido <= 50) || numerosSorteados.size()!=5){
                return false;
            }
        }
        return true;
    }

    public String resetAposta(){
        apostaAtiva = true;
        try {
            jdbcTemplate.update("DELETE FROM APOSTADOR");
            jdbcTemplate.update("DELETE FROM CUSTOM_ID");
            jdbcTemplate.execute("INSERT INTO CUSTOM_ID (GEN_VAL, SEQUENCE_NAME) VALUES (999, 'custom_id')");
            return "Aposta resetada!";
        } catch (Exception e) {
            return "Erro ao limpar o cache: " + e.getMessage();
        }
    }
}
