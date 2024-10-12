package uis.edu.entorno.tournament.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uis.edu.entorno.tournament.modelo.usuario;
import uis.edu.entorno.tournament.repositorio.usuarioRepo;

import java.util.List;

@Service
public class usuarioService {

    @Autowired
    private usuarioRepo usuarioRepo;

    public List<usuario> listarTodos() {
        return usuarioRepo.findAll();
    }

    public usuario obtenerPorId(Long id) {
        return usuarioRepo.findById(id).orElse(null);
    }

    public usuario guardar(usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    public void eliminar(Long id) {
        usuarioRepo.deleteById(id);
    }

}
