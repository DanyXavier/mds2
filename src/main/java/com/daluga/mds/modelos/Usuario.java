package com.daluga.mds.modelos;

public class Usuario {
    private Long id;
    private String usuario;
    private String clave;
    private Boolean autorizado;
    private Boolean activo;

    public Usuario(Long id, String usuario, String clave, Boolean autorizado, Boolean activo) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
        this.autorizado = autorizado;
        this.activo = activo;
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Boolean autorizado) {
        this.autorizado = autorizado;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", clave='" + clave + '\'' +
                ", autorizado=" + autorizado +
                ", activo=" + activo +
                '}';
    }
}
