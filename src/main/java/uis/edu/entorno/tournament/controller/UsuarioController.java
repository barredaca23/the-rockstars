package uis.edu.entorno.tournament.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import uis.edu.entorno.tournament.service.UsuarioService;

@Controller
@RequestMapping("/")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("form-list")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodas());
        return "usuario-list";
    }

    @GetMapping
    public String mostrarPaginaInicio() {
        return "index";
    }
}