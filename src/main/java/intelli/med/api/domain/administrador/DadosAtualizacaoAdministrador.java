package intelli.med.api.domain.administrador;

import jakarta.validation.constraints.NotNull;
import intelli.med.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoAdministrador(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco
) {
}
