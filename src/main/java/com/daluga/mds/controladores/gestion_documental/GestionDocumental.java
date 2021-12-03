package com.daluga.mds.controladores.gestion_documental;

import com.daluga.mds.Main;
import com.daluga.mds.controladores.opciones.GuardarArchivoControlador;
import com.daluga.mds.controladores.opciones.NuevoDirectorioControlador;
import com.daluga.mds.controladores.sweet_alert.SweetAlertController;
import com.daluga.mds.helpers.FileTreeItem;
import com.daluga.mds.modelos.*;
import com.daluga.mds.servicios.EmpleadoServicio;
import com.daluga.mds.servicios.GestionDocumentalServicios;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXStageDialog;
import io.github.palexdev.materialfx.controls.enums.ButtonType;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class GestionDocumental implements Initializable {
    @FXML
    public Label total_pagina;
    @FXML
    public TextField txt_nombre_documento;
    @FXML
    public ComboBox<Directorios> combo_directorio;
    @FXML
    public StackPane principal;
    @FXML
    public JFXDialog dialog;
    private Image FOLDER_COLLAPSE_IMAGE;
    private Image FOLDER_OPEN_IMAGE;
    private Image FILE_TYPE_PDF;
    private Image FILE_TYPE_XLS;
    private Image FILE_TYPE_DOC;
    private Image FILE_TYPE_PPT;
    private Image FILE_TYPE_NOT;
    private Image ROOT_IMG;
    @FXML
    public MenuBar menubar;
    @FXML
    public TabPane tab;
    @FXML
    public TreeTableView<Archivos> tabla_archivos;
    @FXML
    public ComboBox<String> elementos_por_pagina;
    @FXML
    public TextField txt_page;
    @FXML
    public TreeView<String> directorio_tabla;
    @FXML
    public TreeTableColumn<Archivos,Long> col_number;
    @FXML
    public TreeTableColumn<Archivos,String> col_document;
    @FXML
    public TreeTableColumn<Archivos,String> col_file_name;
    @FXML
    public TreeTableColumn<Archivos,String> col_location;
    @FXML
    public TreeTableColumn<Archivos,String> col_area;
    @FXML
    public TreeTableColumn<Archivos,String> col_importancia;
    @FXML
    public TreeTableColumn<Archivos,String> col_description;
    @FXML
    public TreeTableColumn<Archivos,String> col_date_create;
    @FXML
    public TreeTableColumn<Archivos,String> col_date_up;
    @FXML
    public TreeTableColumn<Archivos,String> col_nota;

    private TreeItem<String> root;
    // el index 0 es todos
    private ObservableList<Directorios> observableList;
    private TreeItem<Archivos> treeDocuments;
    private ObservableList<Archivos> documentsObservableList;
    private ObservableList<Importancia> importancia_list;
    private ObservableList<Areas> areas_list;

    @FXML
    public void OnClickImprimir(ActionEvent actionEvent) {
    }
    @FXML
    public void OnClickDescargar(ActionEvent actionEvent) {
    }
    @FXML
    public void OnClickPaginaSig(ActionEvent actionEvent) {
    }
    @FXML
    public void OnClickPaginaAnt(ActionEvent actionEvent) {
    }
    @FXML
    public void OnClickGuardarDoc(ActionEvent actionEvent) throws IOException {

        principal.getChildren().remove(dialog);
        Stage stage = (Stage) principal.getScene().getWindow();
        Stage st = new Stage();
        st.initOwner(stage);
        st.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader nuevo_arch = new FXMLLoader((Main.class.getResource("vista/documental/opciones/guardar_archivo.fxml")));
        GridPane pane = nuevo_arch.load();
        GuardarArchivoControlador arch_controlador = nuevo_arch.getController();
        Scene esc = new Scene(pane);
        st.setTitle("Creación de directorio");
        st.setScene(esc);
        st.setResizable(false);
        st.show();

        arch_controlador.cb_area.setItems(areas_list);
        arch_controlador.cb_directorio.setItems(combo_directorio.getItems());
        arch_controlador.cb_importancia.setItems(importancia_list);

    }
    @FXML
    public void OnClickNuevoDir(ActionEvent actionEvent) throws IOException {
        principal.getChildren().remove(dialog);
        Stage stage = (Stage) principal.getScene().getWindow();
        Stage st = new Stage();
        st.initOwner(stage);
        st.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader nuevo_dir = new FXMLLoader((Main.class.getResource("vista/documental/opciones/nuevo_directorio.fxml")));
        GridPane pane = nuevo_dir.load();
        NuevoDirectorioControlador nuevodir = nuevo_dir.getController();
        Scene esc = new Scene(pane);
        st.setTitle("Creación de directorio");
        st.setScene(esc);
        st.setResizable(false);
        st.show();
        nuevodir.btn_aceptar.setOnAction(e->{

            st.close();
            cargando("Cargando todos los archivos, espere por favor");

            GestionDocumentalServicios servicios = new GestionDocumentalServicios();
            Directorios directorios = new Directorios();
            directorios.setDirectorio(nuevodir.txt_directorio.getText());
            Directorios nuevoDir =servicios.guardarDirectorio(directorios);
            if (nuevoDir != null){
                dialog.close();
                principal.getChildren().remove(dialog);
                JFXDialogLayout layout = new JFXDialogLayout();
                Pane panel = new Pane();
                MFXButton btn_aceptar = new MFXButton("Aceptar");
                btn_aceptar.setStyle("-fx-background-color: #4782F0; -fx-text-fill:white");
                HBox box = new HBox(btn_aceptar);
                panel.setPrefSize(300,300);
                layout.setPrefWidth(350);
                SweetAlertController al = new SweetAlertController(panel);
                panel.getChildren().add(al.success(300,300,200,200));
                layout.setHeading(new Label("Creación del directorio correcto"));
                layout.setBody(panel);
                layout.setActions(box);
                dialog = new JFXDialog(principal,layout,JFXDialog.DialogTransition.CENTER);
                dialog.show();
                btn_aceptar.setOnAction(event ->{
                    dialog.close();
                    principal.getChildren().remove(dialog);
                    cargarDirectorios();
                });
            }
        });
        nuevodir.btn_cancelar.setOnAction(e-> {
            st.close();
            principal.getChildren().remove(dialog);
        });
    }
    @FXML
    public void OnClickRecargar(ActionEvent actionEvent) {
    }

    private void cargarDirectorios() {
        observableList = FXCollections.observableArrayList();
        documentsObservableList = FXCollections.observableArrayList();
        GestionDocumentalServicios f = new GestionDocumentalServicios();
        EmpleadoServicio empleadoServicio = new EmpleadoServicio();
        root = new TreeItem<>("GRUPOHEREDIA",new ImageView(ROOT_IMG));
        observableList.add(new Directorios(0L,"GRUPOHEREDIA",null));
        cargando("Cargando todos los archivos, espere por favor");
        Thread hilo = new Thread(()->{
            try {
                Thread.sleep(1000);

                List<Directorios> directorioList = f.obtenerDirectorios();
                for(Directorios dir : directorioList){
                    for (Archivos arch: dir.getArchivos()){
                        documentsObservableList.add(arch);
                        TreeItem<Archivos> treeArc = new TreeItem<>(arch);
                        treeDocuments.getChildren().add(treeArc);

                    }
                    observableList.add(dir);
                    FileTreeItem treeItem = new FileTreeItem(dir);
                    root.getChildren().add(treeItem);
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Error en obtener los directorios");
            }
            System.out.println("hilo ");
            Platform.runLater(()->{
                tabla_archivos.setRoot(treeDocuments);
                combo_directorio.setItems(observableList);
                directorio_tabla.setRoot(root);
                directorio_tabla.getSelectionModel().selectLast();
                root.setExpanded(true);
                dialog.close();
            });
        });
        hilo.start();
        new Thread(()->{
            try {
                Thread.sleep(2000);
                areas_list =FXCollections.observableList(empleadoServicio.obtenerAreas());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
        new Thread(()->{
            try {
                Thread.sleep(2000);
                importancia_list =FXCollections.observableList(f.obtenerImportancia());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        directorio_tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != root && newValue != null){
                newValue.setGraphic(new ImageView(FOLDER_OPEN_IMAGE));
                combo_directorio.getSelectionModel().select(directorio_tabla.getSelectionModel().getSelectedIndex());

            }
            if (oldValue != root && oldValue != null){
                oldValue.setGraphic(new ImageView(FOLDER_COLLAPSE_IMAGE));
                combo_directorio.getSelectionModel().select(directorio_tabla.getSelectionModel().getSelectedIndex());
            }
        });

        combo_directorio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                directorio_tabla.getSelectionModel().select(combo_directorio.getSelectionModel().getSelectedIndex());
            }
        });

    }

    private void cargando(String s) {
        JFXDialogLayout layout = new JFXDialogLayout();
        Label contentLabel = new Label();
        layout.setPrefWidth(350);
        Pane pane = new Pane();
        pane.setPrefSize(300,300);
        SweetAlertController controller = new SweetAlertController(pane);
        controller.loader(pane.getPrefWidth(),pane.getPrefHeight());
        contentLabel.setText(s);
        layout.setHeading(pane);
        layout.setBody(contentLabel);
        dialog = new JFXDialog(principal,layout,JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }

    private void customCol(){
        col_number.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getId()));
        col_area.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getArea().getArea()));
        col_date_create.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getFechaCreacion().toString()));
        col_date_up.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getFechaSubida().toString()));
        col_description.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getDescripcion()));
        col_file_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getArchivo()));
        col_importancia.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getImportancia().getImportancia()));
        col_location.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getUbicacionFisica()));
        col_nota.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getNota()));

        col_document.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getArchivo()));
        col_document.setCellFactory(new Callback<>() {
            @Override
            public TreeTableCell<Archivos, String> call(TreeTableColumn<Archivos, String> param) {
                return new TreeTableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            //System.out.println(item);

                            String type = item.split("\\.")[1];

                            switch (type) {
                                case "pdf" -> {
                                    HBox box = new HBox();
                                    box.setAlignment(Pos.CENTER);
                                    ImageView imageview = new ImageView();
                                    imageview.setImage(FILE_TYPE_PDF);
                                    box.getChildren().add(imageview);
                                    setGraphic(box);
                                }
                                case "xls", "xlsx" -> {
                                    HBox box = new HBox();
                                    box.setAlignment(Pos.CENTER);
                                    ImageView imageview = new ImageView();
                                    imageview.setImage(FILE_TYPE_XLS);
                                    box.getChildren().add(imageview);
                                    setGraphic(box);
                                }
                                case "doc", "docx" -> {
                                    HBox box = new HBox();
                                    box.setAlignment(Pos.CENTER);
                                    ImageView imageview = new ImageView();
                                    imageview.setImage(FILE_TYPE_DOC);
                                    box.getChildren().add(imageview);
                                    setGraphic(box);
                                }
                                case "ppt" -> {
                                    HBox box = new HBox();
                                    box.setAlignment(Pos.CENTER);
                                    ImageView imageview = new ImageView();
                                    imageview.setImage(FILE_TYPE_PPT);
                                    box.getChildren().add(imageview);
                                    setGraphic(box);
                                }
                                default -> {
                                    HBox box = new HBox();
                                    box.setAlignment(Pos.CENTER);
                                    ImageView imageview = new ImageView();
                                    imageview.setImage(FILE_TYPE_NOT);
                                    box.getChildren().add(imageview);
                                    setGraphic(box);
                                }
                            }
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        combo_directorio.setConverter(new StringConverter<>() {
            @Override
            public String toString(Directorios object) {
                return object!=null?object.getDirectorio():"";
            }

            @Override
            public Directorios fromString(String string) {
                return null;
            }
        });
        combo_directorio.setCellFactory(cell->new ListCell<>(){
            @Override
            protected void updateItem(Directorios item, boolean empty) {
                super.updateItem(item,empty);
                if (item!=null){
                    setText(item.getDirectorio());

                }else setText(null);
            }
        });
    }
    public void OnClickBuscar(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        principal.getChildren().remove(dialog);
        FOLDER_COLLAPSE_IMAGE = new Image(String.valueOf(Main.class.getResource("imagenes/documental/folder_close.png")),30,30,true,true);
        FOLDER_OPEN_IMAGE = new Image(String.valueOf(Main.class.getResource("imagenes/documental/folder_open.png")),30,30,true,true);
        FILE_TYPE_PDF = new Image(String.valueOf(Main.class.getResource("imagenes/documental/pdf.png")),30,30,true,true);
        FILE_TYPE_XLS = new Image(String.valueOf(Main.class.getResource("imagenes/documental/doc.png")),30,30,true,true);
        FILE_TYPE_DOC = new Image(String.valueOf(Main.class.getResource("imagenes/documental/xls.png")),30,30,true,true);
        FILE_TYPE_PPT = new Image(String.valueOf(Main.class.getResource("imagenes/documental/ppt.png")),30,30,true,true);
        FILE_TYPE_NOT = new Image(String.valueOf(Main.class.getResource("imagenes/documental/file_unknown.png")),30,30,true,true);
        ROOT_IMG =  new Image(String.valueOf(Main.class.getResource("imagenes/documental/desktop.png")),30,30,true,true);
        new JMetro(menubar, Style.LIGHT);
        new JMetro(tabla_archivos,Style.LIGHT);
        new JMetro(elementos_por_pagina,Style.LIGHT);
        new JMetro(txt_page,Style.LIGHT);
        new JMetro(directorio_tabla,Style.LIGHT);
        new JMetro(txt_nombre_documento,Style.LIGHT);
        new JMetro(combo_directorio,Style.LIGHT);
        ObservableList<String> elementos = FXCollections.observableArrayList("5","10","15","20","25");
        elementos_por_pagina.setItems(elementos);
        elementos_por_pagina.getSelectionModel().select(1);
        elementos_por_pagina.getSelectionModel().selectedItemProperty().addListener((op,old,ne)->{
            // reloadItem(ne);
        });

        cargarDirectorios();
        customCol();
    }
    /*
    FXMLLoader fxml = new FXMLLoader();
        fxml.setLocation(LoginController.class.getResource("../resources/sweetAlert.fxml"));
        //alert cargando
        JFXAlert<String> alert = new JFXAlert<>(comboBox.getScene().getWindow());
        JFXDialogLayout layout = new JFXDialogLayout();
        Label contentLabel = new Label();
        contentLabel.setStyle("-fx-font-family:'Poppins SemiBold'; -fx-font-size:14px;");
        //JFXButton btnCancle = new JFXButton("Aceptar");
        layout.setPrefWidth(300);
        Pane pane = fxml.load();
        SweetAlertController controller = fxml.getController();
        //layout.setActions(btnCancle);
        controller.loader(pane.getPrefWidth(),pane.getPrefHeight());
        contentLabel.setText("Cargando, espere por favor");
        layout.setHeading(pane);
        layout.setBody(contentLabel);
        alert.setContent(layout);
        alert.show();

        try {
            if (fieldPassword.getText() == null || comboBox.getValue() == null){
                throw new NullPointerException("El campo esta vacio");
            }
            String password = fieldPassword.getText();
            String username = comboBox.getValue();
            LoginService authenticate = new LoginService();
            Usuarios response = authenticate.login(username,password);
            if (response.getPassword().equals(password)){
                alert.close();
                oninfo();

            }else{
                alert.close();
                onError();
                throw new RuntimeException("No se pudo iniciar sesion");
            }
        }catch (NullPointerException e){
            log.error("Error en la linea con null");
        } catch (IOException e) {
            log.error("Error en la linea http");
            log.debug("login sin funcionamiento de los http");
            oninfo();
            //TODO dialogo que hubo un error
        }

                /*JFXDialogLayout layout = new JFXDialogLayout();
        layout.setPrefWidth(pane.getPrefWidth());
        pane.setPrefSize(pane.getPrefWidth(),pane.getPrefHeight());
        layout.setHeading(new Label("Crear nuevo directorio"));
        layout.setBody(pane);
        layout.setActions(boxOpt);***
    //dialog = new JFXDialog(principal,layout,JFXDialog.DialogTransition.CENTER);
    //dialog.show();
    * */
}
