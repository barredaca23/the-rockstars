package uis.edu.entorno.tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uis.edu.entorno.tournament.model.Pruebas;

public interface PruebasRepository extends JpaRepository<Pruebas, Integer> {
}
