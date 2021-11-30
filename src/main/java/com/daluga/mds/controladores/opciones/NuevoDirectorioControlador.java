package com.daluga.mds.controladores.opciones;

import com.daluga.mds.controladores.gestion_documental.GestionDocumental;
import com.daluga.mds.modelos.Directorios;
import com.daluga.mds.servicios.GestionDocumentalServicios;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.net.URL;
import java.util.ResourceBundle;

public class NuevoDirectorioControlador implements Initializable {
    @FXML
    public TextField txt_directorio;
    @FXML
    public MFXButton btn_aceptar;
    @FXML
    public MFXButton btn_cancelar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new JMetro(txt_directorio, Style.LIGHT);
    }
    @FXML
    public void OnClickAceptar(ActionEvent actionEvent) {
        GestionDocumentalServicios servicios = new GestionDocumentalServicios();
        Directorios directorios = new Directorios();
        directorios.setDirectorio(txt_directorio.getText());
        servicios.guardarDirectorio(directorios);
    }
    @FXML
    public void OnClickCancelar(ActionEvent actionEvent) {
        Stage stage = (Stage) txt_directorio.getScene().getWindow();
        stage.close();
    }
}
