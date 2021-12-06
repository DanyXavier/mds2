package com.daluga.mds.controladores.gestion_documental;

import com.daluga.mds.Main;
import com.daluga.mds.controladores.opciones.GuardarArchivoControlador;
import com.daluga.mds.controladores.opciones.NuevoAreaControlador;
import com.daluga.mds.controladores.opciones.NuevoDirectorioControlador;
import com.daluga.mds.controladores.opciones.NuevoImportanciaControlador;
import com.daluga.mds.controladores.pdf.PdfControlador;
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
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableColumn;
import io.github.palexdev.materialfx.controls.enums.ButtonType;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

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
    public TableView<Archivos> tabla_archivos;
    @FXML
    public ComboBox<String> elementos_por_pagina;
    @FXML
    public TextField txt_page;
    @FXML
    public TreeView<String> directorio_tabla;
    @FXML
    public TableColumn<Archivos,Long> col_number;
    @FXML
    public TableColumn<Archivos,String> col_document;
    @FXML
    public TableColumn<Archivos,String> col_file_name;
    @FXML
    public TableColumn<Archivos,String> col_location;
    @FXML
    public TableColumn<Archivos,String> col_area;
    @FXML
    public TableColumn<Archivos,String> col_importancia;
    @FXML
    public TableColumn<Archivos,String> col_description;
    @FXML
    public TableColumn<Archivos,String> col_date_create;
    @FXML
    public TableColumn<Archivos,String> col_date_up;
    @FXML
    public TableColumn<Archivos,String> col_nota;

    private TreeItem<String> root;
    // el index 0 es todos
    private ObservableList<Directorios> observableList;
    private ObservableList<Archivos> documentsObservableList;
    private ObservableList<Importancia> importancia_list;
    private ObservableList<Areas> areas_list;
    private int total;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        principal.getChildren().remove(dialog);
        FOLDER_COLLAPSE_IMAGE = new Image(String.valueOf(Main.class.getResource("imagenes/documental/folder_close.png")),30,30,true,true);
        FOLDER_OPEN_IMAGE = new Image(String.valueOf(Main.class.getResource("imagenes/documental/folder_open.png")),30,30,true,true);
        FILE_TYPE_PDF = new Image(String.valueOf(Main.class.getResource("imagenes/documental/pdf.png")),30,30,true,true);
        FILE_TYPE_XLS = new Image(String.valueOf(Main.class.getResource("imagenes/documental/xls.png")),30,30,true,true);
        FILE_TYPE_DOC = new Image(String.valueOf(Main.class.getResource("imagenes/documental/doc.png")),30,30,true,true);
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
        col_number.setSortType(TableColumn.SortType.ASCENDING);
        ObservableList<String> elementos = FXCollections.observableArrayList("5","10","15","20","25");
        elementos_por_pagina.setItems(elementos);
        elementos_por_pagina.getSelectionModel().select(0);
        elementos_por_pagina.getSelectionModel().selectedItemProperty().addListener((op,old,ne)->{
            // reloadItem(ne);
            ObservableList<Archivos> subList = pageResponse(0);
            tabla_archivos.setItems(subList);
            directorio_tabla.getSelectionModel().select(0);
            txt_page.setText(String.valueOf(1));
        });

        cargarDirectorios();
        customCol();
    }

    @FXML
    public void OnClickImprimir(ActionEvent actionEvent) {
    }
    @FXML
    public void OnClickDescargar(ActionEvent actionEvent) {
        Archivos archivo = tabla_archivos.getSelectionModel().getSelectedItem();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(archivo.getArchivo());
        fileChooser.setTitle("Descargar documento");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todos", "*.*")
        );
        Stage stage = (Stage) txt_page.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        GestionDocumentalServicios g = new GestionDocumentalServicios();

        principal.getChildren().remove(dialog);
        cargando("Descargando documento, espere por favor");
        new Thread(()->{
            File doc = g.descargarArchivo(archivo,file);
            try {
                Thread.sleep(2000);
                System.out.println(doc);
                Platform.runLater(()->{
                    dialog.close();
                });
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        }).start();
    }
    @FXML
    public void OnClickPaginaSig(ActionEvent actionEvent) {
        int pagina = Integer.parseInt(txt_page.getText());
        int totalPa = Integer.parseInt(total_pagina.getText());
        if (pagina<totalPa){
            txt_page.setText(String.valueOf(pagina+1));
            ObservableList<Archivos> archivos = pageResponse(pagina);
            tabla_archivos.setItems(archivos);
        }

    }
    @FXML
    public void OnClickPaginaAnt(ActionEvent actionEvent) {
        int pagina = Integer.parseInt(txt_page.getText())-1;
        if (pagina>0){
            txt_page.setText(String.valueOf(pagina));
            ObservableList<Archivos> archivos = pageResponse(pagina-1);
            tabla_archivos.setItems(archivos);
        }
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
        st.setTitle("Guardar Archivo");
        st.setScene(esc);
        st.setResizable(false);
        st.getIcons().add(new Image(Main.class.getResourceAsStream("imagenes/grupoheredia.png")));
        st.show();

        arch_controlador.cb_area.setItems(areas_list);
        ObservableList<Directorios> dir = combo_directorio.getItems();
        dir.remove(0);
        arch_controlador.cb_directorio.setItems(dir);
        arch_controlador.cb_importancia.setItems(importancia_list);

        arch_controlador.btn_aceptar.setOnAction(e->{
            String filename = "";
            int index = arch_controlador.txt_nombre_archivo.getText().lastIndexOf('.');
            if(index > 0) {
                filename = arch_controlador.txt_nombre_archivo.getText() ;
            }else {
                filename = arch_controlador.txt_nombre_archivo.getText() +"."+ arch_controlador.ext;
            }
            st.close();
            cargando("Estamos guardando su archivo espere por favor.");
            GestionDocumentalServicios servicios = new GestionDocumentalServicios();
            Archivos archivos = new Archivos();
            archivos.setArchivo(filename);
            archivos.setArea(arch_controlador.cb_area.getValue());
            archivos.setDirectorio(arch_controlador.cb_directorio.getValue());
            archivos.setImportancia(arch_controlador.cb_importancia.getValue());
            archivos.setEstado(true);
            archivos.setDescripcion(arch_controlador.txt_descripcion.getText());
            archivos.setFechaCreacion(arch_controlador.date.getDate().toString());
            archivos.setFechaSubida(LocalDate.now().toString());
            archivos.setNota(arch_controlador.txt_nota.getText());
            archivos.setUbicacionFisica(arch_controlador.txt_ubicacion.getText());
            System.out.println(archivos);
            //Directorios directorios = new Directorios();
            //directorios.setDirectorio(arch_controlador.txt_directorio.getText());
            try {
                Archivos nuevoArc  = servicios.guardarArchivo(archivos,arch_controlador.file);
                dialog.close();
                if (nuevoArc != null){
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
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        arch_controlador.btn_cancelar.setOnAction(e-> {
            st.close();
            principal.getChildren().remove(dialog);
        });

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
        st.getIcons().add(new Image(Main.class.getResourceAsStream("imagenes/grupoheredia.png")));
        st.show();
        nuevodir.btn_aceptar.setOnAction(e->{

            st.close();
            cargando("cargando");
            GestionDocumentalServicios servicios = new GestionDocumentalServicios();
            Directorios directorios = new Directorios();
            directorios.setDirectorio(nuevodir.txt_directorio.getText());
            Directorios nuevoDir =servicios.guardarDirectorio(directorios);
            dialog.close();
            if (nuevoDir != null){

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
        tabla_archivos.setItems(documentsObservableList);
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
                Thread.sleep(3000);

                List<Directorios> directorioList = f.obtenerDirectorios();
                for(Directorios dir : directorioList){
                    for (Archivos archivos: dir.getArchivos()) {
                        Directorios direc = dir;
                        direc.setArchivos(null);
                        archivos.setDirectorio(direc);
                        documentsObservableList.addAll(archivos);
                    }

                    observableList.add(dir);
                    FileTreeItem treeItem = new FileTreeItem(dir);
                    root.getChildren().add(treeItem);
                }
                Platform.runLater(()->{
                    documentsObservableList = documentsObservableList.sorted(Comparator.comparing(Archivos::getId));

                    total = documentsObservableList.size();

                    int pagina = Integer.parseInt(txt_page.getText())-1;

                    ObservableList<Archivos> subList = pageResponse(pagina);
                    tabla_archivos.setItems(subList);
                    //tabla_archivos.setShowRoot(false);
                });
            } catch (IOException | InterruptedException e) {
                System.out.println("Error en obtener los directorios");
            }
            Platform.runLater(()->{
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
                Thread.sleep(1000);
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
            combo_directorio.getSelectionModel().select(directorio_tabla.getSelectionModel().getSelectedIndex());
            if (newValue != root) {
                ObservableList<Archivos> filtrado = documentsObservableList.filtered(p->p.getDirectorio().getDirectorio().equals(newValue.getValue()));
                tabla_archivos.setItems(filtrado);
            }

            if (newValue != root && newValue != null){
                newValue.setGraphic(new ImageView(FOLDER_OPEN_IMAGE));
            }
            if (oldValue != root && oldValue != null){
                oldValue.setGraphic(new ImageView(FOLDER_COLLAPSE_IMAGE));
            }
        });

        combo_directorio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                directorio_tabla.getSelectionModel().select(combo_directorio.getSelectionModel().getSelectedIndex());
            }
        });

    }

    private ObservableList<Archivos> pageResponse(int page){
        int limit = Integer.parseInt(elementos_por_pagina.getValue());
        int total_pag = (total/limit) + 1 ;
        total_pagina.setText(String.valueOf(total_pag));
        int fromIndex = page*limit;
        int toIndex = fromIndex+limit;
        if(fromIndex <= total) {
            if(toIndex > total){
                toIndex = total;
            }
            return FXCollections.observableList(documentsObservableList.subList(fromIndex, toIndex));
        }else {
            return FXCollections.observableArrayList();
        }
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

        col_number.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_area.setCellValueFactory(param->{
            SimpleObjectProperty<String> property = new SimpleObjectProperty<>();
            property.setValue(param.getValue().getArea().getArea());
            return property;
        });
        col_date_create.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        col_date_up.setCellValueFactory(new PropertyValueFactory<>("fechaSubida"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col_file_name.setCellValueFactory(new PropertyValueFactory<>("archivo"));
        col_importancia.setCellValueFactory(param->{
            SimpleObjectProperty<String> property = new SimpleObjectProperty<>();
            property.setValue(param.getValue().getImportancia().getImportancia());
            return property;
        });
        col_location.setCellValueFactory(new PropertyValueFactory<>("ubicacionFisica"));
        col_nota.setCellValueFactory(new PropertyValueFactory<>("nota"));

        col_document.setCellValueFactory(new PropertyValueFactory<>("archivo"));
        col_document.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Archivos, String> call(TableColumn<Archivos, String> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            //System.out.println(item);
                            int total = item.length();
                            String type = item.substring(total-5,total).split("\\.")[1];

                            switch (type) {
                                case "pdf" -> {
                                    HBox box = new HBox();
                                    box.setAlignment(Pos.CENTER);
                                    ImageView imageview = new ImageView();
                                    imageview.setImage(FILE_TYPE_PDF);
                                    box.getChildren().add(imageview);
                                    setGraphic(box);
                                    break;
                                }
                                case "xls", "xlsx" -> {
                                    HBox box = new HBox();
                                    box.setAlignment(Pos.CENTER);
                                    ImageView imageview = new ImageView();
                                    imageview.setImage(FILE_TYPE_XLS);
                                    box.getChildren().add(imageview);
                                    setGraphic(box);
                                    break;
                                }
                                case "doc", "docx" -> {
                                    HBox box = new HBox();
                                    box.setAlignment(Pos.CENTER);
                                    ImageView imageview = new ImageView();
                                    imageview.setImage(FILE_TYPE_DOC);
                                    box.getChildren().add(imageview);
                                    setGraphic(box);
                                    break;
                                }
                                case "ppt" -> {
                                    HBox box = new HBox();
                                    box.setAlignment(Pos.CENTER);
                                    ImageView imageview = new ImageView();
                                    imageview.setImage(FILE_TYPE_PPT);
                                    box.getChildren().add(imageview);
                                    setGraphic(box);
                                    break;
                                }
                                default -> {
                                    HBox box = new HBox();
                                    box.setAlignment(Pos.CENTER);
                                    ImageView imageview = new ImageView();
                                    imageview.setImage(FILE_TYPE_NOT);
                                    box.getChildren().add(imageview);
                                    setGraphic(box);
                                    break;
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
        principal.getChildren().remove(dialog);
        //tabla_archivos.it
        System.out.println(txt_nombre_documento.getText());
        tabla_archivos.setItems(documentsObservableList.filtered(p->p.getArchivo().toLowerCase(Locale.ROOT).contains(txt_nombre_documento.getText().toLowerCase(Locale.ROOT))));
    }

    @FXML
    public void OnClickImportancia(ActionEvent actionEvent) throws IOException {
        principal.getChildren().remove(dialog);
        Stage stage = (Stage) principal.getScene().getWindow();
        Stage st = new Stage();
        st.initOwner(stage);
        st.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader nuevo_dir = new FXMLLoader((Main.class.getResource("vista/documental/opciones/nuevo_importancia.fxml")));
        GridPane pane = nuevo_dir.load();
        NuevoImportanciaControlador nuevoImportancia = nuevo_dir.getController();
        Scene esc = new Scene(pane);
        st.setTitle("Creación de la etiqueta importancia");
        st.setScene(esc);
        st.setResizable(false);
        st.getIcons().add(new Image(Main.class.getResourceAsStream("imagenes/grupoheredia.png")));
        st.show();
        nuevoImportancia.cb_importancia.setItems(importancia_list);
        nuevoImportancia.btn_aceptar.setOnAction(e->{

            st.close();

            GestionDocumentalServicios servicios = new GestionDocumentalServicios();
            Importancia importancia = new Importancia();
            importancia.setImportancia(nuevoImportancia.txt_importancia.getText());
            Importancia nuevoImport =servicios.guardarImportancia(importancia);
            dialog.close();
            if (nuevoImport != null){

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
                layout.setHeading(new Label("Creación de la etiqueta importancia correcto"));
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
        nuevoImportancia.btn_cancelar.setOnAction(e-> {
            st.close();
            principal.getChildren().remove(dialog);
        });
    }
    @FXML
    public void OnClickArea(ActionEvent actionEvent) throws IOException {
        principal.getChildren().remove(dialog);
        Stage stage = (Stage) principal.getScene().getWindow();
        Stage st = new Stage();
        st.initOwner(stage);
        st.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader nuevo_dir = new FXMLLoader((Main.class.getResource("vista/documental/opciones/nuevo_area.fxml")));
        GridPane pane = nuevo_dir.load();
        NuevoAreaControlador nuevoAreaControlador = nuevo_dir.getController();
        Scene esc = new Scene(pane);
        st.setTitle("Creación de la etiqueta importancia");
        st.setScene(esc);
        st.setResizable(false);
        st.getIcons().add(new Image(Main.class.getResourceAsStream("imagenes/grupoheredia.png")));
        st.show();
        nuevoAreaControlador.cb_areas.setItems(areas_list);
        nuevoAreaControlador.btn_aceptar.setOnAction(e->{

            st.close();
            EmpleadoServicio servicios = new EmpleadoServicio();
            Areas areas = new Areas();
            areas.setArea(nuevoAreaControlador.txt_area.getText());
            Areas nuevoImport =servicios.guardarArea(areas);
            dialog.close();
            if (nuevoImport != null){

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
                layout.setHeading(new Label("Creación de la etiqueta importancia correcto"));
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
        nuevoAreaControlador.btn_cancelar.setOnAction(e-> {
            st.close();
            principal.getChildren().remove(dialog);
        });
    }

    public void OnClickVerPdf(ActionEvent actionEvent) throws IOException {
        principal.getChildren().remove(dialog);
        Archivos archivo = tabla_archivos.getSelectionModel().getSelectedItem();
        Stage stage = (Stage) principal.getScene().getWindow();
        Stage st = new Stage();
        st.initOwner(stage);
        st.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader nuevo_dir = new FXMLLoader((Main.class.getResource("vista/pdf/pdfView.fxml")));
        AnchorPane pane = nuevo_dir.load();
        PdfControlador pdfControlador = nuevo_dir.getController();
        Scene esc = new Scene(pane);
        st.setTitle("Ver documento");
        st.setScene(esc);
        st.getIcons().add(new Image(Main.class.getResourceAsStream("imagenes/grupoheredia.png")));
        st.setResizable(false);


        GestionDocumentalServicios g = new GestionDocumentalServicios();
        cargando("Cargando documento, espere por favor");

        new Thread(()->{
            InputStream doc = g.verPDF(archivo);
            try {
                Thread.sleep(2000);

                System.out.println(doc);
                Platform.runLater(()->{
                    st.show();
                    pdfControlador.setDocument(doc);
                    dialog.close();

                });
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        }).start();

    }
}
