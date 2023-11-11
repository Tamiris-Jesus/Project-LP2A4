package intelli.med.api.controller;

import intelli.med.api.domain.paciente.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;
import intelli.med.api.domain.endereco.DadosEndereco;
import intelli.med.api.domain.endereco.Endereco;
import intelli.med.api.domain.endereco.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;
    @Autowired
    private EnderecoRepository enderecoRepository;


    @PostMapping
    @Transactional
    // É uma convenção de API Rest retornar a uri de um recurso criado
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dados);
        repository.save(paciente);

        for (DadosEndereco dadosEndereco : dados.enderecos()) {
            Endereco endereco = new Endereco(paciente, dadosEndereco);
            enderecoRepository.save(endereco);
        }

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemPaciente>> listar() {
        var page = repository.findAllByAtivoTrue().stream().map(DadosListagemPaciente::new).toList();
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {

        List<Endereco> enderecos = enderecoRepository.findAllByPessoaId(id);

        var paciente = repository.getReferenceById(id);

        return ResponseEntity.ok(new DetalhamentoPacienteEndereco(paciente, enderecos));
    }

}
