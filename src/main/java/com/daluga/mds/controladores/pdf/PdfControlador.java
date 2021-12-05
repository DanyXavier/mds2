package com.daluga.mds.controladores.pdf;

import com.daluga.mds.Main;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class PdfControlador implements Initializable {
    @FXML
    public WebView web;
    private WebEngine engine;
    private String url;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new JMetro(web, Style.LIGHT);
        engine = web.getEngine();
        try {
            url  = Main.class.getResource("web/viewer.html").toURI().toString();
            engine.setUserStyleSheetLocation(Main.class.getResource("web/viewer.css").toURI().toString());
            engine.setJavaScriptEnabled(true);
            engine.load(url);
        } catch (URISyntaxException e) {
            System.out.println(e);
        }

    }
    public void setDocument(InputStream stream){
        System.out.println(stream);
        try {
            byte[] bytes = IOUtils.toByteArray(stream);
            // Base64 from java.util
            String base64 = Base64.getEncoder().encodeToString(bytes);
            engine.executeScript("openFileFromBase64('" + base64 + "')");
        } catch (IOException e) {
            System.out.println(e);
        }
        /*
        engine.getLoadWorker()
                .stateProperty()
                .addListener((obs, oldV, newV) -> {
                    if (Worker.State.SUCCEEDED == newV) {
                        try {
                            //InputStream is = Main.class.getResourceAsStream("pdfjs_example.pdf");
                            System.out.println(stream);
                            byte[] bytes = IOUtils.toByteArray(stream);
                            // Base64 from java.util
                            String base64 = Base64.getEncoder().encodeToString(bytes);
                            // This must be ran on FXApplicationThread
                            engine.executeScript("openFileFromBase64('" + base64 + "')");

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            if (stream != null) {
                                try {
                                    stream.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                });*/
    }
}
