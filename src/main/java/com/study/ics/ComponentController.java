package com.study.ics;

/*********
 *
 * This Class is not Needed
 * This is will move to an example setup
 *
 *
 *
 *
 * **/

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ComponentController {

    @FXML
    private Label label;

    private Pane pane;

    private double startX;
    private double startY;



//    @FXML
//    public void initialize(){
//        label.setText("hi");
//    }


    public ComponentController makeDraggable(){
        pane.setOnMousePressed( e-> {
            startX = e.getSceneX() - pane.getTranslateX();
            startY = e.getSceneY() - pane.getTranslateY();
        });

        pane.setOnMouseDragged( e->{
            pane.setTranslateX(e.getSceneX() - startX);
            pane.setTranslateY(e.getSceneY() - startY);
        });

        return this;

    }



    public ComponentController setLabelText(String nameOfComponent){
        label.setText(nameOfComponent);

        return this;
    }

    public ComponentController setPane(Pane pane){
        this.pane = pane;
        return  this;
    }
}
