package uis.edu.entorno.tournament.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("dtoRegistro", new DtoRegistro());
        return "register";
    }

    @PostMapping("/register")
    public String registrar(@ModelAttribute("dtoRegistro") DtoRegistro dtoRegistro, Model model) {
        if (usuarioRepository.existsByUsername(dtoRegistro.getUsername())) {
            model.addAttribute("message", "Usuario existente, intenta con otro");
            return "register";
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(dtoRegistro.getUsername());
        usuario.setEmail(dtoRegistro.getEmail());
        usuario.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));

        Roles roles = rolesRepository.findByName("ADMIN").orElse(null);
        if (roles == null) {
            model.addAttribute("message", "Rol 'USER' no encontrado");
            return "register";
        }
        usuario.setRoles(Collections.singletonList(roles));
        try {
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            model.addAttribute("message", "Error al registrar usuario: " + e.getMessage());
            return "register";
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dtoRegistro.getUsername(), dtoRegistro.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/auth/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("dtoLogin") DtoLogin dtoLogin, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dtoLogin.getUsername(), dtoLogin.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerador.generarToken(authentication);
            return "redirect:/auth/user/pruebas";
        } catch (Exception e) {
            model.addAttribute("message", "Credenciales incorrectas, intenta de nuevo.");
            return "login";
        }
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("dtoLogin", new DtoLogin());
        return "login";
    }

}

