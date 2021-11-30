package com.daluga.mds.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

public class Importancia {
    private Long id;
    private String importancia;

    public Importancia(Long id, String importancia) {
        this.id = id;
        this.importancia = importancia;
    }

    public Importancia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImportancia() {
        return importancia;
    }

    public void setImportancia(String importancia) {
        this.importancia = importancia;
    }

    @Override
    public String toString() {
        return "Importancia{" +
                "id=" + id +
                ", importancia='" + importancia + '\'' +
                '}';
    }
}
