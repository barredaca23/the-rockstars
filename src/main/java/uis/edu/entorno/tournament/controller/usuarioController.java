package uis.edu.entorno.tournament.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uis.edu.entorno.tournament.modelo.usuario;
import uis.edu.entorno.tournament.service.usuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class usuarioController {
    @Autowired
    private usuarioService usuarioService;

    @GetMapping
    public List<usuario> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<usuario> obtenerUsuario(@PathVariable Long id) {
        usuario usuario = usuarioService.obtenerPorId(id);
        if(usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public usuario crearUsuario(@RequestBody usuario usuario) {
        return usuarioService.guardar(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<usuario> actualizarUsuario(@PathVariable Long id, @RequestBody usuario usuario) {
        usuario usuarioActual = usuarioService.obtenerPorId(id);
        if(usuario != null) {
            usuario.setUsername(usuarioActual.getUsername());
            usuario.setEmail(usuarioActual.getEmail());
            usuario usuarioActual2 = usuarioService.guardar(usuario);
            return new ResponseEntity<>(usuarioActual2, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuario usuario = usuarioService.obtenerPorId(id);
        if(usuario != null) {
            usuarioService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
