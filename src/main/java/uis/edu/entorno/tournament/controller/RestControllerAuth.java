package uis.edu.entorno.tournament.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uis.edu.entorno.tournament.dtos.DtoAuthRespuesta;
import uis.edu.entorno.tournament.dtos.DtoLogin;
import uis.edu.entorno.tournament.dtos.DtoRegistro;
import uis.edu.entorno.tournament.model.Roles;
import uis.edu.entorno.tournament.model.Usuario;
import uis.edu.entorno.tournament.repository.IRolesRepository;
import uis.edu.entorno.tournament.repository.IUsuarioRepository;
import uis.edu.entorno.tournament.security.JwtGenerador;

import java.util.Collections;

@Controller
@RequestMapping("/auth")
public class RestControllerAuth {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final IRolesRepository rolesRepository;
    private final IUsuarioRepository usuarioRepository;
    private final JwtGenerador jwtGenerador;

    @Autowired
    public RestControllerAuth(AuthenticationManager authenticationManager,
                              PasswordEncoder passwordEncoder,
                              IRolesRepository rolesRepository,
                              IUsuarioRepository usuarioRepository,
                              JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.usuarioRepository = usuarioRepository;
        this.jwtGenerador = jwtGenerador;
    }

    @PostMapping("register")
    public ResponseEntity<DtoAuthRespuesta> registrar(@RequestBody DtoRegistro dtoRegistro) {
        // Verifica si el nombre de usuario ya existe
        if (usuarioRepository.existsByUsername(dtoRegistro.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new DtoAuthRespuesta("Usuario existente, intenta con otro"));
        }

        // Crea un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(dtoRegistro.getUsername());
        usuario.setEmail(dtoRegistro.getEmail());
        usuario.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));

        // Busca el rol "USER"
        Roles roles = rolesRepository.findByName("USER").orElse(null);
        if (roles == null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DtoAuthRespuesta("Rol 'USER' no encontrado"));
        }

        usuario.setRoles(Collections.singletonList(roles));

        // Guarda el nuevo usuario
        try {
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DtoAuthRespuesta("Error al registrar usuario: " + e.getMessage()));
        }

        // Autenticación automática y generación de token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dtoRegistro.getUsername(), dtoRegistro.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerador.generarToken(authentication);
        System.out.println("Token generado: " + token);

        return ResponseEntity.ok(new DtoAuthRespuesta(token));
    }

    @PostMapping("login")
    public ResponseEntity<DtoAuthRespuesta> login(@RequestBody DtoLogin dtoLogin) {
        // Autenticación del usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dtoLogin.getUsername(), dtoLogin.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generación de token
        String token = jwtGenerador.generarToken(authentication);
        return ResponseEntity.ok(new DtoAuthRespuesta(token));
    }

    // Métodos para manejar las vistas de registro y login
    @GetMapping("register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("dtoRegistro", new DtoRegistro());
        return "register"; // Retorna la vista de registro
    }

    @GetMapping("login")
    public String mostrarLogin(Model model) {
        model.addAttribute("dtoLogin", new DtoLogin());
        return "login"; // Retorna la vista de login
    }

    // Puedes añadir métodos adicionales para manejar la lógica de tu aplicación
}

