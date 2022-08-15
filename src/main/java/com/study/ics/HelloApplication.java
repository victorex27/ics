package com.study.ics;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, 800, 450);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setMaximized(true);
//        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();


        root.setCenter( new RootLayout());

    }

    public static void main(String[] args) {
        launch();
    }
}