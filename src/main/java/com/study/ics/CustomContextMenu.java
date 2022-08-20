package com.study.ics;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CustomContextMenu extends ContextMenu {
    @javafx.fxml.FXML
    private MenuItem menuItem1;
    @javafx.fxml.FXML
    private MenuItem menuItem2;
    @javafx.fxml.FXML
    private ComboBox comboBox;
    @javafx.fxml.FXML
    private TextField textField;

    public CustomContextMenu(Label title, Label plantNumber) {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("CustomContextMenu.fxml")
        );

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);




        try {
            fxmlLoader.load();


            String week_days[] =
                    { "Monday", "Tuesday", "Wednesday",
                            "Thursday", "Friday" };



            // Create a combo box
//            ComboBox comboBox =
//                    new ComboBox(FXCollections
//                            .observableArrayList(week_days));

            comboBox.getItems().addAll(FXCollections
                    .observableArrayList(week_days));

            menuItem1.setOnAction((event) -> {
                System.out.println("Choice 1 clicked!");
            });


            menuItem2.setOnAction((event) -> {
                System.out.println("Choice 2 clicked!");
            });


            textField.textProperty().addListener( (obs, oldValue, newValue)->{
                title.setText(newValue);
            });

            comboBox.getSelectionModel().selectedItemProperty().addListener( (opt, oldValue, newValue)->{
                plantNumber.setText(newValue.toString());

            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void initialize(){

    }
}
