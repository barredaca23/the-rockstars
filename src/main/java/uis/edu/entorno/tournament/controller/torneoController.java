package uis.edu.entorno.tournament.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uis.edu.entorno.tournament.model.torneo;
import uis.edu.entorno.tournament.service.torneoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/torneos")
public class torneoController {




        @Autowired
        private torneoService torneoService;

        // 1. Crear un nuevo torneo (POST)
        @PostMapping
        public ResponseEntity<torneo> crearTorneo(@RequestBody torneo torneo) {
            torneo nuevoTorneo = torneoService.guardarTorneo(torneo);
            return ResponseEntity.ok(nuevoTorneo);
        }

        // 2. Obtener todos los torneos (GET)
        @GetMapping
        public List<torneo> listarTorneos() {
            return torneoService.listarTodos();
        }

        // 3. Obtener un torneo por ID (GET)
        @GetMapping("/{id}")
        public ResponseEntity<torneo> obtenerTorneoPorId(@PathVariable Long id) {
            Optional<torneo> torneo = torneoService.obtenerTorneoPorId(id);
            return torneo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        // 4. Actualizar un torneo (PUT)
        @PutMapping("/{id}")
        public ResponseEntity<torneo> actualizarTorneo(@PathVariable Long id, @RequestBody torneo detallesTorneo) {
            Optional<torneo> torneoExistente = torneoService.obtenerTorneoPorId(id);

            if (torneoExistente.isPresent()) {
                torneo torneoActualizado = torneoExistente.get();
                torneoActualizado.setFecha(detallesTorneo.getFecha());
                torneoActualizado.setTipoTorneo(detallesTorneo.getTipoTorneo()); // Actualizar la relación TipoTorneo

                torneoService.guardarTorneo(torneoActualizado);
                return ResponseEntity.ok(torneoActualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        // 5. Eliminar un torneo (DELETE)
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarTorneo(@PathVariable Long id) {
            Optional<torneo> torneo = torneoService.obtenerTorneoPorId(id);
            if (torneo.isPresent()) {
                torneoService.eliminarTorneo(id);
                return ResponseEntity.noContent().build();  // Retorna 204 si se elimina correctamente
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    

}
