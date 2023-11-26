package intelli.med.api.domain.medico;

import intelli.med.api.domain.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medico extends Pessoa {

    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    public Medico(DadosCadastroMedico dados) {
        this.setAtivo(true);
        this.setNome(dados.nome());
        this.setEmail(dados.email());
        this.setTelefone(dados.telefone());
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
//        this.setEnderecos(dados.enderecos().stream().map(Endereco::new).toList());
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
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
