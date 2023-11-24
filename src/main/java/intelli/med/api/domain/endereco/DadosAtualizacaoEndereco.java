package intelli.med.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoEndereco(
        @NotNull
        Long id,
        String logradouro,
        String bairro,
        //@Pattern(regexp = "\\d{9}")
        String cep,
        String cidade,
        String uf,
        String complemento,
        String numero){
}
