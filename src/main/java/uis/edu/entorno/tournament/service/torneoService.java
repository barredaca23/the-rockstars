package uis.edu.entorno.tournament.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uis.edu.entorno.tournament.model.torneo;
import uis.edu.entorno.tournament.repository.torneoRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class torneoService {
    @Autowired
    private torneoRepositorio torneoRepositorio;

    public torneo guardarTorneo(torneo torneo) {
        return torneoRepositorio.save(torneo);
    }

    public List<torneo> listarTodos() {
        return torneoRepositorio.findAll();
    }

    public Optional<torneo> obtenerTorneoPorId(Long id) {
        return torneoRepositorio.findById(id);
    }

    public void eliminarTorneo(Long id) {
        torneoRepositorio.deleteById(id);
    }
}
