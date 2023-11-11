package intelli.med.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import intelli.med.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
