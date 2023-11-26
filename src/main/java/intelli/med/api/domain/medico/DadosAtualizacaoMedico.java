package intelli.med.api.domain.medico;

import intelli.med.api.domain.endereco.DadosAtualizacaoEndereco;
import intelli.med.api.domain.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String email,
        String telefone,
        @Valid List<DadosAtualizacaoEndereco> enderecos) {
}
