package com.imepac.ads.servico_de_notas.entities;

import com.imepac.ads.servico_de_notas.dto.NotaDTO;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Getter
@Setter
@DynamoDbBean
public class NotaEntity {
    
    private String pk;
    private String sk;
    private String nomeAluno;
    private String dataNascimento;
    private String nota;
    private String dataLancamento;
    private String nomeDisciplina;
    private String nomeProfessor;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("SK")
    public String getSk() {
        return sk;
    }

    static public NotaEntity fromNotaDTO(String matricula, String idDisciplina, NotaDTO notaDTO) {
        NotaEntity nota = new NotaEntity();
        nota.setPk("ALUNO#" + matricula);
        nota.setSk("NOTA#" + idDisciplina);
        nota.setNomeAluno("Aluno");
        nota.setDataNascimento("01/01/2000");
        nota.setNota(String.valueOf(notaDTO.getNota()));
        nota.setDataLancamento("01/01/2021");
        nota.setNomeDisciplina(notaDTO.getDisciplina());
        nota.setNomeProfessor(notaDTO.getProfessor());
        return nota;
    }

}
