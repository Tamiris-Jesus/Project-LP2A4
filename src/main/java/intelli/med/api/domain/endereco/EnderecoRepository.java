package intelli.med.api.domain.endereco;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco,Long> {

    List<Endereco> findAllByPessoaId(Long id);

    List<Endereco> findAllByPessoaIdAndIdIn(Long pacienteId, List<Long> enderecoIds);
}
