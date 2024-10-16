package uis.edu.entorno.tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uis.edu.entorno.tournament.model.Roles;

import java.util.Optional;

@Repository
public interface IRolesRepository  extends JpaRepository<Roles, Long> {

    Optional<Roles> findByName(String name);
}
