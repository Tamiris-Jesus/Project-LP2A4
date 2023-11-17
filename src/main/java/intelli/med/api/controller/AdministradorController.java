package intelli.med.api.controller;

import intelli.med.api.domain.administrador.*;
import intelli.med.api.domain.endereco.DadosListagemEndereco;
import intelli.med.api.domain.paciente.DetalhamentoPacienteEndereco;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import intelli.med.api.domain.endereco.DadosEndereco;
import intelli.med.api.domain.endereco.Endereco;
import intelli.med.api.domain.endereco.EnderecoRepository;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("administradores")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdministradorController {

    @Autowired
    private AdministradorRepository repository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @PostMapping
    @Transactional
    // É uma convenção de API Rest retornar a uri de um recurso criado
    public ResponseEntity<DadosDetalhamentoAdministrador> cadastrar(@RequestBody @Valid DadosCadastroAdministrador dados, UriComponentsBuilder uriBuilder) {
        var administrador = new Administrador(dados);
        repository.save(administrador);
        for (DadosEndereco dadosEndereco : dados.enderecos()) {
            Endereco endereco = new Endereco(administrador, dadosEndereco);
            enderecoRepository.save(endereco);
        }
        var uri = uriBuilder.path("/administradores/{id}").buildAndExpand(administrador.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoAdministrador(administrador));
    }
    @GetMapping
    public ResponseEntity<List<DadosListagemAdministrador>> listar() {
        var page = repository.findAllByAtivoTrue().stream().map(DadosListagemAdministrador::new).toList();
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var administrador = repository.getReferenceById(id);
        administrador.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        List<DadosListagemEndereco> enderecosDTO = enderecoRepository.findAllByPessoaId(id).stream().map(DadosListagemEndereco::new).toList();
        var administrador = repository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhamentoAdministradorEndereco(administrador, enderecosDTO));
    }

}
