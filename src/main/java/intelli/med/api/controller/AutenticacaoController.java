package intelli.med.api.controller;

import jakarta.validation.Valid;
import intelli.med.api.domain.usuario.DadosAutenticacao;
import intelli.med.api.domain.usuario.Usuario;
import intelli.med.api.domain.usuario.UsuarioRepository;
import intelli.med.api.infra.security.DadosTokenJWT;
import intelli.med.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AutenticacaoController {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody DadosAutenticacao dados){
        if(this.repository.findByLogin(dados.login()) != null){
            return ResponseEntity.badRequest().build();
        }
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.senha());
        Usuario novoUsuario = new Usuario(dados.login(), senhaCriptografada);
        this.repository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }

}
