package br.unisul.aula.projturma.dto;

import java.util.List;

public class TurmaDTO {
    private String nomeTurma;
    private String nomeProfessor;
    private List<String> nomesAlunos;

    public TurmaDTO() {
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public List<String> getNomesAlunos() {
        return nomesAlunos;
    }

    public void setNomesAlunos(List<String> nomesAlunos) {
        this.nomesAlunos = nomesAlunos;
    }
}
