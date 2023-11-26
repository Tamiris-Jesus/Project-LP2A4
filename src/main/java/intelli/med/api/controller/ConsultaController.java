package intelli.med.api.controller;

import intelli.med.api.domain.consulta.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository repository;

    @GetMapping
    public ResponseEntity<List<DadosListagemConsulta>> listar() {
        var page = repository.findAllByMotivoCancelamentoIsNull().stream().map(DadosListagemConsulta::new).toList();
        return ResponseEntity.ok(page);
    }

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {

        var consulta = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }
}
