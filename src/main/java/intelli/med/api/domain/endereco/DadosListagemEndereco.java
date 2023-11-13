package intelli.med.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;

public record DadosListagemEndereco(String logradouro, String bairro,  String cep,  String cidade, String uf,  String complemento, String numero) {
    public DadosListagemEndereco(Endereco endereco){
       this(endereco.getLogradouro(), endereco.getBairro(), endereco.getCep(), endereco.getCidade(), endereco.getUf(), endereco.getComplemento(), endereco.getNumero());
    }
}



