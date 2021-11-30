package com.daluga.mds.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Set;

public class Directorios {
    private Long id;
    private String directorio;
    private List<Archivos> archivos;

    public Directorios(Long id, String directorio, List<Archivos> archivos) {
        this.id = id;
        this.directorio = directorio;
        this.archivos = archivos;
    }

    public Directorios() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDirectorio() {
        return directorio;
    }

    public void setDirectorio(String directorio) {
        this.directorio = directorio;
    }

    public List<Archivos> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<Archivos> archivos) {
        this.archivos = archivos;
    }

    @Override
    public String toString() {
        return "Directorios{" +
                "id=" + id +
                ", directorio='" + directorio + '\'' +
                ", archivos=" + archivos +
                '}';
    }
}
