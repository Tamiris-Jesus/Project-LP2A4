package intelli.med.api.domain.paciente;

import intelli.med.api.domain.endereco.Endereco;

import java.util.List;

public record DetalhamentoPacienteEndereco (Long id, String nome, String email, String cpf, String telefone, List<Endereco> enderecos){
    public DetalhamentoPacienteEndereco(Paciente paciente, List<Endereco> enderecos) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(), enderecos);
    }
}
