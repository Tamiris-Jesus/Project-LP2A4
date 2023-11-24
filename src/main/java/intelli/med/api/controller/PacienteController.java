package intelli.med.api.controller;

import intelli.med.api.domain.endereco.*;
import intelli.med.api.domain.paciente.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.stream.Collectors;


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

        List<Long> enderecoIds = dados.enderecos().stream().map(DadosAtualizacaoEndereco::id).collect(Collectors.toList());
        List<Endereco> enderecos = enderecoRepository.findAllByPessoaIdAndIdIn(dados.id(), enderecoIds);

        for (Endereco endereco : enderecos) {
            for (DadosAtualizacaoEndereco dadosEndereco : dados.enderecos()) {
                if (endereco.getId().equals(dadosEndereco.id())) {
                    endereco.atualizarInformacoes(dadosEndereco);
                    break;
                }
            }
        }
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
        List<DadosListagemEndereco> enderecosDTO = enderecoRepository.findAllByPessoaId(id).stream().map(DadosListagemEndereco::new).toList();
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhamentoPacienteEndereco(paciente, enderecosDTO));
    }

    //    @GetMapping("/{id}")
//    public ResponseEntity detalhar(@PathVariable Long id) {
//
//        //List<Endereco> enderecos = enderecoRepository.findAllByPessoaId(id);
//        var paciente = repository.getReferenceById(id);
//        return ResponseEntity.ok(new DadosDetalhamentoPaciente (paciente));
//    }

}
