package br.unisul.aula.projturma.dto;

public class NomesAlunosDTO {
    private String nomeAluno;

    public NomesAlunosDTO() {

    }

    public NomesAlunosDTO(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }
}
