package com.daluga.mds.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

public class Archivos {
    private Long id;
    private String archivo;
    private String ubicacionFisica;
    private String descripcion;
    private String nota;
    private String fechaCreacion;
    private String fechaSubida;
    private Importancia importancia;
    private Areas area;
    private Boolean estado;
    private Directorios directorio;

    public Archivos(Long id, String archivo, String ubicacionFisica, String descripcion, String nota, String fechaCreacion, String fechaSubida, Importancia importancia, Areas area, Boolean estado, Directorios directorio) {
        this.id = id;
        this.archivo = archivo;
        this.ubicacionFisica = ubicacionFisica;
        this.descripcion = descripcion;
        this.nota = nota;
        this.fechaCreacion = fechaCreacion;
        this.fechaSubida = fechaSubida;
        this.importancia = importancia;
        this.area = area;
        this.estado = estado;
        this.directorio = directorio;
    }

    public Archivos() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getUbicacionFisica() {
        return ubicacionFisica;
    }

    public void setUbicacionFisica(String ubicacionFisica) {
        this.ubicacionFisica = ubicacionFisica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(String fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public Importancia getImportancia() {
        return importancia;
    }

    public void setImportancia(Importancia importancia) {
        this.importancia = importancia;
    }

    public Areas getArea() {
        return area;
    }

    public void setArea(Areas area) {
        this.area = area;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Directorios getDirectorio() {
        return directorio;
    }

    public void setDirectorio(Directorios directorio) {
        this.directorio = directorio;
    }

    @Override
    public String toString() {
        return "Archivos{" +
                "id=" + id +
                ", archivo='" + archivo + '\'' +
                ", ubicacionFisica='" + ubicacionFisica + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nota='" + nota + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaSubida=" + fechaSubida +
                ", importancia=" + importancia +
                ", area=" + area +
                ", estado=" + estado +
                ", directorio=" + directorio +
                '}';
    }
}
