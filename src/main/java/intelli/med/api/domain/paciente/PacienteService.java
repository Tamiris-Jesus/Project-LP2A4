package intelli.med.api.domain.paciente;

import intelli.med.api.domain.endereco.EnderecoRepository;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    private PacienteRepository repository;
    private EnderecoRepository enderecoRepository;
}
