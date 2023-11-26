package intelli.med.api.domain.paciente;

import intelli.med.api.domain.endereco.DadosAtualizacaoEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import intelli.med.api.domain.endereco.DadosEndereco;

import java.util.List;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        @Valid List<DadosAtualizacaoEndereco> enderecos) {
}
