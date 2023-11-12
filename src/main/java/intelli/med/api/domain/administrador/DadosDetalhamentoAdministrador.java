package intelli.med.api.domain.administrador;

import intelli.med.api.domain.endereco.Endereco;

import java.util.List;

public record DadosDetalhamentoAdministrador(Long id, String nome, String email, String cpf, String telefone, String registro /*, List<Endereco> enderecos*/) {
    public DadosDetalhamentoAdministrador(Administrador administrador) {
        this(administrador.getId(), administrador.getNome(), administrador.getEmail(), administrador.getCpf(), administrador.getTelefone(), administrador.getRegistro() /*, administrador.getEnderecos()*/);
    }
}
