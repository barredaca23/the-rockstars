package uis.edu.entorno.tournament.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import uis.edu.entorno.tournament.model.Usuario;
import uis.edu.entorno.tournament.service.UsuarioService;
/*
@Controller
@RequestMapping("/")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/form-list")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodas());
        return "usuario-list";
    }

    @GetMapping("/")
    public String mostrarPaginaInicio() {
        return "index";
    }

    @GetMapping("/register")
    public String mostrarFormularioNuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario-form";
    }

    @PostMapping("/register")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        return "usuario-form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }
}*/