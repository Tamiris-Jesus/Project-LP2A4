package intelli.med.api.domain.administrador;


public record DadosListagemAdministrador(Long id, String nome, String email, String cpf, String registro) {
    public DadosListagemAdministrador(Administrador administrador){
        this(administrador.getId(), administrador.getNome(), administrador.getEmail(), administrador.getCpf(), administrador.getRegistro());

    }
}
