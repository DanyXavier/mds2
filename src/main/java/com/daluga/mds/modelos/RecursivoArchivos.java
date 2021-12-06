package com.daluga.mds.modelos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public class RecursivoArchivos extends RecursiveTreeObject<RecursivoArchivos> {
    private LongProperty id;
    private StringProperty archivo;
    private StringProperty ubicacionFisica;
    private StringProperty descripcion;
    private StringProperty nota;
    private StringProperty fechaCreacion;
    private StringProperty fechaSubida;
    private StringProperty importancia;
    private StringProperty area;
    private BooleanProperty estado;

    public RecursivoArchivos(LongProperty id, StringProperty archivo, StringProperty ubicacionFisica, StringProperty descripcion, StringProperty nota, StringProperty fechaCreacion, StringProperty fechaSubida, StringProperty importancia, StringProperty area, BooleanProperty estado) {
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
    }

    public RecursivoArchivos() {
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getArchivo() {
        return archivo.get();
    }

    public StringProperty archivoProperty() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo.set(archivo);
    }

    public String getUbicacionFisica() {
        return ubicacionFisica.get();
    }

    public StringProperty ubicacionFisicaProperty() {
        return ubicacionFisica;
    }

    public void setUbicacionFisica(String ubicacionFisica) {
        this.ubicacionFisica.set(ubicacionFisica);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getNota() {
        return nota.get();
    }

    public StringProperty notaProperty() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota.set(nota);
    }

    public String getFechaCreacion() {
        return fechaCreacion.get();
    }

    public StringProperty fechaCreacionProperty() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion.set(fechaCreacion);
    }

    public String getFechaSubida() {
        return fechaSubida.get();
    }

    public StringProperty fechaSubidaProperty() {
        return fechaSubida;
    }

    public void setFechaSubida(String fechaSubida) {
        this.fechaSubida.set(fechaSubida);
    }

    public String getImportancia() {
        return importancia.get();
    }

    public StringProperty importanciaProperty() {
        return importancia;
    }

    public void setImportancia(String importancia) {
        this.importancia.set(importancia);
    }

    public String getArea() {
        return area.get();
    }

    public StringProperty areaProperty() {
        return area;
    }

    public void setArea(String area) {
        this.area.set(area);
    }

    public boolean isEstado() {
        return estado.get();
    }

    public BooleanProperty estadoProperty() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado.set(estado);
    }

    @Override
    public String toString() {
        return "RecursivoArchivos{" +
                "id=" + id +
                ", archivo=" + archivo +
                ", ubicacionFisica=" + ubicacionFisica +
                ", descripcion=" + descripcion +
                ", nota=" + nota +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaSubida=" + fechaSubida +
                ", importancia=" + importancia +
                ", area=" + area +
                ", estado=" + estado +
                '}';
    }
}
