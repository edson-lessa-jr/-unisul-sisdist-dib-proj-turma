package br.unisul.aula.projturma.services;

import br.unisul.aula.projturma.dto.*;
import br.unisul.aula.projturma.model.Turma;
import br.unisul.aula.projturma.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TumaService {
    public static final String URL_PADRAO_PROFESSOR = "http://professor/";
    public static final String URL_PADRAO_ALUNO = "http://aluno/";

    @Autowired
    private RestTemplate cliente;


    @Autowired
    private TurmaRepository turmaRepository;

    public TumaService() {

    }

    public void registrarTurma(TurmaDTO dto) {
        DadosProfessorDTO professorDTO = buscarProfessor(dto);
        List<NomesAlunosDTO> alunosDTOList = new ArrayList<>();
        for (int i = 0; i < dto.getNomesAlunos().size(); i++) {
            alunosDTOList.add(new NomesAlunosDTO(dto.getNomesAlunos().get(i)));
        }
        List<DadosAlunoDTO> dadosAlunoDTOS = buscarAlunos(alunosDTOList);

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

    private List<DadosAlunoDTO> buscarAlunos(List<NomesAlunosDTO> nomesAlunosDTO) {
        List<DadosAlunoDTO> alunoDTOS = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<List<NomesAlunosDTO>> bodyRequestListaAlunos =
                new HttpEntity<>(nomesAlunosDTO, httpHeaders);
        ResponseEntity<DadosAlunoDTO[]> exchange =
                    cliente.exchange(URL_PADRAO_ALUNO +"nome/" ,
                    HttpMethod.POST,
                    bodyRequestListaAlunos, DadosAlunoDTO[].class);
            alunoDTOS.addAll(Arrays.asList(exchange.getBody()));
        return alunoDTOS;
    }

    private DadosProfessorDTO buscarProfessor(TurmaDTO dto) {
        ResponseEntity<DadosProfessorDTO> exchange =
                cliente.exchange(URL_PADRAO_PROFESSOR +"nome/" + dto.getNomeProfessor(),
                        HttpMethod.GET,
                        null, DadosProfessorDTO.class);
        return exchange.getBody();
    }

    public List<InfoTurmaDTO> listarTurmas() {
        List<Turma> turmaList = turmaRepository.findAll();
        List<InfoTurmaDTO> dtos =new ArrayList<>();
        for(Turma turma: turmaList){
            InfoTurmaDTO dto = new InfoTurmaDTO();
            dto.setId(turma.getId());
            dto.setNomeTurma(turma.getNome());
            DadosProfessorDTO nomeProfessor = buscarNomeDoProfessor(turma.getProfessorID());
            dto.setNomeProfessor(nomeProfessor.getNome());
            List<DadosAlunoDTO> listaMatriculas = new ArrayList<>();
            for(Long matricula: turma.getMatriculaAlunos()){
                DadosAlunoDTO alunoDTO = new DadosAlunoDTO();
                alunoDTO.setId(matricula);
                listaMatriculas.add(alunoDTO);
            }
            List<DadosAlunoDTO> dadosAlunoNomes = buscarAlunosID(listaMatriculas);
            dto.setNomesAlunosConvert(dadosAlunoNomes);
            dtos.add(dto);
        }
        return dtos;
    }

    private List<DadosAlunoDTO> buscarAlunosID(List<DadosAlunoDTO> dadosAlunoDTOS) {
        List<DadosAlunoDTO> alunoDTOS = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<List<DadosAlunoDTO>> bodyRequestListaAlunos =
                new HttpEntity<>(dadosAlunoDTOS, httpHeaders);
        ResponseEntity<DadosAlunoDTO[]> exchange =
                cliente.exchange(URL_PADRAO_ALUNO +"info/" ,
                        HttpMethod.POST,
                        bodyRequestListaAlunos, DadosAlunoDTO[].class);
        alunoDTOS.addAll(Arrays.asList(exchange.getBody()));
        return alunoDTOS;
    }

    private DadosProfessorDTO buscarNomeDoProfessor(Long professorID) {
        ResponseEntity<DadosProfessorDTO> exchange =
                cliente.exchange(URL_PADRAO_PROFESSOR +"info/"+ professorID,
                        HttpMethod.GET,
                        null, DadosProfessorDTO.class);
        return exchange.getBody();
    }
}
