module com.daluga.mds {
    requires java.base;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires MaterialFX;
    requires com.fasterxml.jackson.databind;
    requires com.jfoenix;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpmime;
    requires org.jfxtras.styles.jmetro;
    requires org.apache.commons.io;
    requires org.apache.commons.codec;


    opens com.daluga.mds to javafx.fxml;
    exports com.daluga.mds;
    opens com.daluga.mds.controladores to javafx.fxml;
    exports com.daluga.mds.controladores;
    opens com.daluga.mds.controladores.gestion_documental to javafx.fxml;
    exports com.daluga.mds.controladores.gestion_documental;
    exports com.daluga.mds.modelos;
    exports com.daluga.mds.servicios;
    exports com.daluga.mds.helpers;
    exports com.daluga.mds.controladores.sweet_alert;
    exports com.daluga.mds.controladores.opciones;
    opens com.daluga.mds.controladores.opciones to javafx.fxml;
    exports com.daluga.mds.controladores.pdf;
    opens com.daluga.mds.controladores.pdf to javafx.fxml;

}