package uis.edu.entorno.tournament.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uis.edu.entorno.tournament.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
