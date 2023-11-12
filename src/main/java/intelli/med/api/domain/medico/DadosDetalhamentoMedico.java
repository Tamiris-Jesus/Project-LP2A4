package intelli.med.api.domain.medico;

import intelli.med.api.domain.endereco.Endereco;

import java.util.List;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone, Especialidade especialidade /* List<Endereco> enderecos*/) {

    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade() /*medico.getEnderecos()*/);
    }
}
