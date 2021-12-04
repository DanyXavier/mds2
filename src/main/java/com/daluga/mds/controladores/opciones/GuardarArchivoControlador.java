package com.daluga.mds.controladores.opciones;

import com.daluga.mds.modelos.Areas;
import com.daluga.mds.modelos.Directorios;
import com.daluga.mds.modelos.Importancia;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GuardarArchivoControlador implements Initializable {
    @FXML
    public TextField txt_nombre_archivo;
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

    public File file;
    @FXML
    public TextArea txt_ubicacion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new JMetro(txt_nombre_archivo, Style.LIGHT);
        new JMetro(cb_area, Style.LIGHT);
        new JMetro(cb_directorio, Style.LIGHT);
        new JMetro(cb_importancia, Style.LIGHT);
        new JMetro(txt_nota, Style.LIGHT);
        new JMetro(txt_descripcion, Style.LIGHT);
        new JMetro(txt_ubicacion,Style.LIGHT);
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
    @FXML
    public void OnClickBuscarArchivo(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Documento");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todos", "*.*"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        Stage stage = (Stage) txt_nota.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        txt_nombre_archivo.setText(file.toString());
    }
}
