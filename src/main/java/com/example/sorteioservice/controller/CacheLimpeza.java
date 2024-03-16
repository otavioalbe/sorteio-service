package com.example.sorteioservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/começar")
public class CacheLimpeza {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/clear")
    public String clearCache() {
        try {
            jdbcTemplate.update("DELETE FROM APOSTADOR");
            jdbcTemplate.update("DELETE FROM CUSTOM_ID");
            return "Dados limpos com sucesso";
        } catch (Exception e) {
            return "Erro ao limpar o cache: " + e.getMessage();
        }
    }
}
