package uis.edu.entorno.tournament.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uis.edu.entorno.tournament.model.Pruebas;
import uis.edu.entorno.tournament.repository.PruebasRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PruebasService {

    @Autowired
    private PruebasRepository pruebasRepository;

    public List<Pruebas> obtenerTodas() {
        return pruebasRepository.findAll();
    }

    public Optional<Pruebas> obtenerPorId(int id) {
        return pruebasRepository.findById(id);
    }

    public Pruebas guardarOActualizar(Pruebas prueba) {
        return pruebasRepository.save(prueba);
    }

    public void eliminarPorId(int id) {
        pruebasRepository.deleteById(id);
    }
}
