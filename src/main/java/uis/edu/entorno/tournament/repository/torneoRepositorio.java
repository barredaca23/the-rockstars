package uis.edu.entorno.tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uis.edu.entorno.tournament.model.torneo;

public interface torneoRepositorio extends JpaRepository<torneo, Long> {
}
