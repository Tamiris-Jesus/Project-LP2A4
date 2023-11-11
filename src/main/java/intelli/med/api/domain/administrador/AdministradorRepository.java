package intelli.med.api.domain.administrador;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdministradorRepository extends JpaRepository<Administrador,Long> {
    List<Administrador> findAllByAtivoTrue();

}
