package uis.edu.entorno.tournament.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uis.edu.entorno.tournament.modelo.usuario;

@Repository
public interface usuarioRepo extends JpaRepository<usuario, Long> {
}
