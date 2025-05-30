package com.ticarum.hospital_camas.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Cama {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private EstadoCama estado;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "dependencia_id")
    private Dependencia dependencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoCama getEstado() {
        return estado;
    }

    public void setEstado(EstadoCama estado) {
        this.estado = estado;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }
}