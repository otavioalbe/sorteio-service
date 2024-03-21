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
import java.util.stream.Collectors;

@Service
public class SorteioService {

    @Autowired
    private ApostadorRepository apostadorRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ApostaConverter apostaConverter;
    @Autowired
    private ApostadorMapper apostadorMapper;


    private boolean apostaAtiva = true;
    private Set<Integer> resultadosFinais;
    private List<ApostadorResponseDTO> vencedores;
    private Map<Integer,Integer> contagemNumeros;

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

    public String realizarSorteio() {
        apostaAtiva = false;
        vencedores = new ArrayList<>();
        sorteioCincoNumeros();
        int rodada;
        for (rodada = 0; rodada < 25; rodada++) {
            verificarVencedores();
            if (!vencedores.isEmpty())
                break;
            sorteiaMaisUm();
        }
        listaNumApostados();
        if (vencedores.isEmpty()) {
            return "Nenhum vencedor desta vez!<br><br>\nNúmeros sorteados:<br>" + apostaConverter.fromSetToString(resultadosFinais) + "\n<br><br>Rodadas:" + rodada
                    + "<br><br>\n\n" + obterContagemNumeros(contagemNumeros);
        } else {

            return formatacaoVencedores() + "\n<br><br>Números sorteados: " + apostaConverter.fromSetToString(resultadosFinais)
                    + "\n<br><br>Rodadas: " + rodada + "<br><br>\n\n" + obterContagemNumeros(contagemNumeros);
        }
    }

    private void verificarVencedores() {
        for (ApostadorResponseDTO apostador : apostadorRepository.findAll().stream().map(ApostadorResponseDTO::new).toList()) {
            if (resultadosFinais.containsAll(apostaConverter.fromStringToSet(apostador))) {
                vencedores.add(apostador);
            }
        }
    }

    public String formatacaoVencedores(){
        vencedores.sort(Comparator.comparing(ApostadorResponseDTO::getNome));
        StringBuilder resultadoFormatado = new StringBuilder("APOSTAS VENCEDORAS (" + vencedores.size() + "): \n\n");
        String resultado = "";
        Set<Integer> numResultado;
        for (ApostadorResponseDTO vencedor : vencedores) {
            numResultado = apostaConverter.fromStringToSet(vencedor);
            resultado = apostaConverter.fromSetToString(numResultado);
            resultadoFormatado.append("<br><br>ID da aposta: ").append(vencedor.getId()).append("\n<br>Nome: ").append(vencedor.getNome()).append("\n<br>CPF: ").append(vencedor.getCpf()).append("\n\n");
        }
        resultadoFormatado.setLength(resultadoFormatado.length() - 2);
        resultadoFormatado.append("\n\n<br><br>NÚMEROS APOSTADOS: ").append(resultado).append("\n");
        return resultadoFormatado.toString();
    }

    public void listaNumApostados(){
        contagemNumeros = new HashMap<>();
        for(ApostadorResponseDTO apostador: listarApostas()){
            for(Integer aux: apostaConverter.fromStringToSet(apostador)){
                contagemNumeros.put(aux, contagemNumeros.getOrDefault(aux,0)+1);
            }
        }
    }

    public String obterContagemNumeros(Map<Integer, Integer> contagemNumeros) {
        Map<Integer, Integer> contagemNumerosOrdenados = contagemNumeros.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue, (e1,e2)-> e1, LinkedHashMap::new));

        StringBuilder resultado = new StringBuilder("N° - Qtd de apostas\n<br>");
        for (Map.Entry<Integer, Integer> entry : contagemNumerosOrdenados.entrySet()) {
            resultado.append(entry.getKey()).append("  -  ").append(entry.getValue()).append("\n<br>");
        }
        return resultado.toString();
    }

    public void sorteioCincoNumeros(){
        resultadosFinais = new HashSet<>();
        Random random = new Random();
        while (resultadosFinais.size() < 5) {
            var numeroSorteado = random.nextInt(50) + 1;
            resultadosFinais.add(numeroSorteado);
        }
    }

    public void sorteiaMaisUm(){
        Random random = new Random();
        var num = random.nextInt(50) + 1;
        if(!resultadosFinais.contains(num)) {
            resultadosFinais.add(num);
        }else{
            sorteiaMaisUm();
        }
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

    public ResponseEntity<String> premiacao(String cpf) {
        for(ApostadorResponseDTO apostador: vencedores) {
            if (apostador.getCpf().equals(cpf)) {
                return ResponseEntity.ok("Parabéns! Você acertou todos os números da Baita Sena!");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Infelizmente não foi seu dia de sorte... Tente novamente na próxima!");
    }
}
