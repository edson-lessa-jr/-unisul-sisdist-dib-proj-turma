package br.unisul.aula.projturma.controller;

import br.unisul.aula.projturma.dto.TurmaDTO;
import br.unisul.aula.projturma.model.Turma;
import br.unisul.aula.projturma.repository.TurmaRepository;
import br.unisul.aula.projturma.services.TumaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TurmaController {
    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private TumaService tumaService;

    @PostMapping
    public void incluirTurma(@RequestBody TurmaDTO dto){
        tumaService.registrarTurma(dto);
    }
}
