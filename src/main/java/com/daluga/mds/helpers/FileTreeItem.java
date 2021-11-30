package com.daluga.mds.helpers;

import com.daluga.mds.Main;
import com.daluga.mds.modelos.Directorios;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

public class FileTreeItem extends TreeItem<String> {
    private Image FOLDER_COLLAPSE_IMAGE;
    private Image FOLDER_OPEN_IMAGE;


    public FileTreeItem(Directorios path){
        super(path.getDirectorio());
        FOLDER_COLLAPSE_IMAGE = new Image(String.valueOf(Main.class.getResource("imagenes/documental/folder_close.png")),30,30,true,true);
        FOLDER_OPEN_IMAGE = new Image(String.valueOf(Main.class.getResource("imagenes/documental/folder_open.png")),30,30,true,true);
        this.setGraphic(new ImageView(FOLDER_COLLAPSE_IMAGE));
        this.addEventHandler(TreeItem.branchExpandedEvent(),new EventHandler<TreeModificationEvent<String>>(){
            @Override
            public void handle(TreeModificationEvent<String> event) {
                FileTreeItem source = (FileTreeItem) event.getSource();
                if (!source.isExpanded()) {
                    ImageView iv = (ImageView) source.getGraphic();
                    iv.setImage(FOLDER_OPEN_IMAGE);
                }
            }
        });
        this.addEventHandler(TreeItem.branchCollapsedEvent(), new EventHandler<TreeModificationEvent<String>>(){
            @Override
            public void handle(TreeModificationEvent<String> event) {
                FileTreeItem source = (FileTreeItem) event.getSource();

                if (source.isExpanded()){
                    ImageView iv=(ImageView)source.getGraphic();
                    iv.setImage(FOLDER_COLLAPSE_IMAGE);
                }
            }
        });

    }

}
