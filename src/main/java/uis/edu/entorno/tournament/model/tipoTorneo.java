package uis.edu.entorno.tournament.model;

import jakarta.persistence.*;

@Entity
public class tipoTorneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipoTorneo")
    private Long idTipoTorneo;

    private int edad;
    private int jugadoresPorEquipo;

    public Long getIdTipoTorneo() {
        return idTipoTorneo;
    }

    public void setIdTipoTorneo(Long idTipoTorneo) {
        this.idTipoTorneo = idTipoTorneo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getJugadoresPorEquipo() {
        return jugadoresPorEquipo;
    }

    public void setJugadoresPorEquipo(int jugadoresPorEquipo) {
        this.jugadoresPorEquipo = jugadoresPorEquipo;
    }
}
