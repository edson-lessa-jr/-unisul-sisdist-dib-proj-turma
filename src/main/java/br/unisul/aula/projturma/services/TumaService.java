package br.unisul.aula.projturma.services;

import br.unisul.aula.projturma.dto.DadosAlunoDTO;
import br.unisul.aula.projturma.dto.DadosProfessorDTO;
import br.unisul.aula.projturma.dto.TurmaDTO;
import br.unisul.aula.projturma.model.Turma;
import br.unisul.aula.projturma.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TumaService {
    public static final String URL_PADRAO_PROFESSOR = "http://localhost:8082/";
    public static final String URL_PADRAO_ALUNO = "http://localhost:8081";
    @Autowired
    private TurmaRepository turmaRepository;

    public void registrarTurma(TurmaDTO dto) {
        DadosProfessorDTO professorDTO = buscarProfessor(dto);
        List<DadosAlunoDTO> dadosAlunoDTOS = buscarAlunos(dto.getNomesAlunos());
        Turma turma = new Turma();
        turma.setNome(dto.getNomeTurma());
        turma.setProfessorID(professorDTO.getId());
        Set<Long> longs = new HashSet<>();
        for (DadosAlunoDTO alunoDTO: dadosAlunoDTOS){
            longs.add(alunoDTO.getId());
        }
        turma.setMatriculaAlunos(longs);
        turmaRepository.save(turma);
    }

    private List<DadosAlunoDTO> buscarAlunos(List<String> nomesAlunos) {
        RestTemplate restTemplate = new RestTemplate();
        List<DadosAlunoDTO> alunoDTOS = new ArrayList<>();
        for (String nome : nomesAlunos) {
            ResponseEntity<DadosAlunoDTO> exchange = restTemplate.exchange(URL_PADRAO_ALUNO +"/nome/" + nome,
                    HttpMethod.GET,
                    null, DadosAlunoDTO.class);
            alunoDTOS.add(exchange.getBody());
        }
        return alunoDTOS;
    }

    private DadosProfessorDTO buscarProfessor(TurmaDTO dto) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DadosProfessorDTO> exchange =
                restTemplate.exchange(URL_PADRAO_PROFESSOR +"nome/" + dto.getNomeProfessor(),
                        HttpMethod.GET,
                        null, DadosProfessorDTO.class);
        return exchange.getBody();
    }
}
