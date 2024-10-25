package uis.edu.entorno.tournament.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AdminController {

    @GetMapping("/admin/admin-in")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String dashboard() {
        return "admin-in";
    }

    @GetMapping("/sobreNosotros")
    public String sobreNosotros() {
        return "sobreNosotros";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/admin/torneosCrud")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String torneos() {
        return "torneosCrud"; // Esto busca el archivo torneos.html en templates
    }
}