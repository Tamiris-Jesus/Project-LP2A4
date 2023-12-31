package intelli.med.api.controller;

import intelli.med.api.domain.endereco.*;
import intelli.med.api.domain.medico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @PostMapping
    @Transactional
    // É uma convenção de API Rest retornar a uri de um recurso criado
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        repository.save(medico);
        for (DadosEndereco dadosEndereco : dados.enderecos()) {
            Endereco endereco = new Endereco(medico, dadosEndereco);
            enderecoRepository.save(endereco);
        }

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemMedico>> listar() {
        var page = repository.findAllByAtivoTrue().stream().map(DadosListagemMedico::new).toList();
        return ResponseEntity.ok(page);
    }

    @PostMapping("/atualizar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        for (DadosAtualizacaoEndereco dadosEndereco : dados.enderecos()) {
            // Se o ID do endereço não estiver presente, é um novo endereço adicional
            if (dadosEndereco.id() == null || dadosEndereco.id().toString().isEmpty()) {
                Endereco novoEndereco = new Endereco(medico, dadosEndereco);
                enderecoRepository.save(novoEndereco);
            } else {
                // Se o ID do endereço estiver presente, é uma atualização do endereço existente
                Endereco enderecoExistente = enderecoRepository.findById(dadosEndereco.id()).get();
                enderecoExistente.atualizarInformacoes(dadosEndereco);
            }
        }
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        List<DadosListagemEndereco> enderecosDTO = enderecoRepository.findAllByPessoaId(id).stream().map(DadosListagemEndereco::new).toList();
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhamentoMedicoEndereco(medico, enderecosDTO));
    }
}
