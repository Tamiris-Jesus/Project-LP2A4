package intelli.med.api.domain.administrador;

import intelli.med.api.domain.endereco.DadosListagemEndereco;
import java.util.List;

public record DetalhamentoAdministradorEndereco(Long id, String nome, String email, String cpf, String telefone, String registro, List<DadosListagemEndereco> enderecosDTO){
    public DetalhamentoAdministradorEndereco(Administrador administrador, List<DadosListagemEndereco> enderecosDTO) {
        this(administrador.getId(), administrador.getNome(), administrador.getEmail(), administrador.getCpf(), administrador.getTelefone(), administrador.getRegistro(), enderecosDTO);
    }
}
