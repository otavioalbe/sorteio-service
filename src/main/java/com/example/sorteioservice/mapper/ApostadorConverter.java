package com.example.sorteioservice.mapper;

import com.example.sorteioservice.dto.ApostadorDTO;
import com.example.sorteioservice.entity.Apostador;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class ApostadorConverter {

    public Set<Integer> fromStringToSet(Apostador apostador){
        String []numerosDTO = apostador.getNumerosAposta().split(",");
        return Arrays.stream(numerosDTO).map(Integer::parseInt).collect(Collectors.toSet());
    }
    public String fromSetToString(Set<Integer> numerosSet){
        List<Integer> sortedList = new ArrayList<>(numerosSet);
        Collections.sort(sortedList); // Ordena a lista

        StringBuilder sb = new StringBuilder();
        for (Integer numero : sortedList) {
            sb.append(numero).append(",");
        }
        if (!sb.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
