package intelli.med.api.domain.paciente;

import intelli.med.api.domain.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.*;
import intelli.med.api.domain.endereco.Endereco;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@Setter
@NoArgsConstructor
public class Paciente extends Pessoa {

    private String cpf;

    public Paciente(DadosCadastroPaciente dados) {
        this.setAtivo(true);
        this.setNome(dados.nome());
        this.setEmail(dados.email());
        this.setTelefone(dados.telefone());
        this.setCpf(dados.cpf());
//        this.setEnderecos(dados.enderecos().stream().map(Endereco::new).toList());

    }


    public void adicionaEndereco(Endereco endereco){

    }
    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        if (dados.nome() != null) {
            this.setNome(dados.nome());
        }
        if (dados.telefone() != null) {
            this.setTelefone(dados.telefone());
        }
        if (dados.email() != null) {
            this.setEmail(dados.email());
        }


    }

    public void excluir() {
        this.setAtivo(false);
    }
}
