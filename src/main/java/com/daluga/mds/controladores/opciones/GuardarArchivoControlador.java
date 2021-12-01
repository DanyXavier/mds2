package com.daluga.mds.controladores.opciones;

import com.daluga.mds.modelos.Areas;
import com.daluga.mds.modelos.Directorios;
import com.daluga.mds.modelos.Importancia;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class GuardarArchivoControlador implements Initializable {
    @FXML
    public TextField txt_nombre_archivo;
    @FXML
    public MFXButton btn_buscar_archivo;
    @FXML
    public MFXDatePicker date;
    @FXML
    public ComboBox<Areas> cb_area;
    @FXML
    public ComboBox<Directorios> cb_directorio;
    @FXML
    public ComboBox<Importancia> cb_importancia;
    @FXML
    public MFXButton btn_aceptar;
    @FXML
    public MFXButton btn_cancelar;
    @FXML
    public TextArea txt_nota;
    @FXML
    public TextArea txt_descripcion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_area.setConverter(new StringConverter<>() {
            @Override
            public String toString(Areas object) {
                return object!=null?object.getArea():"";
            }

            @Override
            public Areas fromString(String string) {
                return null;
            }
        });
        cb_area.setCellFactory(cell->new ListCell<>(){
            @Override
            protected void updateItem(Areas item, boolean empty) {
                super.updateItem(item,empty);
                if (item!=null){
                    setText(item.getArea());

                }else setText(null);
            }
        });
        cb_directorio.setConverter(new StringConverter<>() {
            @Override
            public String toString(Directorios object) {
                return object!=null?object.getDirectorio():"";
            }

            @Override
            public Directorios fromString(String string) {
                return null;
            }
        });
        cb_directorio.setCellFactory(cell->new ListCell<>(){
            @Override
            protected void updateItem(Directorios item, boolean empty) {
                super.updateItem(item,empty);
                if (item!=null){
                    setText(item.getDirectorio());

                }else setText(null);
            }
        });
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
