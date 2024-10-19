package uis.edu.entorno.tournament.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uis.edu.entorno.tournament.model.Pruebas;
import uis.edu.entorno.tournament.service.PruebasService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pruebas")
public class PruebasController {

    @Autowired
    private PruebasService pruebasService;

    // Obtener todas las pruebas (accesible para cualquier usuario)
    @GetMapping
    public List<Pruebas> getAllPruebas() {
        return pruebasService.obtenerTodas();
    }

    // Obtener una prueba por su ID (accesible para cualquier usuario)
    @GetMapping("/{id}")
    public ResponseEntity<Pruebas> getPruebaById(@PathVariable(name = "id") Integer id) {
        Optional<Pruebas> prueba = pruebasService.obtenerPorId(id);
        return prueba.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear una nueva prueba (solo usuarios con rol de USER)
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Pruebas> crearPrueba(@RequestBody Pruebas prueba) {
        Pruebas nuevaPrueba = pruebasService.guardarOActualizar(prueba);
        return new ResponseEntity<>(nuevaPrueba, HttpStatus.CREATED);
    }

    // Actualizar una prueba existente (solo usuarios con rol de USER)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Pruebas> actualizarPrueba(@PathVariable(name = "id") Integer id, @RequestBody Pruebas prueba) {
        Optional<Pruebas> pruebaExistente = pruebasService.obtenerPorId(id);

        if (pruebaExistente.isPresent()) {
            prueba.setId(id);  // Asegúrate de actualizar el ID correctamente
            Pruebas pruebaActualizada = pruebasService.guardarOActualizar(prueba);
            return new ResponseEntity<>(pruebaActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una prueba por su ID (solo usuarios con rol de USER)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> eliminarPrueba(@PathVariable(name = "id") Integer id) {
        Optional<Pruebas> prueba = pruebasService.obtenerPorId(id);

        if (prueba.isPresent()) {
            pruebasService.eliminarPorId(id);
            return new ResponseEntity<>("Prueba eliminada con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Prueba no encontrada", HttpStatus.NOT_FOUND);
        }
    }
}
