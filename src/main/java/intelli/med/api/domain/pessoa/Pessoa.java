package intelli.med.api.domain.pessoa;

import jakarta.persistence.*;
import lombok.*;
import intelli.med.api.domain.endereco.Endereco;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pessoa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private boolean ativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoa", orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    public Pessoa(String nome, String email, String telefone, boolean ativo) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.ativo = ativo;
    }

//    public void setEnderecos(List<Endereco> enderecos) {
//        this.enderecos = enderecos;
//    }
}
