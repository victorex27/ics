package com.study.ics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class HelloController {

    @FXML
    private VBox vBox;


    public void initialize() throws IOException {

        vBox.getChildren().addAll(createAllPanes());
    }


    private ArrayList<Pane> createAllPanes() throws IOException {

        ArrayList<Pane> arrayListOfPanes = new ArrayList<Pane>();

        arrayListOfPanes.add(createPane("Component 1"));
        arrayListOfPanes.add(createPane("Component 2"));
        arrayListOfPanes.add(createPane("Component 3"));
        arrayListOfPanes.add(createPane("Component 4"));
        arrayListOfPanes.add(createPane("Component 5"));

        return arrayListOfPanes;
    }

    private Pane createPane(String nameOfComponent) throws IOException {

        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("component-view.fxml"));

        Pane pane = (Pane) fxmlLoader2.load();
        ComponentController c = ((ComponentController) (fxmlLoader2.getController()))
                .setLabelText(nameOfComponent)
                .setPane(pane).makeDraggable();

        return pane;
    }
}