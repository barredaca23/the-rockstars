package uis.edu.entorno.tournament.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uis.edu.entorno.tournament.dtos.DtoAuthRespuesta;
import uis.edu.entorno.tournament.dtos.DtoLogin;
import uis.edu.entorno.tournament.dtos.DtoRegistro;
import uis.edu.entorno.tournament.model.Roles;
import uis.edu.entorno.tournament.model.Usuario;
import uis.edu.entorno.tournament.repository.IRolesRepository;
import uis.edu.entorno.tournament.repository.IUsuarioRepository;
import uis.edu.entorno.tournament.security.JwtGenerador;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class RestControllerAuth {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRolesRepository rolesRepository;
    private IUsuarioRepository usuarioRepository;
    private JwtGenerador jwtGenerador;
    @Autowired

    public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRolesRepository rolesRepository, IUsuarioRepository usuarioRepository, JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.usuarioRepository = usuarioRepository;
        this.jwtGenerador = jwtGenerador;
    }

    @PostMapping("register")
    public ResponseEntity<DtoAuthRespuesta> registrar(@RequestBody DtoRegistro dtoRegistro) {
        if (usuarioRepository.existsByUsername(dtoRegistro.getUsername())) {
            return new ResponseEntity<>(new DtoAuthRespuesta("Usuario existente intenta con otro"), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(dtoRegistro.getUsername());
        usuario.setEmail(dtoRegistro.getEmail());
        usuario.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));

        Roles roles = rolesRepository.findByName("USER").orElse(null);
        if (roles == null) {
            return new ResponseEntity<>(new DtoAuthRespuesta("Rol 'USER' no encontrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        usuario.setRoles(Collections.singletonList(roles));

        try {
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            return new ResponseEntity<>(new DtoAuthRespuesta("Error al registrar usuario: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dtoRegistro.getUsername(), dtoRegistro.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerador.generarToken(authentication);
        System.out.println("Token generado: " + token);

        return new ResponseEntity<>(new DtoAuthRespuesta(token), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<DtoAuthRespuesta> login(@RequestBody DtoLogin dtoLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.getUsername(), dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerador.generarToken(authentication);
        return new ResponseEntity<>(new DtoAuthRespuesta(token), HttpStatus.OK);
    }
}
