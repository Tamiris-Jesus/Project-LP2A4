package intelli.med.api.domain.consulta;

import intelli.med.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosListagemConsulta(String nomeMedico, Especialidade especialidade, String nomePaciente, String cpf, LocalDateTime data) {
        public DadosListagemConsulta(Consulta consulta) {
            this(consulta.getMedico().getNome(), consulta.getMedico().getEspecialidade(), consulta.getPaciente().getNome(),
                    consulta.getPaciente().getCpf(), consulta.getData());
        }
    }


