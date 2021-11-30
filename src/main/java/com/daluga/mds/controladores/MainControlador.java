package com.daluga.mds.controladores;

import com.daluga.mds.Main;
import com.daluga.mds.modelos.Usuario;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.util.Objects;

public class MainControlador {
    @FXML
    public ComboBox<Usuario> listUser;
    @FXML
    public PasswordField txt_password;
    public void initialize(){
        new JMetro(listUser, Style.LIGHT);
        new JMetro(txt_password,Style.LIGHT);
        ObservableList<Usuario> usuarioObservableList = FXCollections.observableArrayList(
                new Usuario(1L,"SISTEMAS","123",true,true),
                new Usuario(2L,"RRHH","123",true,true)
        );
        listUser.setItems(usuarioObservableList);
        configureComboBox();
    }
    private void configureComboBox(){
        listUser.setConverter(new StringConverter<>() {
            @Override
            public String toString(Usuario object) {
                return object!=null?object.getUsuario():"";
            }

            @Override
            public Usuario fromString(String string) {
                return null;
            }
        });
        listUser.setCellFactory(cell->new ListCell<>(){
            @Override
            protected void updateItem(Usuario item, boolean empty) {
                super.updateItem(item,empty);
                if (item!=null){
                    setText(item.getUsuario());

                }else setText(null);
            }
        });
    }
    @FXML
    public void OnClickIniciarSesion(ActionEvent actionEvent) throws IOException {
        Node  source = (Node)  actionEvent.getSource();
        Stage root  = (Stage) source.getScene().getWindow();
        Parent fxml = FXMLLoader.load(Main.class.getResource("vista/documental/gestion_documental.fxml"));
        Scene scene = new Scene(fxml);
        Stage stage = new Stage();
        stage.setTitle("Documental");
        stage.setScene(scene);
        //stage.initOwner(root);
        stage.show();
        root.close();
    }
}