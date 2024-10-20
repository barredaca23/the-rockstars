package uis.edu.entorno.tournament.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AdminController {

    @GetMapping("/admin/pruebas")
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    public String dashboard() {
        return "Bienvenido al panel de administración";
    }
}