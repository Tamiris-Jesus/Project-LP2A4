package intelli.med.api.domain.administrador;

import intelli.med.api.domain.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "administradores")
@Entity(name = "Administrador")
@Setter
@Getter
@NoArgsConstructor
public class Administrador extends Pessoa {

    private String cpf;
    private String registro;


    public Administrador(String cpf, String registro) {
        this.cpf = cpf;
        this.registro = registro;
    }


    public Administrador(DadosCadastroAdministrador dados){
        this.setAtivo(true);
        this.setNome(dados.nome());
        this.setEmail(dados.email());
        this.setTelefone(dados.telefone());
        this.setCpf(dados.cpf());
        this.setRegistro(dados.registro());
//        this.setEnderecos(dados.enderecos().stream().map(Endereco::new).toList());
    }

    public void atualizarInformacoes(DadosAtualizacaoAdministrador dados) {
        if (dados.nome() != null) {
            this.setNome(dados.nome());
        }
        if (dados.telefone() != null) {
            this.setTelefone(dados.telefone());
        }
        if (dados.endereco() != null) {
//            this.getEnderecos().forEach(endereco -> endereco.atualizarInformacoes(dados.endereco()));
        }

    }

    public void excluir() {
        this.setAtivo(false);
    }


}
