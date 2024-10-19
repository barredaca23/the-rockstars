package uis.edu.entorno.tournament.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pruebas")
public class Pruebas {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pruebas")
    private int id;
    private String nombre;
    private String descripcion;

    // Constructor vacío
    public Pruebas() {
    }

    // Constructor con parámetros
    public Pruebas(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}

