package com.daluga.mds.modelos;

public class Areas {
    private Long id;
    private String area;

    public Areas(Long id, String area) {
        this.id = id;
        this.area = area;
    }

    public Areas() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Areas{" +
                "id=" + id +
                ", area='" + area + '\'' +
                '}';
    }
}
