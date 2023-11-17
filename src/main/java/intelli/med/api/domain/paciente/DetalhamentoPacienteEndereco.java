package intelli.med.api.domain.paciente;

import intelli.med.api.domain.endereco.DadosListagemEndereco;
import java.util.List;

public record DetalhamentoPacienteEndereco (Long id, String nome, String email, String cpf, String telefone, List<DadosListagemEndereco> enderecosDTO){
    public DetalhamentoPacienteEndereco(Paciente paciente, List<DadosListagemEndereco> enderecosDTO) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(), enderecosDTO);
    }
}
