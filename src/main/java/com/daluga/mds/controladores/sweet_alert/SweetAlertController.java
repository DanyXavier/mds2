package com.daluga.mds.controladores.sweet_alert;

import javafx.animation.*;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class SweetAlertController {
    public Pane dialogRoot;

    public SweetAlertController(Pane dialogRoot) {
        this.dialogRoot = dialogRoot;
    }

    public Canvas success(double width, double height, double x, double y){
        Canvas canvas = new Canvas(width,height);
        GraphicsContext gr = canvas.getGraphicsContext2D();
        gr.setStroke(Color.web("#e9f8e7"));
        gr.setLineWidth(5);
        gr.setLineCap(StrokeLineCap.ROUND);
        gr.strokeOval(50,50,x,y);
        gr.setStroke(Color.web("#87db87"));
        gr.setLineWidth(7);
        gr.strokeLine(width/3,height/1.8,width/2.4,y);
        gr.strokeLine(width/2.4,y,width/1.4,height/3);
        return canvas;
    }
    public Canvas error(double width, double height,double x, double y){
        Canvas canvas = new Canvas(width,height);
        GraphicsContext gr = canvas.getGraphicsContext2D();
        gr.setStroke(Color.web("#e37b77"));
        gr.setLineWidth(5);
        gr.setLineCap(StrokeLineCap.ROUND);
        gr.strokeOval(50,50,x,y);
        gr.setLineWidth(7);
        gr.strokeLine(width/3.2,height/3.2,width/1.47,y+10);
        gr.strokeLine(width/3.2,y+10,width/1.47,height/3.2);
        return canvas;
    }
    public Canvas warning(double width, double height,double x, double y){
        Canvas canvas = new Canvas(width,height);
        GraphicsContext gr = canvas.getGraphicsContext2D();
        gr.setStroke(Color.web("#f8d286"));
        gr.setLineWidth(5);
        gr.setLineCap(StrokeLineCap.ROUND);
        gr.strokeOval(50,50,x,y);
        gr.setStroke(Color.web("#f8bb86"));
        gr.setLineWidth(7);
        gr.strokeLine(width/2,height/4,width/2,y-20);
        gr.strokeOval(width/2 -2.3,y,5,5);
        return canvas;
    }
    public Canvas info(double width, double height,double x, double y){
        Canvas canvas = new Canvas(width,height);
        GraphicsContext gr = canvas.getGraphicsContext2D();
        gr.setStroke(Color.web("#3fc3ee"));
        gr.setLineWidth(5);
        gr.setLineCap(StrokeLineCap.ROUND);
        gr.strokeOval(50,50,x,y);
        gr.setLineWidth(7);
        gr.strokeLine(width/2,height/3,width/2,y+20);
        gr.strokeOval(width/2 -2.3,height/4,5,5);
        return canvas;
    }
    public void loader(double width,double height){
        for (int i = 0;i<25;i++) {
            //
            Circle circle = new Circle();
            circle.setCenterX(20 * i);
            circle.setCenterY(height/ 2);
            circle.setRadius(15);
            circle.setFill(Color.rgb(32, 178, 229, 0.04 * i));
            circle.setStrokeWidth(0);
            dialogRoot.getChildren().add(circle);
        }
        Duration duration = Duration.millis(1500);
        int size = dialogRoot.getChildren().size();
        ObservableList<Node> elements = dialogRoot.getChildren();
        List<PathTransition> pathTransitionL= new ArrayList<>();
        for (int i =0;i<size;i++){
            Circle circle = new Circle(width/2,height/2,width*.45);
            circle.setRotate(11*i);
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(duration);
            pathTransition.setNode(elements.get(i));
            pathTransition.setPath(circle);
            pathTransition.setCycleCount(Timeline.INDEFINITE);
            pathTransition.play();
            pathTransitionL.add(pathTransition);
        }
    }
}