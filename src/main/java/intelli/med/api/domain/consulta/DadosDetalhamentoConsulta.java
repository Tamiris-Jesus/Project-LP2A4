package intelli.med.api.domain.consulta;

import intelli.med.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, String nomeMedico, Especialidade especialidade, String nomePaciente, String cpf, LocalDateTime data) {
    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getNome(), consulta.getMedico().getEspecialidade(), consulta.getPaciente().getNome(),
                consulta.getPaciente().getCpf(), consulta.getData());
    }
}

