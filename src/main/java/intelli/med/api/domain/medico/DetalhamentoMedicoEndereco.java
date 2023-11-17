package intelli.med.api.domain.medico;

import intelli.med.api.domain.endereco.DadosListagemEndereco;
import java.util.List;

public record DetalhamentoMedicoEndereco(Long id, String nome, String email, String crm, String telefone, Especialidade especialidade, List<DadosListagemEndereco> enderecosDTO){
    public DetalhamentoMedicoEndereco(Medico medico, List<DadosListagemEndereco> enderecosDTO) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), enderecosDTO);
    }
}
