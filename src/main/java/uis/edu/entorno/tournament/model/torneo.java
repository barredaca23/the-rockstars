package uis.edu.entorno.tournament.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "torneo")
public class torneo {
    public static final String TABLE_NAME = "torneo";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_torneo")
    private Long id_torneo;

    private String nombreTorneo;
    private LocalDate fecha;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tipoTorneo",referencedColumnName = "id_tipoTorneo")
    private tipoTorneo tipoTorneo;

    public torneo(Long id_torneo, LocalDate fecha, uis.edu.entorno.tournament.model.tipoTorneo tipoTorneo) {
        this.id_torneo = id_torneo;
        this.fecha = fecha;
        this.tipoTorneo = tipoTorneo;
    }

    public torneo() {

    }

    public Long getId_torneo() {
        return id_torneo;
    }

    public void setId_torneo(Long id_torneo) {
        this.id_torneo = id_torneo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public uis.edu.entorno.tournament.model.tipoTorneo getTipoTorneo() {
        return tipoTorneo;
    }

    public void setTipoTorneo(uis.edu.entorno.tournament.model.tipoTorneo tipoTorneo) {
        this.tipoTorneo = tipoTorneo;
    }
}
