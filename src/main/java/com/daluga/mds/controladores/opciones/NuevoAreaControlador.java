package com.daluga.mds.controladores.opciones;

import com.daluga.mds.modelos.Areas;
import com.daluga.mds.modelos.Directorios;
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

public class NuevoAreaControlador implements Initializable {

    @FXML
    public MFXButton btn_aceptar;
    @FXML
    public MFXButton btn_cancelar;
    @FXML
    public TextField txt_area;
    @FXML
    public ComboBox<Areas> cb_areas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new JMetro(txt_area, Style.LIGHT);
        new JMetro(cb_areas, Style.LIGHT);
        cb_areas.setConverter(new StringConverter<>() {
            @Override
            public String toString(Areas object) {
                return object!=null?object.getArea():"";
            }

            @Override
            public Areas fromString(String string) {
                return null;
            }
        });
        cb_areas.setCellFactory(cell->new ListCell<>(){
            @Override
            protected void updateItem(Areas item, boolean empty) {
                super.updateItem(item,empty);
                if (item!=null){
                    setText(item.getArea());

                }else setText(null);
            }
        });
    }

}
