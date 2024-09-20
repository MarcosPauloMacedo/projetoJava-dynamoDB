package com.imepac.ads.servico_de_notas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imepac.ads.servico_de_notas.dto.NotaDTO;
import com.imepac.ads.servico_de_notas.entities.NotaEntity;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("v1/notas")
public class NotaController {
    private DynamoDbTemplate dynamoDbTemplate;

    public NotaController(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    @PostMapping("/{matricula}/{idDisciplina}")
    public ResponseEntity<Void> salvar(
        @PathVariable("matricula") String matricula,
        @PathVariable("idDisciplina") String idDisciplina,
        @RequestBody NotaDTO notaDTO
    ) {

        NotaEntity nota = NotaEntity.fromNotaDTO(matricula, idDisciplina, notaDTO);

        dynamoDbTemplate.save(nota);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<List<NotaEntity>> buscarNotasPorAluno(@PathVariable("matricula") String matricula) {
        Key key = Key.builder()
        .partitionValue(matricula)
        .sortValue("NOTA#")
        .build();

        QueryConditional notas = QueryConditional.sortBeginsWith(key);

        QueryEnhancedRequest query = QueryEnhancedRequest
        .builder()
        .queryConditional(notas)
        .build();

        try {
            PageIterable<NotaEntity> nota = dynamoDbTemplate.query(query, NotaEntity.class);

            List<NotaEntity> notaList = nota.items().stream().toList();
            
            return ResponseEntity.ok(notaList);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}
