package com.example.sorteioservice.controller;

import com.example.sorteioservice.dto.ApostadorResponseDTO;
import com.example.sorteioservice.entity.Apostador;
import com.example.sorteioservice.service.SorteioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/sorteio")
public class SorteioController {

    @Autowired
    private SorteioService sorteioService;

    @PostMapping("/apostar")
    public ResponseEntity<Apostador> registraAposta(@RequestBody Apostador dto){
        return sorteioService.salvarAposta(dto);
    }

    @GetMapping("/listar")
    public List<ApostadorResponseDTO> listarApostas(){
        return sorteioService.listarApostas();
    }

    @GetMapping("/executar-sorteio")
    public String executarSorteio(){
        return sorteioService.realizarSorteio();
    }

    @GetMapping("/reiniciar-aposta")
    public String reiniciarAposta(){
        return sorteioService.resetAposta();
    }

    @GetMapping("/aposta-aberta")
    public boolean isApostaAberta(){
        return sorteioService.verficarApostaAberta();
    }
}
