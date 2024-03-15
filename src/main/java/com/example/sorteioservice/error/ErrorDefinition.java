package com.example.sorteioservice.error;

public enum ErrorDefinition {
    CPF_INVALIDO("CPF digitado incorretamente."),
    NUMERO_DE_ESCOLHA_INVALIDO("Número de escolha foi digitado incorretamente");

    private final String mensagem;

    ErrorDefinition(String mensagemErro){
        this.mensagem = mensagemErro;
    }
}
