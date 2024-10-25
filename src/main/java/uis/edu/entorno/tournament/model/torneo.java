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

    @Column(name = "nombre_torneo")
    private String nombre_torneo;

    @Column(name = "fecha")
    private LocalDate fecha;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tipoTorneo",referencedColumnName = "id_tipoTorneo")
    private tipoTorneo tipoTorneo;

    public torneo(Long id_torneo, LocalDate fecha, uis.edu.entorno.tournament.model.tipoTorneo tipoTorneo, String nombre_torneo) {
        this.id_torneo = id_torneo;
        this.fecha = fecha;
        this.tipoTorneo = tipoTorneo;
        this.nombre_torneo = nombre_torneo;
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

    public String getNombre_torneo() {return nombre_torneo;}

    public void setNombre_torneo(String nombre_torneo) {this.nombre_torneo = nombre_torneo;}
}
