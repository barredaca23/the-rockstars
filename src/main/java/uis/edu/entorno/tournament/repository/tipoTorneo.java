package uis.edu.entorno.tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uis.edu.entorno.tournament.model.torneo;

public interface tipoTorneo extends JpaRepository<torneo, Long> {
}
