package com.daluga.mds.controladores.opciones;

import com.daluga.mds.modelos.Areas;
import com.daluga.mds.modelos.Directorios;
import com.daluga.mds.modelos.Importancia;
import com.daluga.mds.servicios.GestionDocumentalServicios;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.net.URL;
import java.util.ResourceBundle;

public class NuevoImportanciaControlador implements Initializable {
    @FXML
    public MFXButton btn_aceptar;
    @FXML
    public MFXButton btn_cancelar;
    @FXML
    public TextField txt_importancia;
    @FXML
    public ComboBox<Importancia> cb_importancia;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new JMetro(txt_importancia, Style.LIGHT);
        new JMetro(cb_importancia, Style.LIGHT);
        cb_importancia.setConverter(new StringConverter<>() {
            @Override
            public String toString(Importancia object) {
                return object!=null?object.getImportancia():"";
            }

            @Override
            public Importancia fromString(String string) {
                return null;
            }
        });
        cb_importancia.setCellFactory(cell->new ListCell<>(){
            @Override
            protected void updateItem(Importancia item, boolean empty) {
                super.updateItem(item,empty);
                if (item!=null){
                    setText(item.getImportancia());

                }else setText(null);
            }
        });
    }

}
